package com.jmu.client.module.articleReview.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.client.module.articleReview.service.ArticleReviewService;
import com.jmu.client.req.ArticleReviewReq;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleReviewVO;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/21 15:54
 * @since 1.0
 */
@RestController
@RequestMapping("/aritcleReview")
@Slf4j
public class ArticleReviewController {
    @Autowired
    private ArticleReviewService articleReviewService;


    @PostMapping("/reportArticle")
    public Result<String> report(@RequestBody ArticleReviewReq articleReviewReq) {
        log.info("ArticleReviewController disabledArticle articleReviewReq:{} ", articleReviewReq);
        return articleReviewService.reportArticle(articleReviewReq);
    }
}
