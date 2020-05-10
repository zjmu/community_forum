package com.jmu.client.module.blackList.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.client.vo.BlackListVO;

import java.util.List;

public interface BlackListHistoryExtMapper {

    List<BlackListVO> listBlackListHistory(@Param("userId") Long userId);
}
