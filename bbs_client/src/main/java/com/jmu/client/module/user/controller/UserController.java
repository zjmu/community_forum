package com.jmu.client.module.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.module.user.service.UserService;
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
import com.jmu.client.req.LoginReq;
import com.jmu.client.req.RoomReq;
import com.jmu.client.req.UserReq;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.util.WeiXinUtils;
import com.jmu.client.vo.UserForManagerVO;
import com.jmu.client.vo.UserVO;

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
    @Autowired
    private WeiXinUtils weiXinUtils;
    @Autowired
    private RedisUtil redisUtil;

    //登录逻辑：1.获取openId,2.查库，3.放入redis用户信息
    @GetMapping("/login")
    public Result<String> doLogin(LoginReq loginReq) {
        log.info("Start get SessionKey loginReq:{}", loginReq);
        Map<String, Object> map = new HashMap<>();
        JSONObject rawDataJson = weiXinUtils.login(loginReq.getCode());
        System.out.println("登录时服务端返回的信息：" + rawDataJson);
        String openId = (String) rawDataJson.get("openid");
        if (openId != null) {
            return userService.loginWithOpenId(openId, loginReq);
        }
        return ResultUtil.error("无法得到openId");
    }

    /**
     * 注册用户
     *
     * @param userReq 注册用户填写的信息
     * @return 提示
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/19 9:42
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> createUser(@RequestBody UserReq userReq) {
        System.out.println(userReq);
        return userService.createUser(userReq);
    }


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
     * 客户端：获取个人详细信息
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @GetMapping("/getUser")
    public Result<UserVO> getUser(@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return ResultUtil.success(userService.getUser(userInfo));
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
    public Result<List<UserVO>> listSameArea(@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return ResultUtil.success(userService.listSameArea(userInfo));
    }

    /**
     * 获取同楼栋用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @GetMapping("/listSameBuilding")
    public Result<List<UserVO>> listSameBuilding(@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return ResultUtil.success(userService.listSameBuilding(userInfo));
    }

    /**
     * 获取同单元用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @GetMapping("/listSameUnit")
    public Result<List<UserVO>> listSameUnit(@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return ResultUtil.success(userService.listSameUnit(userInfo));
    }

    /**
     * 获取同楼层用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @GetMapping("/listSameFloor")
    public Result<List<UserVO>> listSameFloor(@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return ResultUtil.success(userService.listSameFloor(userInfo));
    }

    /**
     * 客户端：更新用户信息
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @PutMapping("/updata")
    public Result<String> update(@RequestBody UserReq userReq,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return userService.update(userReq,userInfo);
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
