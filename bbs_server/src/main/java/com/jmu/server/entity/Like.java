package com.jmu.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    /**
     * 主键
     */
    private Long id;

    /**
     * 点赞用户
     */
    private Long userId;

    /**
     * 点赞文章
     */
    private Long articleId;

    /**
     * 点赞时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}