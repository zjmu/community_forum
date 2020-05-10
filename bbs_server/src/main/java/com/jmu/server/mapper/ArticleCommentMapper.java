package com.jmu.server.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.server.entity.ArticleComment;

import java.util.List;

public interface ArticleCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    ArticleComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    int commentCount(Long commentId);


    /**
     * 通过parentId获取评论
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/14 10:08
     * @since 1.0
     */
    List<ArticleComment> listCommentByParentId(@Param("articleId") Long articleId, @Param("parentId") Long parentId);
}