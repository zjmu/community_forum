package com.jmu.client.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRole {
    private Integer id;

    private Integer managerId;

    private Integer roleId;
}