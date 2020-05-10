package com.jmu.server.module.articleLabel.service;

import com.github.pagehelper.PageInfo;
import com.jmu.server.req.ArticleLabelReq;
import com.jmu.server.vo.ArticleVO;

public interface ArticleLabelService {

    /**
     * 获取标签对应的文章
     *
     * @author zhoujinmu
     * @date 2020/2/11
     * @since 1.0
     */
    PageInfo<ArticleVO> listArticleOfLabel(ArticleLabelReq articleLabelReq);
}
