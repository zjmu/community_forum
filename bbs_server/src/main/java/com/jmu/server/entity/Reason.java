package com.jmu.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reason {
    /**
     * 主键
     */
    private Long id;

    /**
     * 违规原因
     */
    private String reason;

    /**
     * 分值
     */
    private Byte fraction;

    /**
     * 操作人
     */
    private Long opId;

    /**
     * 操作人编号
     */
    private String opCode;

    /**
     * 操作时间
     */
    private Date opTime;

    private Date modifyTime;
}