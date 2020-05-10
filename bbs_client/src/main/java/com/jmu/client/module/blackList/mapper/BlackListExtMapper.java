package com.jmu.client.module.blackList.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import com.jmu.client.vo.BlackListVO;

import java.util.Map;

public interface BlackListExtMapper {

    /**
     * 查看用户是否被禁言
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    int selectByUserId(@Param("userId") Long userId);

    /**
     * 根据条件查询黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    Page<BlackListVO> listBlackListByConditions(Map<String, Object> conditions);

    /**
     * 从黑名单中移除
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    int deleteByUserId(@Param("userId") Long userId);

    /**
     * 信誉度高用户解除禁言
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    int deleteFromBlackList(Integer score);
}
