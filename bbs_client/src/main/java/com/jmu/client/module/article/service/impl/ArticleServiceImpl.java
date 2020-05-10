package com.jmu.client.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.client.expection.BusinessException;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.article.mapper.ArticleContentExtMapper;
import com.jmu.client.module.article.mapper.ArticleExtExtMapper;
import com.jmu.client.module.article.mapper.ArticleInfoExtMapper;
import com.jmu.client.module.article.service.ArticleService;
import com.jmu.client.module.articleLabel.mapper.ArticleLabelExtMapper;
import com.jmu.client.module.blackList.service.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import com.jmu.client.entity.ArticleContent;
import com.jmu.client.entity.ArticleExt;
import com.jmu.client.entity.ArticleInfo;
import com.jmu.client.entity.ArticleLabel;
import com.jmu.client.enums.BusinessEnum;
import com.jmu.client.enums.FeaturedEnum;
import com.jmu.client.enums.ResourceEnum;
import com.jmu.client.enums.StateEnum;
import com.jmu.client.mapper.ArticleContentMapper;
import com.jmu.client.mapper.ArticleExtMapper;
import com.jmu.client.mapper.ArticleInfoMapper;
import com.jmu.client.mq.SendMsg;
import com.jmu.client.req.ArticleReq;
import com.jmu.client.req.Page;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.CodeGenerateUtil;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleExtVO;
import com.jmu.client.vo.ArticleVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/23 16:36
 * @since 1.0
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleInfoMapper articleInfoMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleExtMapper articleExtMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private ArticleContentExtMapper articleContentExtMapper;
    @Autowired
    private ArticleExtExtMapper articleExtExtMapper;
    @Autowired
    private ArticleInfoExtMapper articleInfoExtMapper;
    @Autowired
    private ArticleLabelExtMapper articleLabelExtMapper;
    @Autowired
    private BlackListService blackListService;
    @Autowired
    private SendMsg sendMsg;

    /**
     * 发送文章，1.获取编号，2.写入文章主体，3.写文章正文，4.写文章附件
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/23 16:42
     * @since 1.0
     */
    @Override
    public void sendArticle(ArticleReq articleReq) {
        if (blackListService.isInBlackList(articleReq.getUserId()) > 0) {
            System.out.println("进入黑名单");
            throw new BusinessException(BusinessEnum.IS_IN_BLACKLIST);
        }
        String articleCode = CodeGenerateUtil.generateArticleCode(articleReq.getUserId());
        ArticleInfo articleInfo = ArticleInfo.builder()
                .articleCode(articleCode)
                .state((byte) 0)
                .favoryNum(0L)
                .likeNum(0L)
                .shareNum(0L)
                .userId(articleReq.getUserId())
                .viewNum(0L)
                .title(articleReq.getTitle())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        System.out.println("ArticleServiceImpl sendArticle articleInfo:" + articleInfo);

        transactionTemplate.execute(s -> {
            //主体信息
            int infoResult = articleInfoMapper.insert(articleInfo);
            if (infoResult <= 0) {
                throw new BusinessException(BusinessEnum.INFO_INSERT_FAILD);
            }
            articleReq.setId(articleInfo.getId());
            // 插入正文内容
            int contentResult = insertArticleContent(articleReq);
            if (contentResult <= 0) {
                throw new BusinessException(BusinessEnum.CONTENT_INSERT_FAILD);
            }
            // 插入媒体内容
            int extResult = insertArticleExt(articleReq);
            if (extResult <= 0) {
                throw new BusinessException(BusinessEnum.EXT_INSERT_FAILD);
            }
            //放入标签标志
            int labelResult = insertLabel(articleReq);
            if (labelResult <= 0) {
                throw new BusinessException(BusinessEnum.ARTICLE_LABEL_INSERT_FAILD);
            }
            return null;
        });
    }


    /**
     * 删除文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/24 11:49
     * @since 1.0
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteArticle(Long id, UserInfo userInfo) {
        //验证本人操作
        validate(id);

        int infoResult = articleInfoMapper.deleteByPrimaryKey(id);
        if (infoResult < 1) {
            throw new BusinessException(BusinessEnum.INFO_DELETE_FAILD);
        }
        int contentResult = articleContentExtMapper.deleteByArticleId(id);
        if (contentResult < 1) {
            throw new BusinessException(BusinessEnum.CONTENT_DELETE_FAILD);
        }
        int extResult = articleExtExtMapper.deleteByArticleId(id);
        if (extResult < 1) {
            throw new BusinessException(BusinessEnum.EXT_DELETE_FAILD);
        }
    }

    @Override
    public void delete(Long id, UserInfo userInfo) {
        //验证本人操作
        validate(id);
        //删除文章主体
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setState(StateEnum.DELETE.getCode());
        articleInfo.setId(id);
        int infoResult = articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
        if (infoResult < 1) {
            throw new BusinessException(BusinessEnum.INFO_DELETE_FAILD);
        }
        //删除文章内容
        ArticleContent articleContent = new ArticleContent();
        articleContent.setState(StateEnum.DELETE.getCode());
        articleContent.setArticleId(id);
        int contentResult = articleContentMapper.updateByArticleIdSelective(articleContent);
        if (contentResult < 1) {
            throw new BusinessException(BusinessEnum.CONTENT_DELETE_FAILD);
        }
        //删除文章拓展
        ArticleExt articleExt = new ArticleExt();
        articleExt.setArticleId(id);
        articleExt.setState(StateEnum.DELETE.getCode());
        int extResult = articleExtMapper.updateByArticleIdSelective(articleExt);
        if (extResult < 1) {
            throw new BusinessException(BusinessEnum.EXT_DELETE_FAILD);
        }
    }

    @Override
    @Transactional
    public ArticleVO getArticle(Long id, UserInfo userInfo) {
        //文章不存在
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(id);
        if (articleInfo == null) {
            throw new BusinessException(BusinessEnum.NO_ARTICLE);
        }
        //文章已删除
        if (articleInfo.getState().equals(StateEnum.DELETE.getCode())) {
            throw new BusinessException(BusinessEnum.ARTICLE_HAD_DELETE);
        }

        ArticleContent articleContent = articleContentExtMapper.getByArticleId(id);
        if (articleContent == null) {
            articleContent = new ArticleContent();
            articleContent.setContent("");
        }
        List<ArticleExt> articleExts = articleExtExtMapper.listByArticleId(id);
        ArticleVO articleVO = ArticleVO.of(articleInfo, articleContent, articleExts, userInfo);
        return articleVO;
    }

    @Override
    public PageInfo<ArticleVO> listArticleOfUser(Long id, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        com.github.pagehelper.Page<ArticleVO> articleVOPage = articleInfoExtMapper.listArticleByUserId(id);
        //对每个字段赋值
        for (int i = 0; i < articleVOPage.size(); i++) {
            if (!(articleVOPage.get(i).getArticleExts() == null || articleVOPage.get(i).getArticleExts().isEmpty())) {
                //对获取到的结果赋值vo
                List<ArticleExtVO> articleExtVOS = articleVOPage.get(i).getArticleExts().stream().map(ArticleExtVO::of).collect(Collectors.toList());
                articleVOPage.get(i).setArticleExtVOS(articleExtVOS);
                articleVOPage.get(i).setLineCount(articleVOPage.get(i).getArticleExtVOS().size() / 4 + 1);
                articleVOPage.get(i).setResourceType(articleVOPage.get(i).getArticleExtVOS().get(0).getType());
            } else {
                articleVOPage.get(i).setLineCount(1);
                articleVOPage.get(i).setResourceType("");
            }
        }
        return articleVOPage.toPageInfo();
    }


    //插入正文
    private int insertArticleContent(ArticleReq articleReq) {
        ArticleContent articleContent = ArticleContent.builder()
                .articleId(articleReq.getId())
                .content(articleReq.getContent())
                .state((byte) 0)
                .createTime(JodaUtils.getCurrentDate())
                .createTime(JodaUtils.getCurrentDate())
                .build();

        int result = articleContentMapper.insertSelective(articleContent);
        return result;
    }

    /**
     * 分页列举文章
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    @Override
    public PageInfo<ArticleVO> listArticlePage(Page page,UserInfo userInfo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        com.github.pagehelper.Page<ArticleVO> articleVOPage = articleInfoExtMapper.listArticlePage(userInfo.getId());

        List<Long> ids = new ArrayList<>();
        //对每个字段赋值
        for (int i = 0; i < articleVOPage.size(); i++) {
            if (articleVOPage.get(i).getFavoriteId() != null && articleVOPage.get(i).getFavoriteId() > 0L) {
                articleVOPage.get(i).setFavorite(true);
            }
            if (articleVOPage.get(i).getLikeId() != null && articleVOPage.get(i).getLikeId() > 0L) {
                articleVOPage.get(i).setLike(true);
            }
            ids.add(articleVOPage.get(i).getId());
            if (!(articleVOPage.get(i).getArticleExts() == null || articleVOPage.get(i).getArticleExts().isEmpty())) {
                //对获取到的结果赋值vo
                List<ArticleExtVO> articleExtVOS = articleVOPage.get(i).getArticleExts().stream().map(ArticleExtVO::of).collect(Collectors.toList());
                articleVOPage.get(i).setArticleExtVOS(articleExtVOS);
                articleVOPage.get(i).setLineCount(articleVOPage.get(i).getArticleExtVOS().size() / 4 + 1);
                articleVOPage.get(i).setResourceType(articleVOPage.get(i).getArticleExtVOS().get(0).getType());
            } else {
                articleVOPage.get(i).setLineCount(1);
                articleVOPage.get(i).setResourceType("");
            }
        }
        //mq发送消息
        sendMsg.addView(ids);
        return articleVOPage.toPageInfo();
    }

    /**
     * 是否精选文章
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    @Override
    public Result<String> featured(ArticleReq articleReq) {

        ArticleInfo articleInfo = ArticleInfo.builder()
                .id(articleReq.getId())
                .overhead(FeaturedEnum.getCodeByMessage(articleReq.getFeatured()))
                .build();
        int result = articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
        if (result > 0) {
            return ResultUtil.success("操作成功！");
        }
        return ResultUtil.error("操作失败！");
    }

    /**
     * 获取个人文章数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    @Override
    public Integer getArticleNum(UserInfo userInfo) {
        return articleInfoExtMapper.getArticleNum(userInfo.getId());
    }

    @Override
    public Integer getArticleNumOfOther(Long id) {
        return articleInfoExtMapper.getArticleNum(id);
    }

    //插入媒体资源
    private int insertArticleExt(ArticleReq articleReq) {
        List<ArticleExt> articleExts = new ArrayList<>();

        //视频
        if (articleReq.getMediaUrl() != null) {
            for (String resource : articleReq.getMediaUrl()) {
                ArticleExt articleExt = ArticleExt.builder()
                        .articleId(articleReq.getId())
                        .state((byte) 0)
                        .resourceUrl(resource)
                        .type(ResourceEnum.MEDIA.getCode())
                        .createTime(JodaUtils.getCurrentDate())
                        .modifyTime(JodaUtils.getCurrentDate())
                        .build();
                articleExts.add(articleExt);
            }
        }

        //图片
        if (articleReq.getImageUrls() != null) {
            for (String resource : articleReq.getImageUrls()) {
                ArticleExt articleExt = ArticleExt.builder()
                        .articleId(articleReq.getId())
                        .state(StateEnum.NORMAL.getCode())
                        .resourceUrl(resource)
                        .type(ResourceEnum.IMAGE.getCode())
                        .createTime(JodaUtils.getCurrentDate())
                        .modifyTime(JodaUtils.getCurrentDate())
                        .build();
                articleExts.add(articleExt);
            }
        }

        //音频
        if (articleReq.getAudioUrl() != null) {
            for (String resource : articleReq.getAudioUrl()) {
                ArticleExt articleExt = ArticleExt.builder()
                        .articleId(articleReq.getId())
                        .state((byte) 0)
                        .resourceUrl(resource)
                        .type((byte) 3)
                        .createTime(JodaUtils.getCurrentDate())
                        .modifyTime(JodaUtils.getCurrentDate())
                        .build();
                articleExts.add(articleExt);
            }
        }
        System.out.println("insertArticleContent:" + articleExts);
        int extResult = articleExtMapper.insertBatch(articleExts);
        return extResult;
    }

    private void validate(Long id) {
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(id);
        if (articleInfo.getUserId() == null) {
            throw new BusinessException(BusinessEnum.NO_USERINFO);
        }
    }

    private int insertLabel(ArticleReq articleReq) {
        List<ArticleLabel> articleLabels = new ArrayList<>(articleReq.getLabelId().length);
        for (Long id : articleReq.getLabelId()) {
            ArticleLabel articleLabel = new ArticleLabel();
            articleLabel.setArticleId(articleReq.getId());
            articleLabel.setLabelId(id);
            articleLabel.setCreateTime(JodaUtils.getCurrentTime());
            articleLabels.add(articleLabel);
        }
        return articleLabelExtMapper.insertArticleLabels(articleLabels);
    }
}
