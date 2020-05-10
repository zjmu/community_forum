package com.jmu.server.mapper;

import com.jmu.server.entity.UserRoom;

public interface UserRoomMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoom record);

    int insertSelective(UserRoom record);

    UserRoom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoom record);

    int updateByPrimaryKey(UserRoom record);
}