package com.jmu.server.enums;

public enum SystemArticleStatuEnum {
    HAVD_SEND((byte) 1, "已发布"),
    NOT_SEND((byte) 2, "未发布"),
    DELETE((byte) 3, "已删除");
    private Byte code;

    private String message;

    SystemArticleStatuEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Byte getCodeByMessage(String status) {
        for (SystemArticleStatuEnum systemArticleStatuEnum : SystemArticleStatuEnum.values()) {
            if (systemArticleStatuEnum.message.equals(status)) {
                return systemArticleStatuEnum.code;
            }
        }
        return null;
    }

    public static String getMessageByCode(Byte param) {
        for (SystemArticleStatuEnum systemArticleStatuEnum : SystemArticleStatuEnum.values()) {
            if (systemArticleStatuEnum.code.equals(param)) {
                return systemArticleStatuEnum.message;
            }
        }
        return null;
    }
}
