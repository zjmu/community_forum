package com.jmu.client.module.articleComment.service;

import com.jmu.client.req.ArticleCommentReq;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.Result;
import com.jmu.client.vo.ArticleCommentVO;

import java.util.List;

public interface ArticleCommentService {


    /**
     * 发评论
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/26 16:07
     * @since 1.0
     */
    Result<String> addComment(ArticleCommentReq articleCommentReq, UserInfo userInfo);

    /**
     * 添加文章评论
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    Result<String> addCommentOfArticle(ArticleCommentReq articleCommentReq, UserInfo userInfo);

    /**
     * 获取评论信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/14 9:53
     * @since 1.0
     */
    List<ArticleCommentVO> listComment(Long articleId);
}
