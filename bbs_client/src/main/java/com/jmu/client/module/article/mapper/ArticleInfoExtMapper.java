package com.jmu.client.module.article.mapper;


import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import com.jmu.client.vo.ArticleVO;

import java.util.List;

public interface ArticleInfoExtMapper {

    Page<ArticleVO> listArticleByUserId(@Param("userId") Long userId);

    Page<ArticleVO> listArticlePage(@Param("userId") Long userId);

    int addArticleView(List<Long> ids);

    /**
     * 获取作者文章数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getArticleNum(@Param("userId") Long userId);
}