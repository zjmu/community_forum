package com.jmu.server.mapper;

import com.jmu.server.entity.Reason;

public interface ReasonMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Reason record);

    int insertSelective(Reason record);

    Reason selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Reason record);

    int updateByPrimaryKey(Reason record);
}