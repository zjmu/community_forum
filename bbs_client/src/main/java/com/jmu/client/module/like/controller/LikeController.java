package com.jmu.client.module.like.controller;

import com.jmu.client.expection.BusinessException;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.user.service.impl.UserServiceImpl;
import com.jmu.client.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.client.entity.Like;
import com.jmu.client.module.like.service.LikeService;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/16 14:59
 * @since 1.0
 */
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 点赞
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:36
     * @since 1.0
     */
    @PostMapping("/likeArticle")
    public Result<String> likeArticle(@RequestBody Like like, @RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        like.setUserId(userInfo.getId());
        return likeService.likeArticle(like);
    }


    /**
     * 取消点赞
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:35
     * @since 1.0
     */
    @DeleteMapping("/unlikeArticle/{articleId}")
    public Result<String> unlikeArticle(@PathVariable("articleId") Long articleId,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        try {
            likeService.unlikeArticle(userInfo, articleId);
        } catch (BusinessException e) {
            return ResultUtil.error("取消点赞失败！");
        }
        return ResultUtil.success("取消点赞成功！");
    }
}
