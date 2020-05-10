package com.jmu.server.module.articleReview.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.articleReview.service.ArticleReviewService;
import com.jmu.server.req.ArticleReviewReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.ArticleReviewVO;

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
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据条件获取信息
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 17:22
     * @since 1.0
     */
    @GetMapping("/listArticleReviewByCondition")
    public Result<PageInfo<ArticleReviewVO>> listByCondition(ArticleReviewReq articleReviewReq) {
        log.info("ArticleReviewController listByCondition articleReviewReq:{} ", articleReviewReq);
        return ResultUtil.success(articleReviewService.listByCondition(articleReviewReq));
    }

    /**
     * 审核通过
     *
     * @author zhoujinmu
     * @date 2020/2/4
     * @since 1.0
     */
    @PutMapping("/reviewResult")
    public Result<String> passArticle(@RequestBody ArticleReviewReq articleReviewReq,@RequestHeader("token")String token) {
        log.info("ArticleReviewController passArticle articleReviewReq:{} ", articleReviewReq);
        ManagerDTO managerDTO = new ManagerDTO();
        if(token !=null ) {
            managerDTO = (ManagerDTO)redisUtil.get(token);
        }
        return articleReviewService.reviewResult(articleReviewReq,managerDTO);
    }

    /**
     * 禁止
     *
     * @author zhoujinmu
     * @date 2020/2/6
     * @since 1.0
     */
    @PutMapping("/disabledArticle")
    public Result<String> disabledArticle(@RequestBody ArticleReviewReq articleReviewReq,@RequestHeader("token")String token) {
        log.info("ArticleReviewController disabledArticle articleReviewReq:{} ", articleReviewReq);
        ManagerDTO managerDTO = new ManagerDTO();
        if(token !=null ) {
            managerDTO = (ManagerDTO)redisUtil.get(token);
        }
        return articleReviewService.disabledArticle(articleReviewReq,managerDTO);
    }

}
