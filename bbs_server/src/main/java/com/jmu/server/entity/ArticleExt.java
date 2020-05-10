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
public class ArticleExt {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 状态：1删除，0正常
     */
    private Byte state;

    /**
     * 文章类型：1视频，2图片，3音频
     */
    private Byte type;

    /**
     * 资源url
     */
    private String resourceUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}