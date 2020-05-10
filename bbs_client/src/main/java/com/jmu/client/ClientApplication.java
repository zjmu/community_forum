package com.jmu.client;

import com.jmu.client.config.WxMiniProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableConfigurationProperties(WxMiniProperties.class)
@MapperScan({"com.jmu.client.mapper", "com.jmu.client.module.*.mapper"})
public class ClientApplication {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000000);
        multipartResolver.setMaxInMemorySize(0);
        return multipartResolver;
    }

    /**
     * 上传最大视频文件限制
     *
     * @author zhoujinmu
     * @date 2020/4/16
     * @since 1.0
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(1000000000));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofBytes(100000000));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
