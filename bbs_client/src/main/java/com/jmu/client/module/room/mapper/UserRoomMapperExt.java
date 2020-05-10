package com.jmu.client.module.room.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.client.dto.UserRoomDTO;

import java.util.List;
import java.util.Map;

public interface UserRoomMapperExt {

    /**
     * 获取用户绑定的房间数
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    Integer getCountOfbind(@Param("userId") Long userId);

    /**
     * 列举审查房间信息
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    List<UserRoomDTO> listRoomCheck(Map<String, Object> conditions);

    /**
     * 获取审查房间详情
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    UserRoomDTO getRoomCheck(Long id);
}
