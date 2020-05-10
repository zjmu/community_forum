package com.jmu.client.shiro.config;
import com.jmu.client.shiro.filter.TokenAccessFilter;
import com.jmu.client.shiro.realm.UserAuthRealm;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/23
 * @since 1.0
 */
@Configuration
public class ShiroConfig {

    @Bean
    public TokenAccessFilter tokenAccessFilter() {
        return  new TokenAccessFilter();
    }

    @Bean
    public FilterRegistrationBean registration(TokenAccessFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
    /**
     * 配置 资源访问策略 . web应用程序 shiro核心过滤器配置
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean factoryBean(@Qualifier("securityManager")SecurityManager securityManager) {
        System.out.println("开启tokenfilter");
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("token", tokenAccessFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/401");
        factoryBean.setFilterChainDefinitionMap(setFilterChainDefinitionMap());
        return factoryBean;
    }

    /**
     * 配置 SecurityManager,可配置一个或多个realm
     */
    @Bean("securityManager")
    public SessionsSecurityManager securityManager(@Qualifier("userAuthRealm")UserAuthRealm userAuthRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userAuthRealm);
        return securityManager;
    }

    @Bean
    public Authorizer authorizer(){
        return new ModularRealmAuthorizer();
    }

    @Bean("userAuthRealm")
    UserAuthRealm userAuthRealm() {
        return new UserAuthRealm();
    }


    /**
     * 开启shiro 注解支持. 使以下注解能够生效 :
     * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 配置 拦截过滤器链.  map的键 : 资源地址 ;  map的值 : 所有默认Shiro过滤器实例名
     * 默认Shiro过滤器实例 参考 : {@link org.apache.shiro.web.filter.mgt.DefaultFilter}
     */
    private Map<String, String> setFilterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/hello", "perms[" + "hello" + "]");
        filterMap.put("/logout", "logout");     //配置登出页,shiro已经帮我们实现了跳转
        filterMap.put("/user/login", "anon");
//        filterMap.put("/user/login", "token");          //所有资源都需要经过验证
        return filterMap;
    }
}
