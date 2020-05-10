package com.jmu.server.module.manager.controller;

import com.jmu.server.req.ManagerReq;
import com.jmu.server.req.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.manager.service.ManagerService;
import com.jmu.server.util.Result;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/25
 * @since 1.0
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /**
     * 用户登录
     *
     * @author zhoujinmu
     * @date 2020/4/25
     * @since 1.0
     */
    @PostMapping("/login")
    public Result<String> log(@RequestBody ManagerReq managerReq) {
        System.out.println(managerReq);
        return managerService.login(managerReq.getUsername(), managerReq.getPassword());
    }

     /**
       * 注册
       * @author zhoujinmu
       * @date 2020/4/29
       * @since 1.0
       */
    @PostMapping("/register")
    public Result<String> register(@RequestBody ManagerReq managerReq) {
        return managerService.register(managerReq);
    }
}
