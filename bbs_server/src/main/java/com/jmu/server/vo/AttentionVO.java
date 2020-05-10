package com.jmu.server.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/14 15:46
 * @since 1.0
 */
@Data
public class AttentionVO implements Serializable {
    private String icon;
    private String nick;
    private String signature;
    private Long userId;
    private String roomInfo;
    private String phone;

    @JsonIgnore
    private String area;
    @JsonIgnore
    private String building;
    @JsonIgnore
    private String unit;
    @JsonIgnore
    private String floor;
    @JsonIgnore
    private String roomCode;
}
