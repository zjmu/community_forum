package com.jmu.server.mapper;


import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import com.jmu.server.entity.Favorite;
import com.jmu.server.vo.ArticleVO;

public interface FavoriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Favorite record);

    int insertSelective(Favorite record);

    Favorite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Favorite record);

    int updateByPrimaryKey(Favorite record);


    /**
     * 根据用户id获取收藏文章信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 11:17
     * @since 1.0
     */
    Page<ArticleVO> listFavoriteArticle(Long userId);


    /**
     * 根据文章id取消文章收藏
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 11:17
     * @since 1.0
     */
    int cancelFavoriteArticle(@Param("userId") Long userId, @Param("articleId") Long articleId);


    /**
     * 查看是否已收藏文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:16
     * @since 1.0
     */
    int isFavorite(@Param("userId") Long userId, @Param("articleId") Long articleId);
}