package com.jmu.server.mapper;

import com.jmu.server.entity.Room;

public interface RoomMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);
}