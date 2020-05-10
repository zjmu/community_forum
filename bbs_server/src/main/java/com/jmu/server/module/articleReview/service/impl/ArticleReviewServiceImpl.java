package com.jmu.server.module.articleReview.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.module.articleReview.service.ArticleReviewService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jmu.server.entity.ArticleInfo;
import com.jmu.server.entity.ArticleReview;
import com.jmu.server.entity.Manager;
import com.jmu.server.entity.User;
import com.jmu.server.enums.ReviewStatusEnum;
import com.jmu.server.enums.FeaturedEnum;
import com.jmu.server.enums.StateEnum;
import com.jmu.server.mapper.ArticleInfoMapper;
import com.jmu.server.mapper.ArticleReviewMapper;
import com.jmu.server.mapper.UserMapper;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.articleReview.mapper.ArticleReviewMapperExt;
import com.jmu.server.module.blackList.service.BlackListService;
import com.jmu.server.req.ArticleReviewReq;
import com.jmu.server.req.Page;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.ArticleReviewVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/21 15:54
 * @since 1.0
 */
@Service
@Slf4j
public class ArticleReviewServiceImpl implements ArticleReviewService {

    private static final String PASS = "通过";
    @Value("${disable.score}")
    private String scoreValue;

    @Autowired
    private ArticleReviewMapperExt articleReviewMapperExt;
    @Autowired
    private ArticleReviewMapper articleReviewMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleInfoMapper articleInfoMapper;
    @Autowired
    private BlackListService blackListService;


    /**
     * 这是一个方法说明
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 17:26
     * @since 1.0
     */
    @Override
    public PageInfo<ArticleReviewVO> listArticleReview(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        com.github.pagehelper.Page<ArticleReviewVO> articleReviewVOS = articleReviewMapperExt.listArticleReview();
        for (int i = 0; i < articleReviewVOS.size(); i++) {
            articleReviewVOS.get(i).setStatusString(ReviewStatusEnum.getMessageByCode(articleReviewVOS.get(i).getStatus()));
        }
        return articleReviewVOS.toPageInfo();
    }


    /**
     * 根据条件获取
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 17:26
     * @since 1.0
     */
    @Override
    public PageInfo<ArticleReviewVO> listByCondition(ArticleReviewReq articleReviewReq) {
        Map<String, Object> condition = new HashMap<>();
        if (!StringUtils.isBlank(articleReviewReq.getArticleCode())) {
            condition.put("articleCode", articleReviewReq.getArticleCode());
        }
        condition.put("userId", articleReviewReq.getUserId());
        if (!StringUtils.isBlank(articleReviewReq.getReviewStatus())) {
            condition.put("status", ReviewStatusEnum.getCodeByMessage(articleReviewReq.getReviewStatus()));
        }
        if (!StringUtils.isBlank(articleReviewReq.getStartTime())) {
            condition.put("startTime", articleReviewReq.getStartTime());
        }
        if (!StringUtils.isBlank(articleReviewReq.getEndTime())) {
            condition.put("endTime", articleReviewReq.getEndTime());
        }
        com.github.pagehelper.Page<ArticleReviewVO> page = articleReviewMapperExt.listByCondition(condition);
        for (int i = 0; i < page.size(); i++) {
            page.get(i).setStatusString(ReviewStatusEnum.getMessageByCode(page.get(i).getStatus()));
            page.get(i).setFeatured(FeaturedEnum.getMessageByCode(page.get(i).getOverhead()));
        }
        return page.toPageInfo();
    }

