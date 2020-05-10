package com.jmu.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Manager implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 管理员编号
     */
    private String managerCode;


    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 名字
     */
    private String name;

    /**
     * 状态：0删除，1可用，2冻结
     */
    private Byte state;


    /**
     * 邮箱
     */
    private String email;

    private List<Role> roles;

    /**
     * 电话
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date modifyTime;
}