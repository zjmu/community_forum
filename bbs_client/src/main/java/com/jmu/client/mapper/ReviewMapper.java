package com.jmu.client.mapper;

import com.jmu.client.entity.Review;

public interface ReviewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Review record);

    int insertSelective(Review record);

    Review selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}