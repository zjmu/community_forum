package com.jmu.server.module.blackList.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.module.blackList.mapper.BlackListExtMapper;
import com.jmu.server.module.blackList.service.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jmu.server.entity.BlackList;
import com.jmu.server.entity.BlackListHistory;
import com.jmu.server.entity.User;
import com.jmu.server.enums.BaseEnum;
import com.jmu.server.mapper.BlackListHistoryMapper;
import com.jmu.server.mapper.BlackListMapper;
import com.jmu.server.mapper.UserMapper;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.blackList.mapper.BlackListHistoryExtMapper;
import com.jmu.server.module.user.mapper.UserMapperExt;
import com.jmu.server.req.BlackListReq;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.BlackListVO;

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
    private BlackListMapper blackListMapper;
    @Autowired
    private BlackListHistoryExtMapper blackListHistoryExtMapper;
    @Autowired
    private BlackListHistoryMapper blackListHistoryMapper;
    @Autowired
    private BlackListExtMapper blackListExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMapperExt userMapperExt;

    /**
     * 设置黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @Override
    public Result<String> createBlackList(Long id, Integer score, String detail, ManagerDTO manager) {
        if (blackListExtMapper.selectByUserId(id) > 0) {
            return ResultUtil.error("用户已在黑名单里！");
        }
        BlackList blackList = BlackList.builder()
                .userId(id)
                .detail(detail)
                .leaveDay(Integer.parseInt(scoreValue) - score)
                .createTime(JodaUtils.getCurrentTime())
                .opId(manager.getId())
                .score(score)
                .build();
        int result = blackListMapper.insert(blackList);
        if (result > 0) {
            return ResultUtil.success("创建黑名单成功！");
        } else {
            return ResultUtil.error("创建黑名单失败！");
        }
    }

    /**
     * 黑名单记录
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @Override
    public Result<String> createBlackListHistory(Long id, Integer score, String detail,ManagerDTO manager) {
        BlackListHistory blackListHistory = BlackListHistory.builder()
                .userId(id)
                .detail(detail)
                .createTime(JodaUtils.getCurrentTime())
                .opId(manager.getId())
                .score(score)
                .build();
        int result = blackListHistoryMapper.insert(blackListHistory);
        if (result > 0) {
            return ResultUtil.success("创建黑名单历史成功！");
        } else {
            return ResultUtil.error("创建黑名单历史失败！");
        }
    }

    /**
     * 查看是否在黑名单中
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @Override
    public int isInBlackList(Long userId) {
        return blackListExtMapper.selectByUserId(userId);
    }

    /**
     * 根据条件查询黑名单
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @Override
    public PageInfo<BlackListVO> listBlackListByPage(BlackListReq blackListReq) {
        Map map = new HashMap();
        if (StringUtils.isNotBlank(blackListReq.getPhone())) {
            map.put("phone", blackListReq.getPhone());
        }
        if (StringUtils.isNotBlank(blackListReq.getUserName())) {
            map.put("userName", blackListReq.getUserName());
        }
        map.put("leaveDay", blackListReq.getLeaveDay());
        if (StringUtils.isNotBlank(blackListReq.getStartTime()) && StringUtils.isNotBlank(blackListReq.getEndTime())) {
            map.put("startTime", blackListReq.getStartTime());
            map.put("startTime", blackListReq.getEndTime());
        }
        PageHelper.startPage(blackListReq.getPageNum(), blackListReq.getPageSize());
        Page<BlackListVO> blackListVOPage = blackListExtMapper.listBlackListByConditions(map);
        return blackListVOPage.toPageInfo();
    }

    /**
     * 从黑名单中移除
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @Override
    public Result<String> delete(Long userId) {
        User user = User.builder()
                .id(userId)
                .credibility(85)
                .build();
        userMapper.updateByPrimaryKeySelective(user);
        int blackListResult = blackListExtMapper.deleteByUserId(userId);
        if (blackListResult > 0) {
            return ResultUtil.success("该用户从黑名单移除成功！");
        }
        return ResultUtil.error("用户从黑名单移除失败！");
    }

    /**
     * 黑名单历史记录
     *
     * @author zhoujinmu
     * @date 2020/4/21
     * @since 1.0
     */
    @Override
    public Result<List<BlackListVO>> listBlackListHistory(Long userId) {
        return ResultUtil.success(blackListHistoryExtMapper.listBlackListHistory(userId));
    }

    @Override
    @Transactional
    public Result<String> managerCreateBlackList(BlackListReq blackListReq,ManagerDTO manager) {
        Result<String> validateResult = validateBeforeCreateBlackList(blackListReq);
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
        Result<String> blackListResult = createBlackList(blackListReq.getUserId(), blackListReq.getScore(), blackListReq.getDetail(),manager);
        if (blackListResult.getCode() == BaseEnum.FAILED.getCode()) {
            throw new RuntimeException("添加黑名单失败！");
        }
        Result<String> blackListHistoryResult = createBlackListHistory(blackListReq.getUserId(), blackListReq.getScore(), blackListReq.getDetail(),manager);
        if (blackListHistoryResult.getCode() == BaseEnum.FAILED.getCode()) {
            throw new RuntimeException("添加黑名单历史失败！");
        }
        User user = User.builder()
                .id(blackListReq.getUserId())
                .credibility(blackListReq.getScore())
                .build();
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result <= 0) {
            throw new RuntimeException("更新用户信息失败！");
        }
        return ResultUtil.success("添加黑名单成功！");
    }

    @Override
    public void dealCronJob() {
        int updateNum = userMapperExt.addCredibility();
        blackListExtMapper.addScore();
        int deleteNum = blackListExtMapper.deleteFromBlackList(Integer.valueOf(scoreValue));
        log.info("增加信誉度人数:{},从黑名单移除人数:{}", updateNum, deleteNum);
    }

    private Result<String> validateBeforeCreateBlackList(BlackListReq blackListReq) {
        if (blackListReq.getScore() > Integer.valueOf(scoreValue)) {
            return ResultUtil.error("设置的用户信誉度未达到禁言标准！");
        }
        User user = userMapper.selectByPrimaryKey(blackListReq.getUserId());
        if (user == null) {
            return ResultUtil.error("该用户不存在，请检查！");
        }
        return ResultUtil.success("通过校验！");
    }
}
