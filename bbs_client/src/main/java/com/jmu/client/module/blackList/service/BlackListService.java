package com.jmu.client.module.blackList.service;

import com.github.pagehelper.PageInfo;
import com.jmu.client.req.BlackListReq;
import com.jmu.client.util.Result;
import com.jmu.client.vo.BlackListVO;

import java.util.List;

public interface BlackListService {

    /**
     * 发文章前查看是否在黑名单中
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    int isInBlackList(Long userId);

}
