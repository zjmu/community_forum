package com.jmu.client.module.article.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.User;
import com.jmu.client.req.ArticleReq;
import com.jmu.client.req.Page;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.Result;
import com.jmu.client.vo.ArticleVO;

public interface ArticleService {

    /**
     * 发送文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/23 16:42
     * @since 1.0
     */
    void sendArticle(ArticleReq articleReq);


    /**
     * 根据id，物理删除文章及关联
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/24 11:34
     * @since 1.0
     */
    void deleteArticle(Long id, UserInfo userInfo);


    /**
     * 逻辑删除文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/13 15:56
     * @since 1.0
     */
    void delete(Long id, UserInfo userInfo);

    /**
     * 获取一篇文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/24 17:26
     * @since 1.0
     */
    ArticleVO getArticle(Long id, UserInfo userInfo);


    /**
     * 列举用户所有文章
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/26 10:41
     * @since 1.0
     */
    PageInfo<ArticleVO> listArticleOfUser(Long id, Page page);

    /**
     * 分页列举文章
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    PageInfo<ArticleVO> listArticlePage(Page page,UserInfo userInfo);

    /**
     * 是否精选文章
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    Result<String> featured(ArticleReq articleReq);

    /**
     * 获得本人文章数
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    Integer getArticleNum(UserInfo userInfo);

    /**
     * 获得他人文章数
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    Integer getArticleNumOfOther(Long id);
}
