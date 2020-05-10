package com.jmu.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 主键
     */
    private Long id;

    private String openId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户名字
     */
    private String name;

    /**
     * 头像
     */
    private String icon;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 性别,1男，2女
     */
    private Boolean sex;

    /**
     * 邮箱
     */
    private String phone;

    /**
     * 信誉度：满分100，低于80加入黑名单
     */
    private Integer credibility;

    /**
     * 电话
     */
    private String email;

    /**
     * 状态：1删除，0正常
     */
    private Byte state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 用户类型：1：住户 0：游客
     */
    private Byte type;
}