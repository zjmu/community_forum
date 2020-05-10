package com.jmu.client.mapper;


import com.jmu.client.entity.BlackListHistory;

public interface BlackListHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlackListHistory record);

    int insertSelective(BlackListHistory record);

    BlackListHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlackListHistory record);

    int updateByPrimaryKey(BlackListHistory record);
}