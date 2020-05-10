package com.jmu.client.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_article_label
 *
 * @author
 */
@Data
public class ArticleLabel implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

}