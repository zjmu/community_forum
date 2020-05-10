package com.jmu.client.module.attention.mapper;

import org.apache.ibatis.annotations.Param;

public interface AttentionExtMapper {

    /**
     * 是否关注过
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer selectAttention(@Param("userId") Long userId, @Param("attentionUserId") Long attentionUserId);

    /**
     * 获取关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getAttentionNum(@Param("userId") Long userId);
}
