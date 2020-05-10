package com.jmu.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Label {
    /**
     * 主键
     */
    private Long id;

    /**
     * 内容
     */
    private String label;

    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 操作人
     */
    private String opCode;

    /**
     * 编号
     */
    private String code;

    /**
     * 级别
     */
    private Integer weight;

    private Byte state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}