package com.jmu.server.enums;

import lombok.Getter;

@Getter
public enum ResourceEnum {
    MEDIA((byte) 1, "视频"),
    IMAGE((byte) 2, "图片"),
    AUDIO((byte) 3, "音频");

    private Byte code;
    private String message;

    ResourceEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResourceEnum getMenu(byte index) {
        for (ResourceEnum resource : ResourceEnum.values()) {
            if (resource.getCode() == index) {
                return resource;
            }
        }
        return null;
    }
}
