package com.jmu.client.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentReq {

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 父级：0主文章，其余评论父id
     */
    private Long parentId;

    /**
     * 文章内容
     */
    private String content;


}