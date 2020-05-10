package com.jmu.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.jmu.server.vo.WxMessVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/23
 * @since 1.0
 */
@Component
public class WxMessageUtil {

    @Value("${wx.login.appId}")
    private String appId;

    @Value("${wx.login.appSecret}")
    private String appSecret;

    public String push(WxMessVO wxMessVO) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMessVO, String.class);
        return responseEntity.getBody();
    }

    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID", appId);
        params.put("APPSECRET", appSecret);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        String Access_Token = object.getString("access_token");
        String expires_in = object.getString("expires_in");
        return Access_Token;
    }
}
