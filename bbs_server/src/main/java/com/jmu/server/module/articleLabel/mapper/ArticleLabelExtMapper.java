package com.jmu.server.module.articleLabel.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import com.jmu.server.entity.ArticleLabel;
import com.jmu.server.vo.ArticleVO;

import java.util.List;

public interface ArticleLabelExtMapper {

    /**
     * 添加文章时放入标签信息
     *
     * @author zhoujinmu
     * @date 2020/2/11
     * @since 1.0
     */
    int insertArticleLabels(@Param("articleLabels") List<ArticleLabel> articleLabels);

    Page<ArticleVO> listArticleOfLabel(@Param("labelId") Long labelId, @Param("userId") Long userId);
}
