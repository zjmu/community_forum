package com.jmu.client.module.systemArticle.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
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
import com.jmu.client.module.systemArticle.service.SystemArticleService;
import com.jmu.client.req.SystemArticleReq;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.SysArticleVO;
import com.jmu.client.vo.SystemArticleVO;

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

}
