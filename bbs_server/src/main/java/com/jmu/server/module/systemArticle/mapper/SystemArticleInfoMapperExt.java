package com.jmu.server.module.systemArticle.mapper;

import com.github.pagehelper.Page;
import com.jmu.server.vo.SysArticleVO;
import com.jmu.server.vo.SystemArticleVO;

import java.util.List;
import java.util.Map;

public interface SystemArticleInfoMapperExt {

    /**
     * 获取系统文章详情
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    SystemArticleVO getSystemArticleInfo(Long id);

    /**
     * 获取所有系统文章封面
     *
     * @author zhoujinmu
     * @date 2020/4/2
     * @since 1.0
     */
    List<SystemArticleVO> listSystemArticleInfo();

    /**
     * 服务端：获取系统文章
     *
     * @author zhoujinmu
     * @date 2020/4/6
     * @since 1.0
     */
    Page<SysArticleVO> listSysArticle(Map<String, Object> conditions);

    /**
     * 定时器：到点发布文章
     *
     * @author zhoujinmu
     * @date 2020/4/6
     * @since 1.0
     */
    int sendArticle();

}
