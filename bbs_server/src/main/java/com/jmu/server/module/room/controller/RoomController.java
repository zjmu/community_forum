package com.jmu.server.module.room.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.util.RedisUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.room.service.RoomService;
import com.jmu.server.req.RoomReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.RoomCheckVO;
import com.jmu.server.vo.RoomVO;
import com.jmu.server.vo.UserRoomVO;

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
     * 展示房间信息
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @GetMapping("/listRoomByPage")
    public Result<PageInfo<RoomVO>> listRoomByPage(RoomReq roomReq) {
        log.info("RoomController listRoomByPage roomReq:{}", roomReq);
        return ResultUtil.success(roomService.listRoomByPage(roomReq));
    }

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
     * 删除房间
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @DeleteMapping("/deleteRoom/{id}")
    public Result<String> deleteRoom(@PathVariable("id") Long id) {
        int result = roomService.deleteRoom(id);
        if (result > 0) {
            return ResultUtil.success("删除房间成功！");
        }
        return ResultUtil.error("删除房间失败！");
    }

    /**
     * 创建房间
     *
     * @author zhoujinmu
     * @date 2020/2/22
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> createRoom(@RequestBody RoomReq roomReq) {
        log.info("RoomController createRoom roomReq:{}", roomReq);
        return roomService.createRoom(roomReq);
    }

    /**
     * 绑定房间
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    @PostMapping("/bindRoom")
    public Result<String> bindRoom(@RequestBody RoomReq roomReq) {
        log.info("RoomController bindRoom roomReq:{}", roomReq);
        return roomService.bindRoom(roomReq);
    }

    /**
     * 解除房间绑定
     *
     * @author zhoujinmu
     * @date 2020/2/24
     * @since 1.0
     */
    @DeleteMapping("/cancelBind")
    public Result<String> cancelBind() {
        return roomService.cancelBind();
    }

    /**
     * 列举审查信息
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    @GetMapping("/listRoomCheck")
    public Result<PageInfo<UserRoomVO>> listRoomCheck(RoomReq roomReq) {
        System.out.println(roomReq);
        return ResultUtil.success(roomService.listRoomCheck(roomReq));
    }

    /**
     * 获取审查房屋详情
     *
     * @author zhoujinmu
     * @date 2020/2/27
     * @since 1.0
     */
    @GetMapping("/getRoomCheck")
    public Result<RoomCheckVO> getRoomCheck(@RequestParam("id") Long id) {
        System.out.println(id);
        return ResultUtil.success(roomService.getRoomCheck(id));
    }

    /**
     * 房屋绑定审查结果
     *
     * @author zhoujinmu
     * @date 2020/2/27
     * @since 1.0
     */
    @PutMapping("/roomCheckResult")
    public Result<String> pass(@RequestBody RoomReq roomReq, @RequestHeader("token")String token) {
        ManagerDTO manager = new ManagerDTO();
        if(token != null) {
            manager = (ManagerDTO)redisUtil.get(token);
        }
        return roomService.roomCheckResult(roomReq,manager);
    }


}
