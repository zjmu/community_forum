package com.jmu.client.module.articleComment.controller;

import com.jmu.client.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.articleComment.service.ArticleCommentService;
import com.jmu.client.req.ArticleCommentReq;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleCommentVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/26 14:28
 * @since 1.0
 */
@RestController
@RequestMapping("/articleComment")
public class ArticleCommentController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleCommentService articleCommentService;

    /**
     * 添加评论
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/26 15:47
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> createComment(@RequestBody ArticleCommentReq articleCommentReq, @RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return articleCommentService.addComment(articleCommentReq, userInfo);
    }

    /**
     * 文章评论
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    @PostMapping("/createCommentOfArticle")
    public Result<String> createCommentOfArticle(@RequestBody ArticleCommentReq articleCommentReq,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo)redisUtil.get(token);
        }
        return articleCommentService.addCommentOfArticle(articleCommentReq, userInfo);
    }


    /**
     * 返回评论信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/14 9:50
     * @since 1.0
     */
    @GetMapping("/listComment")
    public Result<List<ArticleCommentVO>> listComment(Long articleId) {
        return ResultUtil.success(articleCommentService.listComment(articleId));
    }
}
