package com.jmu.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_room
 *
 * @author
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 房间编号
     */
    private String roomCode;

    /**
     * 小区名字
     */
    private String area;

    /**
     * 楼栋
     */
    private String building;

    /**
     * 单元
     */
    private String unit;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 电费
     */
    private Long electricityBill;

    /**
     * 水费
     */
    private Long waterBill;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

}