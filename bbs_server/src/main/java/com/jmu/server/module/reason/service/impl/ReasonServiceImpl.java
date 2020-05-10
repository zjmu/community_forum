package com.jmu.server.module.reason.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.module.article.controller.ArticleController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.server.entity.Manager;
import com.jmu.server.entity.Reason;
import com.jmu.server.enums.BaseEnum;
import com.jmu.server.mapper.ReasonMapper;
import com.jmu.server.module.reason.mapper.ReasonMapperExt;
import com.jmu.server.module.reason.service.ReasonService;
import com.jmu.server.req.ReasonReq;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.ReasonVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/4
 * @since 1.0
 */
@Service
public class ReasonServiceImpl implements ReasonService {

    @Autowired
    private ReasonMapperExt reasonMapperExt;
    @Autowired
    private ReasonMapper reasonMapper;

    /**
     * 禁止时列举
     *
     * @author zhoujinmu
     * @date 2020/2/8
     * @since 1.0
     */
    @Override
    public List<ReasonVO> selectReason() {
        List<Reason> reasons = reasonMapperExt.selectReason();
        return reasons.stream().map(ReasonVO::of).collect(Collectors.toList());
    }

    /**
     * 管理时列举
     *
     * @author zhoujinmu
     * @date 2020/2/8
     * @since 1.0
     */
    @Override
    public PageInfo<ReasonVO> listReason(ReasonReq reasonReq) {
        Map<String, Object> conditions = new HashMap<>();
        if (!StringUtils.isBlank(reasonReq.getManagerCode())) {
            conditions.put("opCode", reasonReq.getManagerCode());
        }
        if (reasonReq.getScore() != null) {
            conditions.put("score", reasonReq.getScore());
        }
        if (!StringUtils.isBlank(reasonReq.getStartTime()) && !StringUtils.isBlank(reasonReq.getEndTime())) {
            conditions.put("startTime", reasonReq.getStartTime());
            conditions.put("endTime", reasonReq.getEndTime());
        }
        Page<ReasonVO> reasonVOPage = reasonMapperExt.listReson(conditions);
        PageInfo<ReasonVO> reasonVOPageInfo = reasonVOPage.toPageInfo();
        System.out.println(reasonVOPageInfo);
        return reasonVOPageInfo;
    }

    /**
     * 添加违规原因
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    @Override
    public Result<String> create(ReasonReq reasonReq, ManagerDTO manager) {
        Result<String> validateResult = validate(reasonReq);
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
        Reason reason = Reason.builder()
                .reason(reasonReq.getContent())
                .fraction(reasonReq.getScore())
                .opCode(manager.getManagerCode())
                .opId(manager.getId())
                .opTime(JodaUtils.getCurrentTime())
                .build();
        int result = reasonMapper.insert(reason);
        if (result < 0) {
            return ResultUtil.error("添加违规规则失败！");
        }
        return ResultUtil.success("添加违规规则成功！");
    }

    /**
     * 修改违规原因
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    @Override
    public Result<String> updateReason(ReasonReq reasonReq,ManagerDTO manager) {
        Result<String> validateResult = validate(reasonReq);
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
        if (reasonReq.getId() == null) {
            return ResultUtil.error("请添加修改记录的ID号！");
        }
        Reason reason = Reason.builder()
                .id(reasonReq.getId())
                .opTime(JodaUtils.getCurrentTime())
                .opId(manager.getId())
                .opCode(manager.getManagerCode())
                .reason(reasonReq.getContent())
                .fraction(reasonReq.getScore())
                .build();
        int result = reasonMapper.updateByPrimaryKey(reason);
        if (result < 0) {
            return ResultUtil.error("更新违规规则失败！");
        }
        return ResultUtil.success("更新违规规则成功！");
    }

    /**
     * 删除违规规则
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    @Override
    public Result<String> deleteReason(Long id) {
        int result = reasonMapper.deleteByPrimaryKey(id);
        if (result < 0) {
            return ResultUtil.error("删除违规规则失败！");
        }
        return ResultUtil.success("删除违规规则成功！");
    }

    /**
     * 验证表单内容
     *
     * @author zhoujinmu
     * @date 2020/2/9
     * @since 1.0
     */
    private Result<String> validate(ReasonReq reasonReq) {
        if (StringUtils.isBlank(reasonReq.getContent())) {
            return ResultUtil.error("请添加违规内容");
        }
        if (null == reasonReq.getScore()) {
            return ResultUtil.error("请添加分值");
        }
        return ResultUtil.success("验证成功！");
    }
}
