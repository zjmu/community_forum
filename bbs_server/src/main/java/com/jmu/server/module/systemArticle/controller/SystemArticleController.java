package com.jmu.server.module.systemArticle.controller;

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
import com.jmu.server.module.systemArticle.service.SystemArticleService;
import com.jmu.server.req.SystemArticleReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.SysArticleVO;
import com.jmu.server.vo.SystemArticleVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/14
 * @since 1.0
 */
@RestController
@RequestMapping("/systemArticle")
@Slf4j
public class SystemArticleController {

    @Autowired
    private SystemArticleService systemArticleService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 系统：创建系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> create(@RequestBody SystemArticleReq systemArticleReq, @RequestHeader("token")String token) {
        log.info("SystemArticleController create systemArticleReq:{}", systemArticleReq);
        ManagerDTO managerDTO = new ManagerDTO();
        if(token !=null ) {
            managerDTO = (ManagerDTO)redisUtil.get(token);
        }
        return systemArticleService.create(systemArticleReq,managerDTO);
    }

    /**
     * 客户端：获取系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    @GetMapping("/getSystemArticle")
    public Result<SystemArticleVO> getSystemArticle(SystemArticleReq systemArticleReq) {
        log.info("SystemArticleController getSystemArticle systemArticleReq:{}", systemArticleReq);
        return ResultUtil.success(systemArticleService.getSystemArticle(systemArticleReq.getSysArticleId()));
    }

    /**
     * 客户端：获取系统文章封面
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    @GetMapping("/listSystemArticle")
    public Result<List<SystemArticleVO>> listSystemArticle() {
        return ResultUtil.success(systemArticleService.listSystemArticle());
    }

    /**
     * 服务端：分页获取系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/6
     * @since 1.0
     */
    @GetMapping("/listSystemArticlePage")
    public Result<PageInfo<SysArticleVO>> listSystemArticlePage(SystemArticleReq sysArticleReq) {
        System.out.println(sysArticleReq);
        return ResultUtil.success(systemArticleService.listSysArticle(sysArticleReq));
    }

    /**
     * 后台：修改系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/7
     * @since 1.0
     */
    @PutMapping("/updateSysArticle")
    public Result<String> updateSysArticle(@RequestBody SystemArticleReq sysArticleReq,@RequestHeader("token")String token) {
        ManagerDTO managerDTO = new ManagerDTO();
        if(token !=null ) {
            managerDTO = (ManagerDTO)redisUtil.get(token);
        }
        return systemArticleService.updateSysArticle(sysArticleReq,managerDTO);
    }

    /**
     * 删除文章
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteSysArticle(@PathVariable Long id) {
        return systemArticleService.deleteSysArticle(id);
    }

    /**
     * 后台：发送文章
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    @PutMapping("/send")
    public Result<String> sendSysArticle(@RequestBody SystemArticleReq systemArticleReq) {
        return systemArticleService.sendSysArticle(systemArticleReq.getSysArticleId());
    }
}
