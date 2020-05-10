package com.jmu.server.shiro.realm;

import com.jmu.server.module.manager.mapper.ManagerExtMapper;
import com.jmu.server.shiro.token.JwtToken;
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
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.dto.PermissionDTO;
import com.jmu.server.dto.RoleDTO;
import com.jmu.server.util.JWTUtil;

import static org.apache.shiro.util.ThreadContext.getSubject;


/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/23
 * @since 1.0
 */
@Component
@Slf4j
public class UserAuthRealm extends AuthorizingRealm {

    @Autowired
    private ManagerExtMapper managerMapper;

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
        System.out.println("认证第一步");
        String tokenPrincipal = (String) token.getPrincipal();
        String username = JWTUtil.getUsername(tokenPrincipal);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        ManagerDTO user = managerMapper.getPermissionByManagerName(username);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        return new SimpleAuthenticationInfo(tokenPrincipal, tokenPrincipal, this.getName());
    }


}
