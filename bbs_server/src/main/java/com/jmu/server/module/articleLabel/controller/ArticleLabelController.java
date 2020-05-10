package com.jmu.server.module.articleLabel.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.articleLabel.service.ArticleLabelService;
import com.jmu.server.req.ArticleLabelReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.ArticleVO;

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

    /**
     * 获取标签对应的文章
     *
     * @author zhoujinmu
     * @date 2020/2/11
     * @since 1.0
     */
    @GetMapping("/listArticleOfLabel")
    public Result<PageInfo<ArticleVO>> listArticleOfLabel(ArticleLabelReq articleLabelReq) {
        log.info("ArticleLabelController ArticleLabelController articleLabelReq:{}", articleLabelReq);
        return ResultUtil.success(articleLabelService.listArticleOfLabel(articleLabelReq));
    }
}
