package com.jmu.server.expection;

import com.jmu.server.enums.BusinessEnum;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/24 10:37
 * @since 1.0
 */
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(BusinessEnum businessEnum) {
        this(businessEnum.getCode(), businessEnum.getMessage());
    }
}
