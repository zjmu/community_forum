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
public class ArticleInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章编号
     */
    private String articleCode;

    /**
     * 状态：1删除，0正常
     */
    private Byte state;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Long viewNum;

    /**
     * 收藏量
     */
    private Long favoryNum;

    /**
     * 点赞量
     */
    private Long likeNum;

    /**
     * 分享量
     */
    private Long shareNum;

    private Byte overhead;

    /**
     * 发布时间
     */
    private Date createTime;

    private Date modifyTime;

}