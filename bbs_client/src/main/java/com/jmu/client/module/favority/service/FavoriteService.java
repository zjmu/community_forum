package com.jmu.client.module.favority.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.Favorite;
import com.jmu.client.req.Page;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.Result;
import com.jmu.client.vo.ArticleVO;

public interface FavoriteService {


    /**
     * 获取用户收藏
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:02
     * @since 1.0
     */
    PageInfo<ArticleVO> listFavoriteArticle(Page page, Long id);


    /**
     * 取消收藏
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:03
     * @since 1.0
     */
    void cancelFavorite(UserInfo userInfo, Long articleId);


    /**
     * 用户收藏文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:03
     * @since 1.0
     */
    Result<String> addFavoriteArticle(Favorite favorite);

    /**
     * 获取本人收藏数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getFavoriteNum(UserInfo userInfo);

    /**
     * 获取他人收藏数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getFavoriteNumOfOther(Long id);
}
