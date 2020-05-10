package com.jmu.server.module.blackList.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.entity.Manager;
import com.jmu.server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.blackList.service.BlackListService;
import com.jmu.server.req.BlackListReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.BlackListVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/20 16:08
 * @since 1.0
 */
@RestController
@RequestMapping("/balckList")
@Slf4j
public class BalckListController {

    @Autowired
    private BlackListService blackListService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据条件查询黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @GetMapping("/listBlackList")
    public Result<PageInfo<BlackListVO>> listBlackList(BlackListReq blackListReq) {
        return ResultUtil.success(blackListService.listBlackListByPage(blackListReq));
    }

    /**
     * 从黑名单中移除
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @DeleteMapping("/delete/{userId}")
    public Result<String> delete(@PathVariable("userId") Long userId) {
        return blackListService.delete(userId);
    }

    /**
     * 从黑名单中移除
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @GetMapping("/listBlackListHistory")
    public Result<List<BlackListVO>> listBlackListHistory(@RequestParam("userId") Long userId) {
        System.out.println("heimmmingdan"+userId);
        return blackListService.listBlackListHistory(userId);
    }

    /**
     * 加入黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> createBlackList(@RequestBody BlackListReq blackListReq, @RequestHeader("token")String token) {
        System.out.println(token);
        ManagerDTO manager = new ManagerDTO();
        if(token != null) {
            manager = (ManagerDTO) redisUtil.get(token);
            System.out.println(manager);
        }
        try {
            return blackListService.managerCreateBlackList(blackListReq,manager);
        } catch (Exception e) {
            log.error("BalckListController createBlackList:" + e.getMessage());
            return ResultUtil.error(e.getMessage());
        }
    }
}
