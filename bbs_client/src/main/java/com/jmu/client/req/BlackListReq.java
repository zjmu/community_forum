package com.jmu.client.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BlackListReq extends Page {

    private Long userId;
    /**
     * 用户名字
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 剩余天数
     */
    private Integer leaveDay;

    private Integer score;

    private String detail;

    /**
     * 创建的时间范围
     */
    private String startTime;
    private String endTime;

}