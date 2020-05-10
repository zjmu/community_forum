package com.jmu.server.module.reason.service;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.entity.Manager;
import com.jmu.server.req.ReasonReq;
import com.jmu.server.util.Result;
import com.jmu.server.vo.ReasonVO;

import java.util.List;

public interface ReasonService {

    /**
     * 禁止文章时列举
     *
     * @author zhoujinmu
     * @date 2020/2/8
     * @since 1.0
     */
    List<ReasonVO> selectReason();

    /**
     * 管理时列举
     *
     * @author zhoujinmu
     * @date 2020/2/8
     * @since 1.0
     */
    PageInfo<ReasonVO> listReason(ReasonReq reasonReq);

    /**
     * 添加违规规则
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    Result<String> create(ReasonReq reasonReq, ManagerDTO manager);

    /**
     * 更新违规规则
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    Result<String> updateReason(ReasonReq reasonReq,ManagerDTO managerDTO);

    Result<String> deleteReason(Long id);
}
