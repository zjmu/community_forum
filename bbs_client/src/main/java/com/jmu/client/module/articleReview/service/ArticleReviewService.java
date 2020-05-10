package com.jmu.client.module.articleReview.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.req.ArticleReviewReq;
import com.jmu.client.req.Page;
import com.jmu.client.util.Result;
import com.jmu.client.vo.ArticleReviewVO;

public interface ArticleReviewService {

    /**
     * 举报文章
     *
     * @author zhoujinmu
     * @date 2020/2/10
     * @since 1.0
     */
    Result<String> reportArticle(ArticleReviewReq articleReviewReq);
}
