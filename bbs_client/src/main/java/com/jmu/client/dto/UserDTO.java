package com.jmu.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/29
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
public class UserDTO {

    private Long id;

    private String name;

    private String icon;

    private String nick;

    private Boolean sex;

    private String username;

    private String password;

    private String signature;

    private Integer age;

    private String email;

    private String phone;

    private Integer credibility;

    /**
     * 账号状态：0可用，1冻结，2删除
     */
    private Byte state;

    private Date createTime;

    private Date modifyTime;

    private RoomDTO roomDTO;
}
