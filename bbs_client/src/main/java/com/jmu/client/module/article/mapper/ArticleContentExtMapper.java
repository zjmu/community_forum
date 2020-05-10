package com.jmu.client.module.article.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.jmu.client.entity.ArticleContent;

@Mapper
public interface ArticleContentExtMapper {


    int deleteByArticleId(@Param("articleId") Long articleId);

    ArticleContent getByArticleId(@Param("articleId") Long articleId);


}