package com.jmu.server.mapper;


import com.jmu.server.entity.BlackListHistory;

public interface BlackListHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlackListHistory record);

    int insertSelective(BlackListHistory record);

    BlackListHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlackListHistory record);

    int updateByPrimaryKey(BlackListHistory record);
}