package com.jmu.client.module.blackList.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.blackList.service.BlackListService;
import com.jmu.client.req.BlackListReq;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.BlackListVO;

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

    /**
     * 查看用户是否被禁言
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    @GetMapping("/getBlackListOfUser")
    public Result<String> getBlackListOfUser(UserInfo userInfo) {
        int num = blackListService.isInBlackList(userInfo.getId());
        if (num > 0) {
            return ResultUtil.error("您已被禁言，请解除后再发文章！");
        }
        return ResultUtil.success("您可以发表文章！");
    }
}
