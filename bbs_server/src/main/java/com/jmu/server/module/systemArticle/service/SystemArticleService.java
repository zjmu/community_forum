package com.jmu.server.module.systemArticle.service;

import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.req.SystemArticleReq;
import com.jmu.server.util.Result;
import com.jmu.server.vo.SysArticleVO;
import com.jmu.server.vo.SystemArticleVO;

import java.util.List;

public interface SystemArticleService {

    /**
     * 创建系统文章
     *
     * @author zhoujinmu
     * @date 2020/2/16
     * @since 1.0
     */
    Result<String> create(SystemArticleReq systemArticleReq,ManagerDTO manager);

    /**
     * 获取单个系统文章
     *
     * @author zhoujinmu
     * @date 2020/2/16
     * @since 1.0
     */
    SystemArticleVO getSystemArticle(Long id);

    /**
     * 获取所有系统文章封面
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    List<SystemArticleVO> listSystemArticle();

    /**
     * 服务端：获取系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/6
     * @since 1.0
     */
    PageInfo<SysArticleVO> listSysArticle(SystemArticleReq sysArticleReq);

    /**
     * 后台：修改系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/7
     * @since 1.0
     */
    Result<String> updateSysArticle(SystemArticleReq systemArticleReq, ManagerDTO manager);

    /**
     * 根据编号删除文章
     *
     * @author zhoujinmu
     * @date 2020/4/8
     * @since 1.0
     */
    Result<String> deleteSysArticle(Long id);

    /**
     * 发送文章
     *
     * @author zhoujinmu
     * @date 2020/4/8
     * @since 1.0
     */
    Result<String> sendSysArticle(Long id);

    /**
     * 发布定时文章
     *
     * @author zhoujinmu
     * @date 2020/4/8
     * @since 1.0
     */
    int send();
}
