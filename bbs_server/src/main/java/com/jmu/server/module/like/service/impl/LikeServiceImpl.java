package com.jmu.server.module.like.service.impl;

import com.jmu.server.expection.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.server.entity.ArticleInfo;
import com.jmu.server.entity.Like;
import com.jmu.server.enums.BaseEnum;
import com.jmu.server.enums.BusinessEnum;
import com.jmu.server.mapper.ArticleInfoMapper;
import com.jmu.server.mapper.LikeMapper;
import com.jmu.server.module.like.service.LikeService;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.mq.SendMsg;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/16 15:00
 * @since 1.0
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private ArticleInfoMapper articleInfoMapper;
    @Autowired
    private SendMsg sendMsg;

    /**
     * 文章点赞
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:14
     * @since 1.0
     */
    @Override
    public Result<String> likeArticle(Like like) {
        System.out.println("点赞信息:" + like.getUserId() + ":" + like.getArticleId());
        Result<String> validateResult = validate(like.getUserId(), like.getArticleId());
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
        //文章点赞
        like.setCreateTime(JodaUtils.getCurrentDate());
        int result = likeMapper.insert(like);
        if (result <= 0) {
            return ResultUtil.error("点赞失败！");
        }
        //mq
        sendMsg.addLike(like.getArticleId());
        return ResultUtil.success("点赞成功！");
    }

    /**
     * 取消文章点赞
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 15:14
     * @since 1.0
     */
    @Override
    public void unlikeArticle(UserInfo userInfo, Long articleId) {
        int result = likeMapper.unlike(userInfo.getId(), articleId);
        if (result <= 0) {
            throw new BusinessException(BusinessEnum.UNLIKEARTICLE_DEFAULT);
        }

        //mq
        articleInfoMapper.updateLikeNum(articleId, -1);
    }


    /**
     * 点赞前验证
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/16 14:25
     * @since 1.0
     */
    private Result<String> validate(Long usreId, Long articleId) {
        //查看是否已点赞
        int count = likeMapper.isLike(usreId, articleId);
        if (count > 0) {
            return ResultUtil.error("已点赞！");
        }
        //是否有此文章
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleId);
        if (articleInfo == null) {
            return ResultUtil.error("文章不存在！");
        }
        return ResultUtil.success("可点赞");
    }
}
