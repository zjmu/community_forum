package com.jmu.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_permission
 *
 * @author
 */
@Data
public class PermissionDTO implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private String url;

}