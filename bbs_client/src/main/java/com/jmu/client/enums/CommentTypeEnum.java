package com.jmu.client.enums;

import lombok.Getter;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/26 15:17
 * @since 1.0
 */
@Getter
public enum CommentTypeEnum {
    ARTICLE_COMMENT(true, "文章的评论"),
    COMMENT_COMMENT(false, "评论的评论");

    private Boolean type;
    private String message;

    CommentTypeEnum(Boolean type, String message) {
        this.type = type;
        this.message = message;
    }
}
