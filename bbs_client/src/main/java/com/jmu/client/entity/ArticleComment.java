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
public class ArticleComment {
    /**
     * 主键
     */
    private Long id;

    /**
     * 状态：1删除，0正常
     */
    private Byte state;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 类型：0文章的评论，1评论的评论
     */
    private Boolean type;

    /**
     * 父级：0主文章，其余评论父id
     */
    private Long parentId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}