package com.jmu.server.module.articleReview.service;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.req.ArticleReviewReq;
import com.jmu.server.req.Page;
import com.jmu.server.util.Result;
import com.jmu.server.vo.ArticleReviewVO;

public interface ArticleReviewService {


    /**
     * 审核列表
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 15:56
     * @since 1.0
     */
    PageInfo<ArticleReviewVO> listArticleReview(Page page);


    /**
     * 根据条件获取
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 17:26
     * @since 1.0
     */
    PageInfo<ArticleReviewVO> listByCondition(ArticleReviewReq articleReviewReq);

    /**
     * 审查通过
     *
     * @author zhoujinmu
     * @date 2020/2/4
     * @since 1.0
     */
    Result<String> reviewResult(ArticleReviewReq articleReviewReq, ManagerDTO managerDTO);

    /**
     * 审核不过
     *
     * @author zhoujinmu
     * @date 2020/2/6
     * @since 1.0
     */
    Result<String> disabledArticle(ArticleReviewReq articleReviewReq,ManagerDTO managerDTO);

    /**
     * 举报文章
     *
     * @author zhoujinmu
     * @date 2020/2/10
     * @since 1.0
     */
    Result<String> reportArticle(ArticleReviewReq articleReviewReq);
}
