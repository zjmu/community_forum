package com.jmu.client.module.user.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.req.LoginReq;
import com.jmu.client.req.RoomReq;
import com.jmu.client.req.UserReq;
import com.jmu.client.util.Result;
import com.jmu.client.vo.UserForManagerVO;
import com.jmu.client.vo.UserVO;

import java.util.List;

public interface UserService {

    /**
     * 注册用户
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/17 16:49
     * @since 1.0
     */
    Result<String> createUser(UserReq userReq);


    /**
     * 条件查询用户
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 11:38
     * @since 1.0
     */
    PageInfo<UserForManagerVO> selectByCondition(UserReq userReq);


    /**
     * 修改用户信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 14:57
     * @since 1.0
     */
    Result<String> updateUser(UserReq userReq);


    /**
     * 逻辑删除用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:47
     * @since 1.0
     */
    Result<String> deleteUser(Long id);

    /**
     * 客户端：根据openid展示用户信息
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    UserVO getUser(UserInfo userInfo);

    /**
     * 获取和房间绑定的用户
     *
     * @author zhoujinmu
     * @date 2020/3/3
     * @since 1.0
     */
    List<UserVO> getUserOfRoom(RoomReq roomReq);

    /**
     * 通过openId得到用户id
     *
     * @author zhoujinmu
     * @date 2020/3/11
     * @since 1.0
     */
    Result<String> loginWithOpenId(String openId, LoginReq loginReq);

    /**
     * 客户端：获取同小区用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    List<UserVO> listSameArea(UserInfo userInfo);

    /**
     * 客户端：获取同小区用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    List<UserVO> listSameUnit(UserInfo userInfo);

    /**
     * 客户端：获取同楼栋用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    List<UserVO> listSameBuilding(UserInfo userInfo);

    /**
     * 客户端：获取同楼层用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    List<UserVO> listSameFloor(UserInfo userInfo);

    /**
     * 客户端用户修改个人信息
     *
     * @author zhoujinmu
     * @date 2020/4/14
     * @since 1.0
     */
    Result<String> update(UserReq userReq,UserInfo userInfo);

    /**
     * 客户端：根据id展示用户信息
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    UserVO getUserOfOther(Long id);
}
