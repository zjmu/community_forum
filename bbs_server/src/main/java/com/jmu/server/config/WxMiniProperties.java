package com.jmu.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zjm
 * @date 2019/11/17
 */
@Data
@ConfigurationProperties(prefix = "wx.login")
@PropertySource("classpath: wx.yml")
public class WxMiniProperties {
    private String appId;
    private String appSecret;
    private String interfaceUrl;
}
