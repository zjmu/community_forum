package com.jmu.server.mapper;

import com.jmu.server.entity.RoomCheck;

public interface RoomCheckMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoomCheck record);

    int insertSelective(RoomCheck record);

    RoomCheck selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoomCheck record);

    int updateByPrimaryKey(RoomCheck record);
}