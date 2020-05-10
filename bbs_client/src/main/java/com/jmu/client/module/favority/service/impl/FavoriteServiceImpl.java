package com.jmu.client.module.favority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.User;
import com.jmu.client.expection.BusinessException;
import com.jmu.client.module.favority.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.client.entity.ArticleInfo;
import com.jmu.client.entity.Favorite;
import com.jmu.client.enums.BaseEnum;
import com.jmu.client.enums.BusinessEnum;
import com.jmu.client.mapper.ArticleInfoMapper;
import com.jmu.client.mapper.FavoriteMapper;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.favority.mapper.FavoriteExtMapper;
import com.jmu.client.mq.SendMsg;
import com.jmu.client.req.Page;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleExtVO;
import com.jmu.client.vo.ArticleVO;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/16 10:41
 * @since 1.0
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private ArticleInfoMapper articleInfoMapper;
    @Autowired
    private SendMsg sendMsg;
    @Autowired
    private FavoriteExtMapper favoriteExtMapper;


    /**
     * 查询收藏的文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:04
     * @since 1.0
     */
    @Override
    public PageInfo<ArticleVO> listFavoriteArticle(Page page, Long id) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        com.github.pagehelper.Page<ArticleVO> articleVOPage = favoriteMapper.listFavoriteArticle(id);
        //对每个字段赋值
        for (int i = 0; i < articleVOPage.size(); i++) {
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


    /**
     * 取消收藏
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:04
     * @since 1.0
     */
    @Override
    public void cancelFavorite(UserInfo userInfo, Long articleId) {
        int result = favoriteMapper.cancelFavoriteArticle(userInfo.getId(), articleId);
        if (result <= 0) {
            throw new BusinessException(BusinessEnum.CANCEL_FAVORITEARTICLE_DEFAULT);
        }
        //mq
        sendMsg.subtractFavorite(articleId);
    }


    /**
     * 收藏文章
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:04
     * @since 1.0
     */
    @Override
    public Result<String> addFavoriteArticle(Favorite favorite) {
        Result<String> validateResult = validate(favorite.getUserId(), favorite.getArticleId());
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
        //收藏文章
        favorite.setCreateTime(JodaUtils.getCurrentDate());
        int result = favoriteMapper.insert(favorite);
        if (result <= 0) {
            return ResultUtil.error("收藏失败！");
        }
        //mq
        sendMsg.addFavorite(favorite.getId());
        return ResultUtil.success("收藏成功！");
    }

    /**
     * 获取收藏数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    @Override
    public Integer getFavoriteNum(UserInfo userInfo) {
        return favoriteExtMapper.getFavoriteNum(userInfo.getId());
    }

    /**
     * 获取他人收藏数
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @Override
    public Integer getFavoriteNumOfOther(Long id) {
        return favoriteExtMapper.getFavoriteNum(id);
    }

    /**
     * 收藏前验证是否已有
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:25
     * @since 1.0
     */
    private Result<String> validate(Long userId, Long articleId) {
        //查看是否已收藏
        int count = favoriteMapper.isFavorite(userId, articleId);
        if (count > 0) {
            return ResultUtil.error("已收藏！");
        }
        //是否有此文章
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleId);
        if (articleInfo == null) {
            return ResultUtil.error("文章不存在！");
        }

        return ResultUtil.success("可收藏");
    }
}
