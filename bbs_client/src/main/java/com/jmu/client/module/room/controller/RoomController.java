package com.jmu.client.module.room.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.module.room.service.RoomService;
import com.jmu.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.client.req.RoomReq;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.RoomCheckVO;
import com.jmu.client.vo.RoomVO;
import com.jmu.client.vo.UserRoomVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/18
 * @since 1.0
 */
@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取小区
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @GetMapping("/listArea")
    public Result<List<String>> listArea() {
        return ResultUtil.success(roomService.listArea());
    }

    /**
     * 获取楼栋
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @GetMapping("/listBuilding")
    public Result<List<String>> listBuilding(RoomReq roomReq) {
        try {
            List<String> result = roomService.listBuilding(roomReq);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 获取单元
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @GetMapping("/listUnit")
    public Result<List<String>> listUnit(RoomReq roomReq) {
        try {
            List<String> result = roomService.listUnit(roomReq);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 获取楼层
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @GetMapping("/listFloor")
    public Result<List<String>> listFloor(RoomReq roomReq) {
        try {
            List<String> result = roomService.listFloor(roomReq);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 获取房间
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @GetMapping("/listRoomCode")
    public Result<List<String>> listRoomCode(RoomReq roomReq) {
        try {
            List<String> result = roomService.listRoomCode(roomReq);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }



    /**
     * 绑定房间
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    @PostMapping("/bindRoom")
    public Result<String> bindRoom(@RequestBody RoomReq roomReq, @RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        log.info("RoomController bindRoom roomReq:{}", roomReq);
        return roomService.bindRoom(roomReq,userInfo);
    }

    /**
     * 解除房间绑定
     *
     * @author zhoujinmu
     * @date 2020/2/24
     * @since 1.0
     */
    @DeleteMapping("/cancelBind")
    public Result<String> cancelBind(@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        return roomService.cancelBind(userInfo);
    }





}
