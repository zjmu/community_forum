package com.jmu.server.enums;

public enum UserTypeEnum {
    VISITER((byte) 1, "住户"),
    HOUSEHOLD((byte) 0, "游客");

    private Byte code;

    private String message;

    UserTypeEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessageByCode(Byte param) {
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            if (userTypeEnum.getCode().equals(param)) {
                return userTypeEnum.message;
            }
        }
        return null;
    }

    public static Byte getCodeByMessage(String param) {
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            if (userTypeEnum.getMessage().equals(param)) {
                return userTypeEnum.code;
            }
        }
        return null;
    }
}
