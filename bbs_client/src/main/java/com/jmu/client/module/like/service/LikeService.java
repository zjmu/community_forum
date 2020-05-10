package com.jmu.client.module.like.service;

import com.jmu.client.entity.Like;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.util.Result;

public interface LikeService {


    /**
     * 给文章点赞
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:03
     * @since 1.0
     */
    Result<String> likeArticle(Like like);


    /**
     * 取消文章点赞
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:03
     * @since 1.0
     */
    void unlikeArticle(UserInfo userInfo, Long articleId);
}
