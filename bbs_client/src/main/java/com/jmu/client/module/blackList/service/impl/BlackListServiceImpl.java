package com.jmu.client.module.blackList.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jmu.client.entity.BlackList;
import com.jmu.client.entity.BlackListHistory;
import com.jmu.client.entity.Manager;
import com.jmu.client.entity.User;
import com.jmu.client.enums.BaseEnum;
import com.jmu.client.mapper.BlackListHistoryMapper;
import com.jmu.client.mapper.BlackListMapper;
import com.jmu.client.mapper.UserMapper;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.blackList.mapper.BlackListExtMapper;
import com.jmu.client.module.blackList.mapper.BlackListHistoryExtMapper;
import com.jmu.client.module.blackList.service.BlackListService;
import com.jmu.client.module.user.mapper.UserMapperExt;
import com.jmu.client.req.BlackListReq;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.BlackListVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/20 16:09
 * @since 1.0
 */
@Service
@Slf4j
public class BlackListServiceImpl implements BlackListService {

    @Value("${disable.score}")
    private String scoreValue;

    @Autowired
    private BlackListExtMapper blackListExtMapper;

    @Override
    public int isInBlackList(Long userId) {
        return blackListExtMapper.selectByUserId(userId);
    }


}
