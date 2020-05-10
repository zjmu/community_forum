package com.jmu.server.module.manager.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.server.dto.RoleDTO;

import java.util.List;

public interface RoleExtMapper {

    List<RoleDTO> getPermission(@Param("id") Long id);
}
