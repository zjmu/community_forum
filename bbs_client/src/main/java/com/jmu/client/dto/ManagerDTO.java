package com.jmu.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_manager
 *
 * @author
 */
@Data
public class ManagerDTO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 管理员编号
     */
    private String managerCode;

    /**
     * 昵称
     */
    private String nick;

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
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    private List<RoleDTO> roles;

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