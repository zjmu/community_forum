package com.jmu.server.mapper;


import com.jmu.server.entity.ArticleContent;

public interface ArticleContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleContent record);

    int insertSelective(ArticleContent record);

    ArticleContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleContent record);

    int updateByPrimaryKey(ArticleContent record);

    int updateByArticleIdSelective(ArticleContent record);
}