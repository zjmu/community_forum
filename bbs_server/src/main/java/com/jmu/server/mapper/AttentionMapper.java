package com.jmu.server.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.server.entity.Attention;
import com.jmu.server.vo.AttentionVO;

import java.util.List;

public interface AttentionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attention record);

    int insertSelective(Attention record);

    Attention selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attention record);

    int updateByPrimaryKey(Attention record);


    /**
     * 获取关联用户信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/14 16:09
     * @since 1.0
     */
    List<AttentionVO> listAttentionByUserId(Long userId);


    /**
     * 取消关注
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 10:06
     * @since 1.0
     */
    int cancelAttention(@Param("userId") Long userId, @Param("id") Long id);
}