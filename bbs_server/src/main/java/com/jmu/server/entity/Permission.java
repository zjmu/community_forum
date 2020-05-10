package com.jmu.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private Integer id;

    private String name;

    private String description;

    private String url;

    private Integer pid;

    private String method;
}