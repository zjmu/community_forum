package com.jmu.server.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description:
 * @author: domain
 * @create: 2019/8/18 13:52
 */

public class JwtToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}