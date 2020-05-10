package com.jmu.client.enums;

public enum FeaturedEnum {

    FEATURED((byte) 1, "精选"),
    UNFEATURED((byte) 0, "非精选");

    private Byte code;

    private String message;

    FeaturedEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Byte getCodeByMessage(String state) {
        for (FeaturedEnum featuredEnum : FeaturedEnum.values()) {
            if (featuredEnum.message.equals(state)) {
                return featuredEnum.code;
            }
        }
        return null;
    }

    public static String getMessageByCode(Byte param) {
        for (FeaturedEnum featuredEnum : FeaturedEnum.values()) {
            if (featuredEnum.code.equals(param)) {
                return featuredEnum.message;
            }
        }
        return null;
    }
}
