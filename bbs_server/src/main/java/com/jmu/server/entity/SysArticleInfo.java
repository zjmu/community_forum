package com.jmu.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_article_info
 *
 * @author
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysArticleInfo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 管理员id
     */
    private Long managerId;

    /**
     * 文章编号
     */
    private String articleCode;

    /**
     * 文章状态:1已发布，2未发布，3已删除
     */
    private Byte status;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面图片
     */
    private String image;

    /**
     * 浏览量
     */
    private Integer viewNum;

    /**
     * 预约发布时间
     */
    private Date sendTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * Markdown内容
     */
    private String content;

}