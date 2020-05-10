package com.jmu.client.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
public class ReasonReq extends Page {
    /**
     * 主键
     */
    private Long id;

    /**
     * 分值
     */
    private Byte score;

    /**
     * 操作人编号
     */
    private String managerCode;

    /**
     * 违规内容
     */
    private String content;


    /**
     * 操作时间
     */
    private String startTime;
    private String endTime;

}