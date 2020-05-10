package com.jmu.client.expection;

import com.jmu.client.enums.BusinessEnum;
import lombok.Data;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/24 10:37
 * @since 1.0
 */
@Data
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
