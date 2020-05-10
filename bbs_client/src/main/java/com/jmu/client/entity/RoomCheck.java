package com.jmu.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_room_check
 *
 * @author
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RoomCheck implements Serializable {
    private Long id;

    /**
     * 用户绑定房间的记录
     */
    private Long userRoomId;

    /**
     * 图片路径
     */
    private String imgurl;

    private Date createTime;

    private Date modifyTime;

}