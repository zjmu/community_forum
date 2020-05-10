package com.jmu.server.module.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.user.service.UserService;
import com.jmu.server.req.LoginReq;
import com.jmu.server.req.RoomReq;
import com.jmu.server.req.UserReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.util.WeiXinUtils;
import com.jmu.server.vo.UserForManagerVO;
import com.jmu.server.vo.UserVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/17 9:59
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 条件查找用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:45
     * @since 1.0
     */
    @GetMapping("/getByCondition")
//    @RequiresRoles("admin")
    public Result<PageInfo<UserForManagerVO>> searchUsers(UserReq userReq) {

        return ResultUtil.success(userService.selectByCondition(userReq));
    }


    /**
     * 更新用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:46
     * @since 1.0
     */
    @PutMapping("/updataUser")
    public Result<String> updateUser(@RequestBody UserReq userReq) {
        System.out.println(userReq);
        return userService.updateUser(userReq);
    }

     /**
       * 逻辑删除
       * @author zhoujinmu
       * @date 2020/4/28
       * @since 1.0
       */
    @DeleteMapping("/deleteUser/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        System.out.println(id);
        return userService.deleteUser(id);
    }

    /**
     * 客户端：获取个人详细信息
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @GetMapping("/getUser")
    public Result<UserVO> getUser() {
        return ResultUtil.success(userService.getUser());
    }

    /**
     * 根据房间信息获取绑定人
     *
     * @author zhoujinmu
     * @date 2020/3/2
     * @since 1.0
     */
    @GetMapping("/showUserOfRoom")
    public Result<List<UserVO>> getUserOfRoom(RoomReq roomReq) {
        return ResultUtil.success(userService.getUserOfRoom(roomReq));
    }

    /**
     * 获取同小区用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @GetMapping("/listSameArea")
    public Result<List<UserVO>> listSameArea() {
        return ResultUtil.success(userService.listSameArea());
    }

    /**
     * 获取同楼栋用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @GetMapping("/listSameBuilding")
    public Result<List<UserVO>> listSameBuilding() {
        return ResultUtil.success(userService.listSameBuilding());
    }

    /**
     * 获取同单元用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @GetMapping("/listSameUnit")
    public Result<List<UserVO>> listSameUnit() {
        return ResultUtil.success(userService.listSameUnit());
    }

    /**
     * 获取同楼层用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @GetMapping("/listSameFloor")
    public Result<List<UserVO>> listSameFloor() {
        return ResultUtil.success(userService.listSameFloor());
    }

    /**
     * 客户端：更新用户信息
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @PutMapping("/updata")
    public Result<String> update(@RequestBody UserReq userReq) {
        return userService.update(userReq);
    }

    /**
     * 客户端：展示其他人信息
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @GetMapping("/getUserOfOther")
    public Result<UserVO> getUserOfOther(Long id) {
        return ResultUtil.success(userService.getUserOfOther(id));
    }
}
