package com.jmu.client.module.articleComment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.client.entity.ArticleComment;
import com.jmu.client.enums.BaseEnum;
import com.jmu.client.enums.StateEnum;
import com.jmu.client.mapper.ArticleCommentMapper;
import com.jmu.client.mapper.ArticleInfoMapper;
import com.jmu.client.module.articleComment.service.ArticleCommentService;
import com.jmu.client.req.ArticleCommentReq;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleCommentVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/26 14:23
 * @since 1.0
 */
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Autowired
    private ArticleInfoMapper articleInfoMapper;


    /**
     * 文章评论
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/26 16:06
     * @since 1.0
     */
    @Override
    public Result<String> addComment(ArticleCommentReq articleCommentReq, UserInfo userInfo) {
        Result<String> validateResult = validate(articleCommentReq, userInfo);
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }

        ArticleComment articleComment = new ArticleComment();
        articleComment.setState(StateEnum.NORMAL.getCode());
        articleComment.setArticleId(articleCommentReq.getArticleId());
        articleComment.setContent(articleCommentReq.getContent());
        articleComment.setParentId(articleCommentReq.getParentId());
        articleComment.setNick(userInfo.getNickname());
        articleComment.setUserId(userInfo.getId());
        articleComment.setCreateTime(JodaUtils.getCurrentDate());
        articleComment.setModifyTime(JodaUtils.getCurrentDate());
        int result = articleCommentMapper.insert(articleComment);
        if (result > 0) {
            return ResultUtil.success("回复成功");
        }
        return ResultUtil.error("回复失败");
    }

    /**
     * 添加文章评论
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    @Override
    public Result<String> addCommentOfArticle(ArticleCommentReq articleCommentReq, UserInfo userInfo) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setState(StateEnum.NORMAL.getCode());
        articleComment.setArticleId(articleCommentReq.getArticleId());
        articleComment.setContent(articleCommentReq.getContent());
        articleComment.setParentId(0L);
        articleComment.setNick(userInfo.getNickname());
        articleComment.setUserId(userInfo.getId());
        articleComment.setCreateTime(JodaUtils.getCurrentDate());
        articleComment.setModifyTime(JodaUtils.getCurrentDate());
        int result = articleCommentMapper.insert(articleComment);
        if (result > 0) {
            return ResultUtil.success("文章评论成功");
        }
        return ResultUtil.error("文章评论失败");
    }

    @Override
    public List<ArticleCommentVO> listComment(Long articleId) {
        List<ArticleCommentVO> comments = new ArrayList<>();
        List<ArticleComment> articleComments = articleCommentMapper.listCommentByParentId(articleId, 0L);
        System.out.println(articleComments);
        comments.addAll(listChildComment(articleId, articleComments, null, null, new ArrayList<>(), new ArrayList<>()));
        return comments;

    }

    private List<ArticleCommentVO> listChildComment(Long articleId, List<ArticleComment> articleComments, String nick, Long userId, List<ArticleComment> comments, List<ArticleCommentVO> articleCommentVOS) {

        for (ArticleComment articleComment : articleComments) {
            ArticleCommentVO articleCommentVO = new ArticleCommentVO();
            articleCommentVO.setId(articleComment.getId());
            articleCommentVO.setSendNick(articleComment.getNick());
            articleCommentVO.setText(articleComment.getContent());
            articleCommentVO.setSendUserId(articleComment.getUserId());
            articleCommentVO.setReturnNick(nick);
            articleCommentVO.setReturnUserId(userId);
            articleCommentVOS.add(articleCommentVO);
            comments.add(articleComment);
            Long id = articleComment.getId();
            String returnNick = articleComment.getNick();
            Long returnUserId = articleComment.getUserId();
            List<ArticleComment> articleCommentList = articleCommentMapper.listCommentByParentId(articleId, id);
            if (articleCommentList != null && articleCommentList.size() > 0) {
                listChildComment(articleId, articleCommentList, returnNick, returnUserId, comments, articleCommentVOS);
            }
        }
        return articleCommentVOS;
    }

    private Result<String> validate(ArticleCommentReq articleCommentReq, UserInfo userInfo) {
        if (articleCommentReq.getArticleId() < 0 || articleCommentReq.getParentId() < 0) {
            return ResultUtil.error("评论请求出错");
        }
        //验证文章存在
        if (articleInfoMapper.countArticle(articleCommentReq.getArticleId()) <= 0) {
            return ResultUtil.error("无此文章");
        }
        return ResultUtil.success();
    }
}
