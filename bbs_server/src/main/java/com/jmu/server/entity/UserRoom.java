package com.jmu.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_user_room
 *
 * @author
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoom implements Serializable {
    private Long id;

    private Long roomId;

    private Long userId;

    private Byte status;

    private Byte result;

    private String opName;

    private String opCode;

    private Date createTime;

    private Date modifyTime;
}