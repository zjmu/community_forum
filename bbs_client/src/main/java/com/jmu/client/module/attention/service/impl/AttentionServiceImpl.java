package com.jmu.client.module.attention.service.impl;

import com.jmu.client.entity.User;
import com.jmu.client.expection.BusinessException;
import com.jmu.client.module.attention.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.client.entity.Attention;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.enums.BusinessEnum;
import com.jmu.client.mapper.AttentionMapper;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.attention.mapper.AttentionExtMapper;
import com.jmu.client.req.AttentionReq;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.AttentionVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/14 15:29
 * @since 1.0
 */
@Service
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    private AttentionMapper attentionMapper;
    @Autowired
    private AttentionExtMapper attentionExtMapper;

    /**
     * 根据id获取该用户关注信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 10:01
     * @since 1.0
     */
    @Override
    public List<AttentionVO> listAttention(Long id) {
        List<AttentionVO> attentionVOS = attentionMapper.listAttentionByUserId(id);
        System.out.println("获取关注列表" + attentionVOS);
        for (int i = 0; i < attentionVOS.size(); i++) {
            if (attentionVOS.get(i) != null && attentionVOS.get(i).getArea() != null && attentionVOS.get(i).getBuilding() != null &&
                    attentionVOS.get(i).getUnit() != null && attentionVOS.get(i).getFloor() != null) {
                attentionVOS.get(i).setRoomInfo(attentionVOS.get(i).getArea() +
                        attentionVOS.get(i).getBuilding() +
                        attentionVOS.get(i).getUnit() +
                        attentionVOS.get(i).getFloor());
            }
        }
        return attentionVOS;
    }

    /**
     * 根据id，取消当前用户关注信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 10:01
     * @since 1.0
     */
    @Override
    public void cancelAttention(Long userId, Long id) {
        int result = attentionMapper.cancelAttention(userId, id);
        if (result <= 0) {
            throw new BusinessException(BusinessEnum.CANCEL_ATTENTION_DEFAULT);
        }
    }

    /**
     * 添加关注
     *
     * @author zhoujinmu
     * @date 2020/4/12
     * @since 1.0
     */
    @Override
    public Result<String> createAttention(AttentionReq attentionReq,UserInfo userInfo) {
        if (attentionReq.getAttentionUserId() == null && attentionReq.getAttentionUserId() > 0L) {
            return ResultUtil.error("请输入合法关注人id！");
        }
        Integer num = attentionExtMapper.selectAttention(userInfo.getId(), attentionReq.getAttentionUserId());
        if (num != null && num > 0) {
            return ResultUtil.error("您已关注！");
        }
        Attention attention = Attention.builder()
                .userId(userInfo.getId())
                .attentionUserId(attentionReq.getAttentionUserId())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int result = attentionMapper.insert(attention);
        if (result > 0) {
            return ResultUtil.success("关注成功！");
        }
        return ResultUtil.error("关注失败！");
    }

    /**
     * 获取关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    @Override
    public Integer getAttentionNum(UserInfo userInfo) {
        return attentionExtMapper.getAttentionNum(userInfo.getId());
    }

    /**
     * 获取他人关注数
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @Override
    public Integer getAttentionNumOfOther(Long id) {
        return attentionExtMapper.getAttentionNum(id);
    }
}
