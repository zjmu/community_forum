package com.jmu.server.mapper;


import com.jmu.server.entity.BlackList;

public interface BlackListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlackList record);

    int insertSelective(BlackList record);

    BlackList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlackList record);

    int updateByPrimaryKey(BlackList record);
}