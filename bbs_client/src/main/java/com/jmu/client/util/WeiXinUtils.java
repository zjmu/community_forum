package com.jmu.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jmu.client.config.WxMiniProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author zjm
 * @date 2019/11/17
 */
@Slf4j
@Component
public class WeiXinUtils {
    @Autowired
    private WxMiniProperties miniProperties;
    public static WxMiniProperties properties;

    @PostConstruct
    private void init() {
        properties = this.miniProperties;
        System.out.println(this.miniProperties);
        System.out.println(properties);
    }

    public JSONObject login(String code) {
        log.info("------小程序登录方法开始--------");
        String url = properties.getInterfaceUrl() + "/sns/jscode2session?appid=" + properties.getAppId() + "&secret=" +
                properties.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject message;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            message = JSON.parseObject(response);
        } catch (Exception e) {
            log.error("微信服务器请求错误", e);
            message = new JSONObject();
        }
        log.info("message" + message.toString());
        log.info("--------小程序登录方法结束-----------");
        return message;
    }

}
