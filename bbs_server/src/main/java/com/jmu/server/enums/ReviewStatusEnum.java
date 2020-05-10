package com.jmu.server.enums;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/17 9:22
 * @since 1.0
 */
public enum ReviewStatusEnum {
    AUDITED((byte) 1, "已审核"),
    UNREVIEWD((byte) 0, "未审核");

    private Byte code;

    private String message;

    ReviewStatusEnum(Byte code, String message) {
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
        for (ReviewStatusEnum reviewStatusEnum : ReviewStatusEnum.values()) {
            if (reviewStatusEnum.message.equals(state)) {
                return reviewStatusEnum.code;
            }
        }
        return null;
    }

    public static String getMessageByCode(Byte param) {
        for (ReviewStatusEnum reviewStatusEnum : ReviewStatusEnum.values()) {
            if (reviewStatusEnum.code.equals(param)) {
                return reviewStatusEnum.message;
            }
        }
        return null;
    }
}
