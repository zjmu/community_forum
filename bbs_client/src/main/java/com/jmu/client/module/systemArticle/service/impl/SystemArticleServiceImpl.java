package com.jmu.client.module.systemArticle.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.client.entity.Manager;
import com.jmu.client.entity.SysArticleInfo;
import com.jmu.client.enums.SystemArticleStatuEnum;
import com.jmu.client.mapper.SysArticleInfoMapper;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.systemArticle.mapper.SystemArticleInfoMapperExt;
import com.jmu.client.module.systemArticle.service.SystemArticleService;
import com.jmu.client.req.SystemArticleReq;
import com.jmu.client.util.CodeGenerateUtil;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.SysArticleVO;
import com.jmu.client.vo.SystemArticleVO;

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
