package com.jmu.server.req;

import lombok.Data;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/20
 * @since 1.0
 */
@Data
public class RoomReq extends Page {
    private Long id;
    private String area;
    private String building;
    private String unit;
    private String floor;
    private String roomCode;
    private String imgUrls[];
    private String checkResult;
    private String reviewStatus;
    private String opCode;
    private String endTime;
    private String startTime;
}
