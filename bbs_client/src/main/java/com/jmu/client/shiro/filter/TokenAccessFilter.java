package com.jmu.client.shiro.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.enums.BaseEnum;
import com.jmu.client.module.user.mapper.UserMapperExt;
import com.jmu.client.shiro.token.JwtToken;
import com.jmu.client.util.RedisUtil;
import org.apache.shiro.web.filter.AccessControlFilter;
import com.jmu.client.util.JWTUtil;
import com.jmu.client.util.Result;
import org.bouncycastle.jcajce.provider.symmetric.VMPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * token过期机制：
 * 1.设置redis过期时间、设置token过期时间
 * 2.token过期，redis没过期，刷新token
 * 3.redis过期，token没过期通过每次交互放入redis中解决
 * 4.两个都过期说明会话结束，重新登录
 *
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/24
 * @since 1.0
 */
public class TokenAccessFilter extends AccessControlFilter {

    @Autowired
    private UserMapperExt userMapperExt;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if (((HttpServletRequest) servletRequest).getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        return false;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        System.out.println("进入filter");
        String token = ((HttpServletRequest) servletRequest).getHeader("token");
        if (token == null || token.length() == 0) {
            responseError(servletResponse, BaseEnum.UNAUTHENRICATE.getCode(), BaseEnum.UNAUTHENRICATE.getMessage());
            return false;
        }
        String username = JWTUtil.getUsername(token);

        try {
            JWTUtil.verify(token, username);
        } catch (TokenExpiredException e) {
            if(redisUtil.hashKey(token)) {
                redisUtil.del(token);
                UserInfo userInfo = userMapperExt.getUserByOpenId(username);
                String newToken = JWTUtil.sign(username);
                redisUtil.set(newToken,userInfo,7200);
                responseError(servletResponse, BaseEnum.REPRESH_TOKEN.getCode(), BaseEnum.REPRESH_TOKEN.getMessage(), newToken);
            } else  {
                responseError(servletResponse, BaseEnum.UNAUTHENRICATE.getCode(), BaseEnum.UNAUTHENRICATE.getMessage());
            }

            return false;
        } catch (Exception e) {
            responseError(servletResponse, BaseEnum.AUTHENRICATE_FAILED.getCode(), BaseEnum.AUTHENRICATE_FAILED.getMessage());
            return false;
        }
        JwtToken jwtToken = new JwtToken(token);
        getSubject(servletRequest, servletResponse).login(jwtToken);
        return true;
    }

    private void responseError(ServletResponse response, int code, String errorMsg) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        Result baseResponse = new Result(code, errorMsg, null);
        OutputStream os = httpServletResponse.getOutputStream();
        os.write(new ObjectMapper().writeValueAsString(baseResponse).getBytes("UTF-8"));
        os.flush();
        os.close();
    }

    private void responseError(ServletResponse response, int code, String errorMsg, String data) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        Result baseResponse = new Result(code, errorMsg, data);
        OutputStream os = httpServletResponse.getOutputStream();
        os.write(new ObjectMapper().writeValueAsString(baseResponse).getBytes("UTF-8"));
        os.flush();
        os.close();
    }
}
