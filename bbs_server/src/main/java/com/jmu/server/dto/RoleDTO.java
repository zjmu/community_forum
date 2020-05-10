package com.jmu.server.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * sys_role
 *
 * @author
 */
@Data
public class RoleDTO implements Serializable {
    private Integer id;

    private String name;

    private List<PermissionDTO> permissions;
}