package com.jmu.client.module.systemArticle.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.req.SystemArticleReq;
import com.jmu.client.util.Result;
import com.jmu.client.vo.SysArticleVO;
import com.jmu.client.vo.SystemArticleVO;

import java.util.List;

public interface SystemArticleService {


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
