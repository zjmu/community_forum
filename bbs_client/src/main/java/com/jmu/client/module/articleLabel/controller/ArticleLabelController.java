package com.jmu.client.module.articleLabel.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.client.module.articleLabel.service.ArticleLabelService;
import com.jmu.client.req.ArticleLabelReq;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleVO;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/10
 * @since 1.0
 */
@RestController
@RequestMapping("/articleLabel")
@Slf4j
public class ArticleLabelController {
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取标签对应的文章
     *
     * @author zhoujinmu
     * @date 2020/2/11
     * @since 1.0
     */
    @GetMapping("/listArticleOfLabel")
    public Result<PageInfo<ArticleVO>> listArticleOfLabel(ArticleLabelReq articleLabelReq, @RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        log.info("ArticleLabelController ArticleLabelController articleLabelReq:{}", articleLabelReq);
        return ResultUtil.success(articleLabelService.listArticleOfLabel(articleLabelReq,userInfo));
    }
}
