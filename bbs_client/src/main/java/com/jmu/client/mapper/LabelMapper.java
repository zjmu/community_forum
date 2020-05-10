package com.jmu.client.mapper;


import com.jmu.client.entity.Label;
import com.jmu.client.vo.LabelVO;

import java.util.List;

public interface LabelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Label record);

    int insertSelective(Label record);

    Label selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Label record);

    int updateByPrimaryKey(Label record);

    List<LabelVO> listLabel();
}