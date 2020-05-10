package com.jmu.server.module.like.controller;

import com.jmu.server.expection.BusinessException;
import com.jmu.server.module.article.controller.ArticleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.entity.Like;
import com.jmu.server.module.like.service.LikeService;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;

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
    public Result<String> likeArticle(@RequestBody Like like) {
        UserInfo userInfo = ArticleController.addUserInfo();
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
    public Result<String> unlikeArticle(@PathVariable("articleId") Long articleId) {
        UserInfo userInfo = ArticleController.addUserInfo();
        try {
            likeService.unlikeArticle(userInfo, articleId);
        } catch (BusinessException e) {
            return ResultUtil.error("取消点赞失败！");
        }
        return ResultUtil.success("取消点赞成功！");
    }
}
