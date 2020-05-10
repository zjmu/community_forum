package com.jmu.client.module.articleComment.mapper;

import org.apache.ibatis.annotations.Param;

public interface ArticleCommentExtMapper {

    /**
     * 更新用户信息时更新昵称
     *
     * @author zhoujinmu
     * @date 2020/4/20
     * @since 1.0
     */
    int updateNickByUserId(@Param("userId") Long userId, @Param("nick") String nick);
}
