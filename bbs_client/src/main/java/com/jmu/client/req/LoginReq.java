package com.jmu.client.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/3/13
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
public class LoginReq {
    private String code;
    private String icon;
    private String nick;
    private Boolean sex;
}
