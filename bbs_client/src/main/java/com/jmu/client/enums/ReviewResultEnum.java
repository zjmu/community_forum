package com.jmu.client.enums;

public enum ReviewResultEnum {
    PASS((byte) 1, "通过"),
    DENY((byte) 0, "拒绝");

    private Byte code;

    private String message;

    ReviewResultEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Byte getCodeByMessage(String result) {
        for (ReviewResultEnum reviewResultEnum : ReviewResultEnum.values()) {
            if (reviewResultEnum.message.equals(result)) {
                return reviewResultEnum.code;
            }
        }
        return null;
    }

    public static String getMessageByCode(Byte param) {
        for (ReviewResultEnum reviewResultEnum : ReviewResultEnum.values()) {
            if (reviewResultEnum.code.equals(param)) {
                return reviewResultEnum.message;
            }
        }
        return null;
    }
}
