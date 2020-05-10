package com.jmu.server.module.like.service;

import com.jmu.server.entity.Like;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.util.Result;

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
