package com.jmu.client.module.favority.mapper;

import org.apache.ibatis.annotations.Param;

public interface FavoriteExtMapper {

    Long selectFavoriteOfUser(@Param("articleId") Long articleId, @Param("userId") Long userId);

    /**
     * 获得用户的收藏数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getFavoriteNum(@Param("userId") Long userId);
}
