package com.jmu.client.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/17 16:04
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class UserReq extends Page {

    private Long id;
    private String username;
    private String name;
    private String icon;
    private String signature;
    private String nickname;
    private String email;
    private String sex;
    private String phone;
    private Byte age;
    private Integer credibility;
    private String userType;

    /**
     * 查询时使用时间范围
     */
    private String startTime;
    private String endTime;

    /**
     * 用户状态
     */
    private String state;

}
