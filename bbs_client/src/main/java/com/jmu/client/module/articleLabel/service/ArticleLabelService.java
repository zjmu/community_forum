package com.jmu.client.module.articleLabel.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.req.ArticleLabelReq;
import com.jmu.client.vo.ArticleVO;

public interface ArticleLabelService {

    /**
     * 获取标签对应的文章
     *
     * @author zhoujinmu
     * @date 2020/2/11
     * @since 1.0
     */
    PageInfo<ArticleVO> listArticleOfLabel(ArticleLabelReq articleLabelReq, UserInfo userInfo);
}
