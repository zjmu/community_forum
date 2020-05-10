package com.jmu.server.mapper;


import com.jmu.server.entity.PermissionRole;

public interface PermissionRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PermissionRole record);

    int insertSelective(PermissionRole record);

    PermissionRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PermissionRole record);

    int updateByPrimaryKey(PermissionRole record);
}