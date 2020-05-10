package com.jmu.server.module.articleComment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.articleComment.service.ArticleCommentService;
import com.jmu.server.req.ArticleCommentReq;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.ArticleCommentVO;

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
    public Result<String> createComment(@RequestBody ArticleCommentReq articleCommentReq) {
        UserInfo userInfo = ArticleController.addUserInfo();
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
    public Result<String> createCommentOfArticle(@RequestBody ArticleCommentReq articleCommentReq) {
        UserInfo userInfo = ArticleController.addUserInfo();
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
