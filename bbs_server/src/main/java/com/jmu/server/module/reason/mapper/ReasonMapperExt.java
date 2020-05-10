package com.jmu.server.module.reason.mapper;

import com.github.pagehelper.Page;
import com.jmu.server.entity.Reason;
import com.jmu.server.vo.ReasonVO;


import java.util.List;
import java.util.Map;

public interface ReasonMapperExt {
    /**
     * 禁止时列举
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    List<Reason> selectReason();

    /**
     * 管理时列举
     *
     * @author zhoujinmu
     * @date 2020/2/8
     * @since 1.0
     */
    Page<ReasonVO> listReson(Map<String, Object> conditions);
}
