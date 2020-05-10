package com.jmu.client.mapper;


import org.apache.ibatis.annotations.Param;
import com.jmu.client.entity.ArticleInfo;

public interface ArticleInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    int countArticle(Long id);


    /**
     * mq文章收藏量加一
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:39
     * @since 1.0
     */
    int updateFavoriteNum(@Param("articleId") Long articleId, @Param("num") Integer num);

    /**
     * mq文章点赞量加一
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:39
     * @since 1.0
     */
    int updateLikeNum(@Param("articleId") Long articleId, @Param("num") Integer num);

    /**
     * mq文章分享量加一
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:39
     * @since 1.0
     */
    int updateShareNum(@Param("articleId") Long articleId);

    /**
     * mq文章浏览量加一
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:39
     * @since 1.0
     */
    int updateViewNum(Long articleId);

}