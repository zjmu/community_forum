package com.jmu.client.shiro.realm;

import com.jmu.client.dto.UserDTO;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.module.user.mapper.UserMapperExt;
import com.jmu.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jmu.client.dto.ManagerDTO;
import com.jmu.client.dto.PermissionDTO;
import com.jmu.client.dto.RoleDTO;
import com.jmu.client.shiro.token.JwtToken;
import com.jmu.client.util.JWTUtil;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/23
 * @since 1.0
 */
@Component
@Slf4j
public class UserAuthRealm extends AuthorizingRealm {

    @Autowired
    private UserMapperExt userMapperExt;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 权限核心配置 根据数据库中的该用户 角色 和 权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("认证权限：" + principals);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();         //获取 角色
            authorizationInfo.addRole(null);                      //添加 角色
            authorizationInfo.addStringPermission(null);//添加 权限
        return authorizationInfo;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 用户登陆 凭证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String tokenPrincipal = (String) token.getPrincipal();
        String openId = JWTUtil.getUsername(tokenPrincipal);
        System.out.println("用户名称：" + openId);
        if (openId == null) {
            throw new AuthenticationException("token invalid");
        }
        UserInfo user = userMapperExt.getUserByOpenId(openId);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if(!redisUtil.hashKey(tokenPrincipal)) {
            redisUtil.set(tokenPrincipal,user);
        }
        return new SimpleAuthenticationInfo(tokenPrincipal, tokenPrincipal, this.getName());
    }


}
