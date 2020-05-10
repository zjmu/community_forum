package com.jmu.client.module.article.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.client.entity.ArticleExt;

import java.util.List;

public interface ArticleExtExtMapper {

    int deleteByArticleId(Long articleId);

    List<ArticleExt> listByArticleId(@Param("articleId") Long articleId);
}
