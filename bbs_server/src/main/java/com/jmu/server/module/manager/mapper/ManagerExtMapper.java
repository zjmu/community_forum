package com.jmu.server.module.manager.mapper;

import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.entity.Manager;

public interface ManagerExtMapper {

    ManagerDTO getPermissionByManagerName(String name);

    Manager getManagerByManagerName(String name);
}
