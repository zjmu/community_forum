package com.jmu.server.module.blackList.mapper;

import org.apache.ibatis.annotations.Param;
import com.jmu.server.vo.BlackListVO;

import java.util.List;

public interface BlackListHistoryExtMapper {

    List<BlackListVO> listBlackListHistory(@Param("userId") Long userId);
}
