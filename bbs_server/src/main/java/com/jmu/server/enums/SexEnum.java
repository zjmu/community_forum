package com.jmu.server.enums;

public enum SexEnum {
    MALE(true, "男"),
    FEMALE(false, "女");

    private Boolean code;
    private String message;

    SexEnum(Boolean code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Boolean getCodeByMessage(String sex) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.message.equals(sex)) {
                return sexEnum.code;
            }
        }
        return null;
    }

    public static String getMessageByCode(Boolean param) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.code.equals(param)) {
                return sexEnum.message;
            }
        }
        return null;
    }
}
