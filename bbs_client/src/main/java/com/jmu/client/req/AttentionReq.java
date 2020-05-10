package com.jmu.client.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttentionReq {
    /**
     * 主键
     */
    private Long id;

    /**
     * 关注人id
     */
    private Long userId;

    /**
     * 被关注人id
     */
    private Long attentionUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}