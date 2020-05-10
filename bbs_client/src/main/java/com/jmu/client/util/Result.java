package com.jmu.client.util;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;


/**
 * 这是一个方法说明
 *
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/17 9:21
 */
@Getter
@ToString
public class Result<T> implements Serializable {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 详细异常信息
     */
    private Throwable e;

    /**
     * 具体响应数据
     */
    private T data;


    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message, T data, Throwable e) {
        this.code = code;
        this.message = message;
        this.e = e;
        this.data = data;
    }


}
