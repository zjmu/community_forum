package com.jmu.client.module.room.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import com.jmu.client.vo.RoomVO;

import java.util.List;
import java.util.Map;

public interface RoomMapperExt {

    /**
     * 分页获取房屋信息
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    Page<RoomVO> listRoomByPage(Map<String, Object> conditions);

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
    List<String> listBuilding(@Param("area") String area);

    /**
     * 获取单元
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listUnit(@Param("area") String area, @Param("building") String building);

    /**
     * 获取楼层
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listFloor(@Param("area") String area, @Param("building") String building, @Param("unit") String unit);

    /**
     * 获取房间
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    List<String> listRoomCode(@Param("area") String area, @Param("building") String building, @Param("unit") String unit, @Param("floor") String floor);

    /**
     * 获取房间id
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    Long getIdOfRoom(@Param("area") String area, @Param("building") String building, @Param("unit") String unit, @Param("floor") String floor, @Param("roomCode") String roomCode);

    /**
     * 取消绑定用户信息
     *
     * @author zhoujinmu
     * @date 2020/2/24
     * @since 1.0
     */
    Integer deleteBindRoom(@Param("id") Long id);
}
