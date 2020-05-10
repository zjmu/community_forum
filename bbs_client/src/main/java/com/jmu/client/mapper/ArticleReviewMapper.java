package com.jmu.client.mapper;

import com.jmu.client.entity.ArticleReview;

public interface ArticleReviewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleReview record);

    int insertSelective(ArticleReview record);

    ArticleReview selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleReview record);

    int updateByPrimaryKey(ArticleReview record);
}