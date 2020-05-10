package com.jmu.client.mapper;


import org.apache.ibatis.annotations.Param;
import com.jmu.client.entity.Like;

public interface LikeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Like record);

    int insertSelective(Like record);

    Like selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Like record);

    int updateByPrimaryKey(Like record);


    /**
     * 是否点赞过
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:19
     * @since 1.0
     */
    int isLike(@Param("userId") Long userId, @Param("articleId") Long articleId);


    /**
     * 取消点赞
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:20
     * @since 1.0
     */
    int unlike(@Param("userId") Long userId, @Param("articleId") Long articleId);
}