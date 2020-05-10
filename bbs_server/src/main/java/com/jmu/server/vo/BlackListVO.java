package com.jmu.server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackListVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 惩罚手段
     */
    private Integer score;

    /**
     * 剩余天数
     */
    private Integer leaveDay;

    /**
     * 操作人id
     */
    private Long opId;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;
}