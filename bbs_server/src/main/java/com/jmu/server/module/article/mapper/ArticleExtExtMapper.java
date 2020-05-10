package com.jmu.server.module.article.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.server.entity.ArticleExt;

import java.util.List;

public interface ArticleExtExtMapper {

    int deleteByArticleId(Long articleId);

    List<ArticleExt> listByArticleId(@Param("articleId") Long articleId);
}
