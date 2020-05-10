package com.jmu.server.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/23 16:28
 * @since 1.0
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * openId
     */
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
    private Byte credibility;

    /**
     * 电话
     */
    private String email;

    /**
     * 状态：1删除，0正常
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}
