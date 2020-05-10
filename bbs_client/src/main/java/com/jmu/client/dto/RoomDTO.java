package com.jmu.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/29
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RoomDTO {
    private String roomCode;
    private String area;
    private String building;
    private String unit;
    private String floor;
    private Date createTime;
    private Byte result;
}
