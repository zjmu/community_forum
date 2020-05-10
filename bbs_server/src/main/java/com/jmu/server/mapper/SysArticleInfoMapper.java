package com.jmu.server.mapper;

import com.jmu.server.entity.SysArticleInfo;

public interface SysArticleInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysArticleInfo record);

    int insertSelective(SysArticleInfo record);

    SysArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysArticleInfo record);

    int updateByPrimaryKeyWithBLOBs(SysArticleInfo record);

    int updateByPrimaryKey(SysArticleInfo record);
}