    /**
     * 文章审核通过
     *
     * @author zhoujinmu
     * @date 2020/2/6
     * @since 1.0
     */
    @Override
    @Transactional
    public Result<String> reviewResult(ArticleReviewReq articleReviewReq, ManagerDTO manager) {
        ArticleReview oldData = articleReviewMapper.selectByPrimaryKey(articleReviewReq.getId());
        if (oldData.getResult() != null && oldData.getResult().equals(PASS)) {
            return ResultUtil.error("文章已通过审核！");
        }
        ArticleReview articleReview = ArticleReview.builder()
                .id(articleReviewReq.getId())
                .status(ReviewStatusEnum.AUDITED.getCode())
                .opCode(manager.getManagerCode())
                .opName(manager.getName())
                .result(PASS)
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int articleReviewResult = articleReviewMapper.updateByPrimaryKeySelective(articleReview);
        ArticleInfo articleInfo = ArticleInfo.builder().id(articleReviewReq.getArticleId()).state(StateEnum.NORMAL.getCode()).build();
        int articleInfoResult = articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
        if (articleReviewResult > 0 && articleInfoResult > 0) {
            return ResultUtil.success("文章审查通过成功！");
        }
        return ResultUtil.error("文章审查通过失败！");
    }

    /**
     * 审核不过
     *
     * @author zhoujinmu
     * @date 2020/2/6
     * @since 1.0
     */
    @Override
    @Transactional
    public Result<String> disabledArticle(ArticleReviewReq articleReviewReq,ManagerDTO manager) {

        validateBeforeDisablled(articleReviewReq);
        ArticleReview articleReview = ArticleReview.builder()
                .id(articleReviewReq.getId())
                .status(ReviewStatusEnum.AUDITED.getCode())
                .opCode(manager.getManagerCode())
                .opName(manager.getName())
                .result(articleReviewReq.getContent())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int result = articleReviewMapper.updateByPrimaryKeySelective(articleReview);
        //扣除用户信誉,扣除后判断2
        User articleUser = userMapper.selectByPrimaryKey(articleReviewReq.getUserId());
        if (articleUser == null) {
            return ResultUtil.error("用户不存在！");
        }
        int credibility = articleUser.getCredibility() - articleReviewReq.getScore();
        User user;
        //信誉度过低加入黑名单
        if (credibility < Integer.parseInt(scoreValue)) {
            blackListService.createBlackList(articleReviewReq.getUserId(), credibility, articleReviewReq.getContent(),manager);
            blackListService.createBlackListHistory(articleReviewReq.getUserId(), credibility, articleReviewReq.getContent(),manager);
        }
        user = User.builder()
                .id(articleReviewReq.getUserId())
                .credibility(credibility)
                .build();
        int result1 = userMapper.updateByPrimaryKeySelective(user);
        //修改文章状态
        ArticleInfo articleInfo = ArticleInfo.builder()
                .id(articleReviewReq.getArticleId())
                .state(StateEnum.DELETE.getCode())
                .build();
        int result2 = articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
        if (result > 0 && result1 > 0 && result2 > 0) {
            return ResultUtil.success("文章禁止成功！");
        }
        return ResultUtil.error("文章禁止失败！");
    }

    /**
     * 举报文章
     *
     * @author zhoujinmu
     * @date 2020/2/10
     * @since 1.0
     */
    @Override
    public Result<String> reportArticle(ArticleReviewReq articleReviewReq) {
        Integer num = articleReviewMapperExt.getByArticleId(articleReviewReq.getArticleId());
        if (num > 0) {
            return ResultUtil.success("举报成功！");
        }
        ArticleReview articleReview = ArticleReview.builder()
                .articleId(articleReviewReq.getArticleId())
                .userId(articleReviewReq.getUserId())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int result = articleReviewMapper.insertSelective(articleReview);
        if (result > 0) {
            return ResultUtil.success("举报成功！");
        }
        return ResultUtil.error("举报失败！");
    }

    private Result<String> validateBeforeDisablled(ArticleReviewReq articleReviewReq) {
        if (articleReviewReq.getArticleId() == null) {
            return ResultUtil.error("文章id不能为空");
        }
        if (articleReviewReq.getUserId() == null) {
            return ResultUtil.error("用户id不能为空");
        }
        if (articleReviewReq.getContent() == null) {
            return ResultUtil.error("请输入禁止原因");
        }
        if (articleReviewReq.getScore() == null) {
            return ResultUtil.error("请输入需要扣除信誉分");
        }

        return ResultUtil.success("验证成功！");
    }
}
