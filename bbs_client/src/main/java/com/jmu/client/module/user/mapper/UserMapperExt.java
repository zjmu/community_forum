package com.jmu.client.module.user.mapper;

import com.github.pagehelper.Page;
import com.jmu.client.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import com.jmu.client.dto.RoomDTO;
import com.jmu.client.dto.UserDTO;
import com.jmu.client.vo.UserForManagerVO;

import java.util.List;
import java.util.Map;

public interface UserMapperExt {

    /**
     * 显示所有用户信息
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    Page<UserForManagerVO> listAllUser();

    /**
     * 条件查询展示用户信息
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    Page<UserForManagerVO> getBySelect(Map<String, Object> map);

    /**
     * 小程序展示用户信息
     *
     * @author zhoujinmu
     * @date 2020/3/11
     * @since 1.0
     */
    UserDTO getUser(String openId);

    /**
     * 小程序展示用户信息
     *
     * @author zhoujinmu
     * @date 2020/3/11
     * @since 1.0
     */
    UserDTO getUserOfOther(@Param("id") Long id);

    /**
     * 通过openId获取用户信息
     *
     * @author zhoujinmu
     * @date 2020/3/11
     * @since 1.0
     */
    UserInfo getUserByOpenId(String openId);

    /**
     * 根据房屋信息获取用户
     *
     * @author zhoujinmu
     * @date 2020/3/3
     * @since 1.0
     */
    List<UserDTO> getUserOfRoom(Map<String, Object> map);

    /**
     * 条件搜索前获取用户信息
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    RoomDTO getRoomInfoOfUser(@Param("id") Long id);

    /**
     * 定时任务：增加信誉度低用户信誉
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    int addCredibility();

}
