package com.jmu.server.module.systemArticle.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.module.systemArticle.service.SystemArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.server.entity.Manager;
import com.jmu.server.entity.SysArticleInfo;
import com.jmu.server.enums.SystemArticleStatuEnum;
import com.jmu.server.mapper.SysArticleInfoMapper;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.systemArticle.mapper.SystemArticleInfoMapperExt;
import com.jmu.server.req.SystemArticleReq;
import com.jmu.server.util.CodeGenerateUtil;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.SysArticleVO;
import com.jmu.server.vo.SystemArticleVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/14
 * @since 1.0
 */
@Service
@Slf4j
public class SystemArticleServiceImpl implements SystemArticleService {

    @Autowired
    private SysArticleInfoMapper sysArticleInfoMapper;
    @Autowired
    private SystemArticleInfoMapperExt systemArticleInfoMapperExt;

    /**
     * 获取系统文章
     *
     * @author zhoujinmu
     * @date 2020/2/16
     * @since 1.0
     */
    @Override
    public Result<String> create(SystemArticleReq systemArticleReq, ManagerDTO manager) {
        SysArticleInfo sysArticleInfo = SysArticleInfo.builder()
                .managerId(manager.getId())
                .articleCode(CodeGenerateUtil.generateSystemArticleCode(manager.getManagerCode()))
                .status(SystemArticleStatuEnum.NOT_SEND.getCode())
                .title(systemArticleReq.getTitle())
                .content(systemArticleReq.getMarkdown())
                .image(systemArticleReq.getImageUrl())
                .viewNum(0)
                .sendTime(systemArticleReq.getSendTime())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int result = sysArticleInfoMapper.insertSelective(sysArticleInfo);
        if (result > 0) {
            return ResultUtil.success("发送系统文章成功！");
        }
        return ResultUtil.error("发送系统文章失败！");
    }

    /**
     * 获取单条系统文章
     *
     * @author zhoujinmu
     * @date 2020/2/16
     * @since 1.0
     */
    @Override
    public SystemArticleVO getSystemArticle(Long id) {

        return systemArticleInfoMapperExt.getSystemArticleInfo(id);
    }

    /**
     * 获取所有系统文章封面
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    @Override
    public List<SystemArticleVO> listSystemArticle() {
        return systemArticleInfoMapperExt.listSystemArticleInfo();
    }

    /**
     * 服务端：获取系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/6
     * @since 1.0
     */
    @Override
    public PageInfo<SysArticleVO> listSysArticle(SystemArticleReq sysArticleReq) {
        PageHelper.startPage(sysArticleReq.getPageNum(), sysArticleReq.getPageSize());
        Map<String, Object> conditions = new HashMap<>();
        if (!StringUtils.isBlank(sysArticleReq.getOpCode())) {
            conditions.put("opCode", sysArticleReq.getOpCode());
        }
        if (!StringUtils.isBlank(sysArticleReq.getStatus())) {
            conditions.put("status", SystemArticleStatuEnum.getCodeByMessage(sysArticleReq.getStatus()));
        }
        if (!StringUtils.isBlank(sysArticleReq.getStartTime()) && !StringUtils.isBlank(sysArticleReq.getEndTime())) {
            conditions.put("startTime", sysArticleReq.getStartTime());
            conditions.put("endTime", sysArticleReq.getEndTime());
        }
        com.github.pagehelper.Page<SysArticleVO> sysArticleVOPage = systemArticleInfoMapperExt.listSysArticle(conditions);
        for (SysArticleVO sysArticleVO : sysArticleVOPage) {
            sysArticleVO.setStatusString(SystemArticleStatuEnum.getMessageByCode(sysArticleVO.getStatus()));
        }
        return sysArticleVOPage.toPageInfo();
    }

    /**
     * 后台：修改系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/7
     * @since 1.0
     */
    @Override
    public Result<String> updateSysArticle(SystemArticleReq systemArticleReq,ManagerDTO manager) {
        SysArticleInfo sysArticleInfo = SysArticleInfo.builder()
                .id(systemArticleReq.getSysArticleId())
                .sendTime(systemArticleReq.getSendTime())
                .viewNum(systemArticleReq.getViewNum())
                .content(systemArticleReq.getMarkdown())
                .title(systemArticleReq.getTitle())
                .status(SystemArticleStatuEnum.getCodeByMessage(systemArticleReq.getStatus()))
                .image(systemArticleReq.getImageUrl())
                .createTime(JodaUtils.getCurrentTime())
                .managerId(manager.getId())
                .build();
        int result = sysArticleInfoMapper.updateByPrimaryKeySelective(sysArticleInfo);
        if (result > 0) {
            return ResultUtil.success("更新系统文章成功！");
        } else {
            return ResultUtil.error("更新系统文章失败！");
        }
    }

    /**
     * 根据编号删除文章
     *
     * @author zhoujinmu
     * @date 2020/4/8
     * @since 1.0
     */
    @Override
    public Result<String> deleteSysArticle(Long id) {
        SysArticleInfo sysArticleInfo = SysArticleInfo.builder()
                .id(id)
                .status(SystemArticleStatuEnum.DELETE.getCode())
                .build();
        int result = sysArticleInfoMapper.updateByPrimaryKeySelective(sysArticleInfo);
        if (result > 0) {
            return ResultUtil.success("删除文章成功！");
        } else {
            return ResultUtil.error("删除文章失败！");
        }
    }

    /**
     * 发送文章
     *
     * @author zhoujinmu
     * @date 2020/4/8
     * @since 1.0
     */
    @Override
    public Result<String> sendSysArticle(Long id) {
        SysArticleInfo sysArticleInfo = SysArticleInfo.builder()
                .id(id)
                .status(SystemArticleStatuEnum.HAVD_SEND.getCode())
                .build();
        int result = sysArticleInfoMapper.updateByPrimaryKeySelective(sysArticleInfo);
        if (result > 0) {
            return ResultUtil.success("发送文章成功！");
        } else {
            return ResultUtil.error("发送文章失败！");
        }
    }

    /**
     * 发布定时文章
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    @Override
    public int send() {
        int result = systemArticleInfoMapperExt.sendArticle();
        log.info("发布文章数量：{}", result);
        return result;
    }
}
