package com.jmu.server.module.favority.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.server.expection.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.entity.Favorite;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.favority.service.FavoriteService;
import com.jmu.server.req.FavoriteReq;
import com.jmu.server.req.Page;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.ArticleVO;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/16 10:41
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;


    /**
     * 获取用户收藏文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 11:04
     * @since 1.0
     */
    @GetMapping("/listFavoriteArticle")
    public Result<PageInfo<ArticleVO>> listFavoriteArticle(Page page) {
        UserInfo userInfo = ArticleController.addUserInfo();
        PageInfo<ArticleVO> favoriteArticles = favoriteService.listFavoriteArticle(page, userInfo.getId());
        return ResultUtil.success(favoriteArticles);
    }

    /**
     * 获取其他人收藏文章
     *
     * @author zhoujinmu
     * @date 2020/3/13
     * @since 1.0
     */
    @GetMapping("/listFavoriteArticleOfOther")
    public Result<PageInfo<ArticleVO>> listFavoriteArticleOfOther(FavoriteReq favoriteReq) {
        log.info("FavoriteController listFavoriteArticleOfOther favoriteReq:{}", favoriteReq);
        Page page = new Page();
        page.setPageNum(favoriteReq.getPageNum());
        page.setPageSize(favoriteReq.getPageSize());
        PageInfo<ArticleVO> favoriteArticles = favoriteService.listFavoriteArticle(page, favoriteReq.getId());
        return ResultUtil.success(favoriteArticles);
    }

    /**
     * 取消收藏文章
     *
     * @author zhoujinmu
     * @date 2020/4/11
     * @since 1.0
     */
    @DeleteMapping("/cancelFavoriteArticle/{articleId}")
    public Result<String> cancle(@PathVariable("articleId") Long articleId) {
        UserInfo userInfo = ArticleController.addUserInfo();
        try {
            favoriteService.cancelFavorite(userInfo, articleId);
        } catch (BusinessException e) {
            return ResultUtil.error("取消收藏失败！");
        }
        return ResultUtil.success("取消收藏成功！");
    }


    /**
     * 收藏文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:01
     * @since 1.0
     */
    @PostMapping("/addFavoriteArticle")
    public Result<String> addFavoriteArticle(@RequestBody Favorite favorite) {
        UserInfo userInfo = ArticleController.addUserInfo();
        favorite.setUserId(userInfo.getId());
        return favoriteService.addFavoriteArticle(favorite);
    }

    /**
     * 获得本人收藏数
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @GetMapping("/getFavoriteNum")
    public Result<Integer> getFavoriteNum() {
        return ResultUtil.success(favoriteService.getFavoriteNum());
    }

    /**
     * 获得他人收藏数
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @GetMapping("/getFavoriteNumOfOther")
    public Result<Integer> getFavoriteNumOfOther(Long id) {
        return ResultUtil.success(favoriteService.getFavoriteNumOfOther(id));
    }


}
