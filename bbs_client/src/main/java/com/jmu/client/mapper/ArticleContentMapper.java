package com.jmu.client.mapper;


import com.jmu.client.entity.ArticleContent;

public interface ArticleContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleContent record);

    int insertSelective(ArticleContent record);

    ArticleContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleContent record);

    int updateByPrimaryKey(ArticleContent record);

    int updateByArticleIdSelective(ArticleContent record);
}