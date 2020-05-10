package com.jmu.server.module.articleLabel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.articleLabel.mapper.ArticleLabelExtMapper;
import com.jmu.server.module.articleLabel.service.ArticleLabelService;
import com.jmu.server.req.ArticleLabelReq;
import com.jmu.server.vo.ArticleExtVO;
import com.jmu.server.vo.ArticleVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/10
 * @since 1.0
 */
@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {

    @Autowired
    private ArticleLabelExtMapper articleLabelExtMapper;

    /**
     * 获取标签对应的文章
     *
     * @author zhoujinmu
     * @date 2020/2/11
     * @since 1.0
     */
    @Override
    public PageInfo<ArticleVO> listArticleOfLabel(ArticleLabelReq articleLabelReq) {
        UserInfo userInfo = ArticleController.addUserInfo();
        PageHelper.startPage(articleLabelReq.getPageNum(), articleLabelReq.getPageSize());
        com.github.pagehelper.Page<ArticleVO> articleVOPage = articleLabelExtMapper.listArticleOfLabel(articleLabelReq.getLabelId(), userInfo.getId());

        //对每个字段赋值
        for (int i = 0; i < articleVOPage.size(); i++) {
            if (articleVOPage.get(i).getFavoriteId() != null && articleVOPage.get(i).getFavoriteId() > 0L) {
                articleVOPage.get(i).setFavorite(true);
            }
            if (articleVOPage.get(i).getLikeId() != null && articleVOPage.get(i).getLikeId() > 0L) {
                articleVOPage.get(i).setLike(true);
            }
            if (!(articleVOPage.get(i).getArticleExts() == null || articleVOPage.get(i).getArticleExts().isEmpty())) {
                //对获取到的结果赋值vo
                List<ArticleExtVO> articleExtVOS = articleVOPage.get(i).getArticleExts().stream().map(ArticleExtVO::of).collect(Collectors.toList());
                articleVOPage.get(i).setArticleExtVOS(articleExtVOS);

                articleVOPage.get(i).setLineCount(articleVOPage.get(i).getArticleExtVOS().size() / 4 + 1);
                articleVOPage.get(i).setResourceType(articleVOPage.get(i).getArticleExtVOS().get(0).getType());
            } else {
                articleVOPage.get(i).setLineCount(1);
                articleVOPage.get(i).setResourceType("");
            }
        }
        return articleVOPage.toPageInfo();
    }
}
