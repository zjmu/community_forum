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
public class BlackListHistory {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 操作人id
     */
    private Long opId;

    /**
     * 惩罚类型
     */
    private String type;

    /**
     * 原因
     */
    private String detail;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * s
     */
    private Date modifyTime;
}