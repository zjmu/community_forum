package com.jmu.server.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerReq {
    private String username;
    private String name;
    private String email;
    private String phone;
    private String password;
}