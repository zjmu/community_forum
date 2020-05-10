package com.jmu.client.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.client.entity.ArticleExt;

import java.util.List;

public interface ArticleExtMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleExt record);

    int insertSelective(ArticleExt record);

    ArticleExt selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleExt record);

    int updateByPrimaryKey(ArticleExt record);

    int insertBatch(@Param("articleExts") List<ArticleExt> articleExts);


    /**
     * 通过article_id修改
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/13 16:12
     * @since 1.0
     */
    int updateByArticleIdSelective(ArticleExt record);
}