package com.jmu.client.enums;

public enum StateEnum {
    DELETE((byte) 1, "已删除"),
    NORMAL((byte) 0, "正常");

    private Byte code;

    private String message;

    StateEnum(Byte code, String message) {
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
        for (StateEnum stateEnum : StateEnum.values()) {
            if (stateEnum.message.equals(state)) {
                return stateEnum.code;
            }
        }
        return null;
    }

    public static String getMessageByCode(Byte param) {
        for (StateEnum stateEnum : StateEnum.values()) {
            if (stateEnum.code.equals(param)) {
                return stateEnum.message;
            }
        }
        return null;
    }
}
