package com.jmu.client.vo;

import com.jmu.client.entity.TemplateData;
import lombok.Data;

import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/23
 * @since 1.0
 */
@Data
public class WxMessVO {

    private String touser;//用户openid
    private String template_id;//订阅消息模版id
    private String page = "pages/home/home";//默认跳到小程序首页
    private Map<String, TemplateData> data;//推送文字

}
