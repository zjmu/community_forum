package com.jmu.server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForManagerVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 身份类型：1住户，0游客
     */
    @JsonIgnore
    private Byte type;

    private String userType;

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
    private Boolean state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    private static final long serialVersionUID = 1L;
}