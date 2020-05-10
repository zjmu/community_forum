package com.jmu.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/17 14:12
 * @since 1.0
 */


@Configuration
public class CORSConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //addMapping 跨域所能访问的路径
                //allowedOrigins：那些域可以访问，默认为任何域都可以访问
                System.out.println("CORSConfiguration开始执行");
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
