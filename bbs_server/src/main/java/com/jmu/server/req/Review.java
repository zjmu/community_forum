package com.jmu.server.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    /**
     * 主键
     */
    private Long id;

    /**
     * 评审编号
     */
    private String reviewCode;

    /**
     * 类型：1文章，2评论
     */
    private Byte type;

    /**
     * 管理员id
     */
    private Integer managerId;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 评审状态：0未评，1已评
     */
    private Boolean isReview;

    /**
     * 详情
     */
    private String detail;

    /**
     * 时间
     */
    private Date time;

    /**
     * 修改时间
     */
    private Date modifyTime;
}