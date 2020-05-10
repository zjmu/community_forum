package com.jmu.client.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReq implements Serializable {

    private Long id;

    /**
     * 标题
     */
    private String title;

    private String content;

    /**
     * 视频
     */
    private String[] mediaUrl;

    private Long userId;
    /**
     * 图片
     */
    private String[] imageUrls;

    /**
     * 音频
     */
    private String[] audioUrl;

    private Long[] labelId;

    /**
     * 精选
     */
    private String featured;

}