package com.jmu.server.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
public class LabelReq extends Page {
    /**
     * 主键
     */
    private Long id;

    /**
     * 内容
     */
    private String label;

    /**
     * 操作人
     */
    private Long opId;

    /**
     * 编号
     */
    private String code;

    /**
     * 级别
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 标签状态
     */
    private String state;

    /**
     * 标签图片
     */
    private String imageUrl;

}