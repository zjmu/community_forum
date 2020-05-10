package com.jmu.client.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReview {
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 管理员编号
     */
    private String opCode;

    /**
     * 管理员名字
     */
    private String opName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 1：已审核，0：未审核
     */
    private Byte status;

    /**
     * 通过或设置违规原因
     */
    private String result;

    /**
     * 审核时间
     */
    private Date createTime;

    private Date modifyTime;

    public static ArticleReviewBuilder builder() {
        return new ArticleReviewBuilder();
    }
}