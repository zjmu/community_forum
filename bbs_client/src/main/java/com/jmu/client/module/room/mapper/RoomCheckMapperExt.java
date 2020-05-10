package com.jmu.client.module.room.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.client.entity.RoomCheck;
import com.jmu.client.entity.UserRoom;

import java.util.List;

public interface RoomCheckMapperExt {

    /**
     * 批量插入房屋审查资源
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    Integer insertMore(@Param("roomChecks") List<RoomCheck> roomChecks);

    /**
     * 通过用户绑定的id获取审核材料
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    List<RoomCheck> listRoomCheckById(@Param("id") Long id);

    /**
     * 修改房屋审核结果
     *
     * @author zhoujinmu
     * @date 2020/2/27
     * @since 1.0
     */
    Integer updateRoomCheckResult(UserRoom userRoom);
}
