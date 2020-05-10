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
public class BlackList {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 惩罚手段
     */
    private Integer score;

    /**
     * 剩余天数
     */
    private Integer leaveDay;

    /**
     * 操作人id
     */
    private Long opId;

    /**
     * 原因
     */
    private String detail;

    /**
     * 创建时间
     */
    private Date createTime;

}