package com.jmu.server.mapper;


import com.jmu.server.entity.ManagerRole;

public interface ManagerRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerRole record);

    int insertSelective(ManagerRole record);

    ManagerRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerRole record);

    int updateByPrimaryKey(ManagerRole record);
}