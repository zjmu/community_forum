package com.jmu.server.module.room.service;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.req.RoomReq;
import com.jmu.server.util.Result;
import com.jmu.server.vo.RoomCheckVO;
import com.jmu.server.vo.RoomVO;
import com.jmu.server.vo.UserRoomVO;

import java.util.List;

public interface RoomService {

    /**
     * 分页获取房屋信息
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    PageInfo<RoomVO> listRoomByPage(RoomReq roomReq);

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
     * 删除房间信息
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    int deleteRoom(Long id);

    /**
     * 新建房屋
     *
     * @author zhoujinmu
     * @date 2020/2/22
     * @since 1.0
     */
    Result<String> createRoom(RoomReq roomReq);

    /**
     * 绑定房间
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    Result<String> bindRoom(RoomReq roomReq);

    /**
     * 解除房间绑定
     *
     * @author zhoujinmu
     * @date 2020/2/24
     * @since 1.0
     */
    Result<String> cancelBind();

    /**
     * 获取审核列表
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    PageInfo<UserRoomVO> listRoomCheck(RoomReq roomReq);

    /**
     * 审查房间详情
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    RoomCheckVO getRoomCheck(Long id);

    /**
     * 房屋绑定审查结果
     *
     * @author zhoujinmu
     * @date 2020/2/27
     * @since 1.0
     */
    Result<String> roomCheckResult(RoomReq roomReq, ManagerDTO managerDTO);

}
