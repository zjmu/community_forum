package com.jmu.server.module.manager.service.impl;

import com.jmu.server.entity.Manager;
import com.jmu.server.enums.StateEnum;
import com.jmu.server.mapper.ManagerMapper;
import com.jmu.server.module.manager.mapper.ManagerExtMapper;
import com.jmu.server.req.ManagerReq;
import com.jmu.server.util.CodeGenerateUtil;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.module.manager.service.ManagerService;
import com.jmu.server.util.JWTUtil;
import com.jmu.server.util.MD5Util;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/25
 * @since 1.0
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerExtMapper managerExtMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private RedisUtil redisUtil;

     /**
       * 登录
       * @author zhoujinmu
       * @date 2020/4/29
       * @since 1.0
       */
    @Override
    public Result<String> login(String username, String password) {

        ManagerDTO manager = managerExtMapper.getPermissionByManagerName(username);
        String encryptPassword = MD5Util.encrypt(username, password);
        if (manager != null && manager.getPassword().equals(encryptPassword)) {
            String token = JWTUtil.sign(username);
            redisUtil.set(token,manager,7200);
            System.out.println(token);
            System.out.println(redisUtil.get(token));
            return ResultUtil.success(token);
        }
        return ResultUtil.error("登录失败！");
    }

     /**
       * 注册
       * @author zhoujinmu
       * @date 2020/4/29
       * @since 1.0
       */
    @Override
    public Result<String> register(ManagerReq managerReq) {
        Manager haveManager = managerExtMapper.getManagerByManagerName(managerReq.getUsername());
        if(haveManager != null) {
            return ResultUtil.error("系统存在该用户！");
        }
        Manager manager = Manager.builder()
                .managerCode(CodeGenerateUtil.generateManagerCode())
                .createTime(JodaUtils.getCurrentTime())
                .email(managerReq.getEmail())
                .phone(managerReq.getPhone())
                .password(MD5Util.encrypt(managerReq.getUsername(), managerReq.getPassword()))
                .username(managerReq.getUsername())
                .name(managerReq.getName())
                .state(StateEnum.NORMAL.getCode())
                .build();
        int result = managerMapper.insert(manager);
        if(result > 0) {
            return ResultUtil.success("注册成功,请登录！");
        }
        return ResultUtil.error("注册失败！");
    }
}
