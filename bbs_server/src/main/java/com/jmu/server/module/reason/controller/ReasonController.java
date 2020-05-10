package com.jmu.server.module.reason.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.reason.service.ReasonService;
import com.jmu.server.req.ReasonReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.ReasonVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/4
 * @since 1.0
 */
@RestController
@RequestMapping("/reason")
@Slf4j
public class ReasonController {

    @Autowired
    private ReasonService reasonService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 增加违规原因
     *
     * @author zhoujinmu
     * @date 2020/2/5
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> create(@RequestBody ReasonReq reasonReq,@RequestHeader("token")String token) {
        log.info("ReasonController create reasonReq:{}", reasonReq);
        ManagerDTO managerDTO = new ManagerDTO();
        if(token !=null ) {
            managerDTO = (ManagerDTO)redisUtil.get(token);
        }
        return reasonService.create(reasonReq,managerDTO);
    }

    /**
     * 逻辑删除
     *
     * @author zhoujinmu
     * @date 2020/2/5
     * @since 1.0
     */
    @DeleteMapping("/tombstone/{id}")
    public Result<String> tombstone(@PathVariable("id") Long id) {
        return null;
    }

    /**
     * 物理删除
     *
     * @author zhoujinmu
     * @date 2020/2/5
     * @since 1.0
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Long id) {
        log.info("delete id:{}", id);
        return reasonService.deleteReason(id);
    }

    /**
     * 修改内容
     *
     * @author zhoujinmu
     * @date 2020/2/5
     * @since 1.0
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody ReasonReq reasonReq, @RequestHeader("token")String token) {
        log.info("ReasonController update reasonReq:{}", reasonReq);
        ManagerDTO managerDTO = new ManagerDTO();
        if(token !=null ) {
            managerDTO = (ManagerDTO)redisUtil.get(token);
        }
        return reasonService.updateReason(reasonReq,managerDTO);
    }

    /**
     * 管理时列举
     *
     * @author zhoujinmu
     * @date 2020/2/5
     * @since 1.0
     */
    @GetMapping("/listReason")
    public Result<PageInfo<ReasonVO>> listReason(ReasonReq reasonReq) {
        log.info("ReasonController listReason reasonReq:{}", reasonReq);
        return ResultUtil.success(reasonService.listReason(reasonReq));
    }

    /**
     * 禁止时列举
     *
     * @author zhoujinmu
     * @date 2020/2/5
     * @since 1.0
     */
    @GetMapping("/selectReason")
    public Result<List<ReasonVO>> selectReason() {
        return ResultUtil.success(reasonService.selectReason());
    }
}
