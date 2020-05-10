package com.jmu.server.enums;

import lombok.Getter;

@Getter
public enum BusinessEnum {

    SUCCESS(0, "成功"),
    FAILD(1, "失败"),
    CONTENT_INSERT_FAILD(2, "文章正文插入失败"),
    INFO_INSERT_FAILD(3, "文章主表插入失败"),
    EXT_INSERT_FAILD(4, "文章媒体插入失败"),
    CONTENT_DELETE_FAILD(5, "文章正文删除失败"),
    INFO_DELETE_FAILD(6, "文章主表删除失败"),
    EXT_DELETE_FAILD(7, "文章媒体删除失败"),
    NO_USERINFO(8, "缺少用户信息"),
    ARTICLE_HAD_DELETE(9, "文章已删除"),
    NO_ARTICLE(10, "无文章信息"),
    CANCEL_ATTENTION_DEFAULT(11, "取消关注失败"),
    CANCEL_FAVORITEARTICLE_DEFAULT(12, "取消收藏失败"),
    CREATE_FAVORITEARTICLE_DEFAULT(13, "收藏文章失败"),
    UNLIKEARTICLE_DEFAULT(14, "取消点赞"),
    ARTICLE_LABEL_INSERT_FAILD(15, "放入文章标签失败！"),
    IS_IN_BLACKLIST(16, "已被禁言，无法发送文章！");

    private Integer code;
    private String message;

    BusinessEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
