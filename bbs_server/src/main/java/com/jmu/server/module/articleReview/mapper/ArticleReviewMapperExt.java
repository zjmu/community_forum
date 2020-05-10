package com.jmu.server.module.articleReview.mapper;


import com.github.pagehelper.Page;
import com.jmu.server.vo.ArticleReviewVO;

import java.util.Map;

public interface ArticleReviewMapperExt {

    Page<ArticleReviewVO> listArticleReview();


    /**
     * 根据条件查询
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 17:27
     * @since 1.0
     */
    Page<ArticleReviewVO> listByCondition(Map<String, Object> map);

    Integer getByArticleId(Long articleId);
}