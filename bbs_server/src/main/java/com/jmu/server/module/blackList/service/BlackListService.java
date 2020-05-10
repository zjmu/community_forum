package com.jmu.server.module.blackList.service;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.req.BlackListReq;
import com.jmu.server.util.Result;
import com.jmu.server.vo.BlackListVO;

import java.util.List;

public interface BlackListService {

    /**
     * 创建黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    Result<String> createBlackList(Long id, Integer score, String detail, ManagerDTO manager);

    /**
     * 黑名单记录
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    Result<String> createBlackListHistory(Long id, Integer score, String detail,ManagerDTO manager);

    /**
     * 发文章前查看是否在黑名单中
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    int isInBlackList(Long userId);

    /**
     * 根据条件查询黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    PageInfo<BlackListVO> listBlackListByPage(BlackListReq blackListReq);

    /**
     * 从黑名单中移除
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    Result<String> delete(Long userId);

    /**
     * 黑名单历史记录
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    Result<List<BlackListVO>> listBlackListHistory(Long userId);

    /**
     * 创建黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    Result<String> managerCreateBlackList(BlackListReq blackListReq, ManagerDTO manager);

    /**
     * 执行定时任务
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    void dealCronJob();
}
