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
public class ArticleContent {
    /**
     * 主键
     */
    private Long id;

    /**
     * 问章id
     */
    private Long articleId;

    /**
     * 状态：1删除，0正常
     */
    private Byte state;

    /**
     * 正文内容
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