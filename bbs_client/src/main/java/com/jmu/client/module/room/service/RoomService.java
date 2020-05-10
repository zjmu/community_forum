package com.jmu.client.module.room.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.req.RoomReq;
import com.jmu.client.util.Result;
import com.jmu.client.vo.RoomCheckVO;
import com.jmu.client.vo.RoomVO;
import com.jmu.client.vo.UserRoomVO;

import java.util.List;

public interface RoomService {

    /**
     * 获取小区
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listArea();

    /**
     * 获取楼栋
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listBuilding(RoomReq roomReq);

    /**
     * 获取单元
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listUnit(RoomReq roomReq);

    /**
     * 获取楼层
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listFloor(RoomReq roomReq);

    /**
     * 获取房间
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listRoomCode(RoomReq roomReq);



    /**
     * 绑定房间
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    Result<String> bindRoom(RoomReq roomReq,UserInfo userInfo);

    /**
     * 解除房间绑定
     *
     * @author zhoujinmu
     * @date 2020/2/24
     * @since 1.0
     */
    Result<String> cancelBind(UserInfo userInfo);



}
