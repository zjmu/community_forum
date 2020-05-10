package com.jmu.client.mapper;


import com.jmu.client.entity.BlackList;

public interface BlackListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlackList record);

    int insertSelective(BlackList record);

    BlackList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlackList record);

    int updateByPrimaryKey(BlackList record);
}