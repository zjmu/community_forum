package com.jmu.client.module.articleReview.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jmu.client.entity.ArticleInfo;
import com.jmu.client.entity.ArticleReview;
import com.jmu.client.entity.Manager;
import com.jmu.client.entity.User;
import com.jmu.client.enums.ReviewStatusEnum;
import com.jmu.client.enums.FeaturedEnum;
import com.jmu.client.enums.StateEnum;
import com.jmu.client.mapper.ArticleInfoMapper;
import com.jmu.client.mapper.ArticleReviewMapper;
import com.jmu.client.mapper.UserMapper;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.articleReview.mapper.ArticleReviewMapperExt;
import com.jmu.client.module.articleReview.service.ArticleReviewService;
import com.jmu.client.module.blackList.service.BlackListService;
import com.jmu.client.req.ArticleReviewReq;
import com.jmu.client.req.Page;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleReviewVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/21 15:54
 * @since 1.0
 */
@Service
@Slf4j
public class ArticleReviewServiceImpl implements ArticleReviewService {

    private static final String PASS = "通过";
    @Value("${disable.score}")
    private String scoreValue;

    @Autowired
    private ArticleReviewMapperExt articleReviewMapperExt;
    @Autowired
    private ArticleReviewMapper articleReviewMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleInfoMapper articleInfoMapper;
    @Autowired
    private BlackListService blackListService;


    /**
     * 举报文章
     *
     * @author zhoujinmu
     * @date 2020/2/10
     * @since 1.0
     */
    @Override
    public Result<String> reportArticle(ArticleReviewReq articleReviewReq) {
        Integer num = articleReviewMapperExt.getByArticleId(articleReviewReq.getArticleId());
        if (num > 0) {
            return ResultUtil.success("举报成功！");
        }
        ArticleReview articleReview = ArticleReview.builder()
                .articleId(articleReviewReq.getArticleId())
                .userId(articleReviewReq.getUserId())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int result = articleReviewMapper.insertSelective(articleReview);
        if (result > 0) {
            return ResultUtil.success("举报成功！");
        }
        return ResultUtil.error("举报失败！");
    }

}
