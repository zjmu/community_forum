package com.jmu.server.module.manager.service;

import com.jmu.server.req.ManagerReq;
import com.jmu.server.util.Result;

public interface ManagerService {

     /**
       * 登录
       * @author zhoujinmu
       * @date 2020/4/29
       * @since 1.0
       */
    Result<String> login(String username, String password);

     /**
       * 注册
       * @author zhoujinmu
       * @date 2020/4/29
       * @since 1.0
       */
    Result<String> register(ManagerReq managerReq);
}
