package com.jmu.client.module.attention.service;

import com.jmu.client.entity.UserInfo;
import com.jmu.client.req.AttentionReq;
import com.jmu.client.util.Result;
import com.jmu.client.vo.AttentionVO;

import java.util.List;

public interface AttentionService {

    /**
     * 根据id获取该用户关注信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 10:01
     * @since 1.0
     */
    List<AttentionVO> listAttention(Long id);


    /**
     * 根据id，取消当前用户关注信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 10:01
     * @since 1.0
     */
    void cancelAttention(Long userId, Long id);

    /**
     * 添加关注
     *
     * @author zhoujinmu
     * @date 2020/4/12
     * @since 1.0
     */
    Result<String> createAttention(AttentionReq attentionReq, UserInfo userInfo);

    /**
     * 获取本人关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getAttentionNum(UserInfo userInfo);

    /**
     * 获取他人关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getAttentionNumOfOther(Long id);

}
