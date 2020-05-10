package com.jmu.server.module.attention.service;

import com.jmu.server.req.AttentionReq;
import com.jmu.server.util.Result;
import com.jmu.server.vo.AttentionVO;

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
    Result<String> createAttention(AttentionReq attentionReq);

    /**
     * 获取本人关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getAttentionNum();

    /**
     * 获取他人关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    Integer getAttentionNumOfOther(Long id);

}
