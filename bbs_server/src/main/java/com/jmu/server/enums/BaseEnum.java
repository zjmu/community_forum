package com.jmu.server.enums;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/17 9:22
 * @since 1.0
 */
public enum BaseEnum {
    SUCCESS(0, "成功"),
    FAILED(1, "失败"),
    UNAUTHENRICATE(2, "未认证"),
    REPRESH_TOKEN(3, "刷新token");
    private Integer code;
    private String message;

    BaseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
