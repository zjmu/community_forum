package com.jmu.server.mapper;

import com.jmu.server.entity.ArticleLabel;

public interface ArticleLabelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleLabel record);

    int insertSelective(ArticleLabel record);

    ArticleLabel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleLabel record);

    int updateByPrimaryKey(ArticleLabel record);
}