package com.jmu.server.module.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.articleComment.mapper.ArticleCommentExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jmu.server.dto.RoomDTO;
import com.jmu.server.dto.UserDTO;
import com.jmu.server.entity.User;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.enums.BaseEnum;
import com.jmu.server.enums.SexEnum;
import com.jmu.server.enums.StateEnum;
import com.jmu.server.enums.UserTypeEnum;
import com.jmu.server.mapper.UserMapper;
import com.jmu.server.module.user.mapper.UserMapperExt;
import com.jmu.server.module.user.service.UserService;
import com.jmu.server.req.LoginReq;
import com.jmu.server.req.RoomReq;
import com.jmu.server.req.UserReq;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.PrivacyDimmerUtils;
import com.jmu.server.util.RedisUtil;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.util.ValidateUtils;
import com.jmu.server.vo.UserForManagerVO;
import com.jmu.server.vo.UserVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/17 9:59
 * @since 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapperExt userMapperExt;
    @Autowired
    private ArticleCommentExtMapper articleCommentExtMapper;
    @Value("${disable.score}")
    private String score;

    @Autowired
    private UserMapper userMapper;


    /**
     * 注册用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/17 16:49
     * @since 1.0
     */
    @Override
    public Result<String> createUser(UserReq userReq) {

        Result<String> stringResult = validate(userReq);
        if (stringResult.getCode() == BaseEnum.FAILED.getCode()) {
            return stringResult;
        }

        User user = User.builder().age(userReq.getAge())
                .createTime(JodaUtils.getCurrentDate())
                .credibility(100)
                .email(userReq.getEmail())
                .icon(userReq.getIcon())
                .name(userReq.getName())
                .nickname(userReq.getNickname())
                .phone(PrivacyDimmerUtils.maskMobile(userReq.getPhone()))
                .sex(SexEnum.getCodeByMessage(userReq.getSex()))
                .signature(userReq.getSignature())
                .state(StateEnum.NORMAL.getCode())
                .type((byte) 0)
                .username(userReq.getUsername())
                .build();
        int result = userMapper.insertSelective(user);
        if (result > 0) {
            return ResultUtil.success("添加用户成功！");
        }

        return ResultUtil.error("添加用户失败！");
    }


    /**
     * 条件查询用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 11:39
     * @since 1.0
     */
    @Override
    public PageInfo<UserForManagerVO> selectByCondition(UserReq userReq) {
        PageHelper.startPage(userReq.getPageNum(), userReq.getPageSize());
        Map<String, Object> map = new HashMap<>();
        map.put("name", userReq.getName());
        map.put("phone", userReq.getPhone());
        if (!StringUtils.isBlank(userReq.getState())) {
            map.put("state", StateEnum.getCodeByMessage(userReq.getState().trim()));
        }
        if (!StringUtils.isBlank(userReq.getStartTime())) {
            map.put("startTime", JodaUtils.stringToDate(userReq.getStartTime().trim()));
        }
        if (!StringUtils.isBlank(userReq.getEndTime())) {
            map.put("endTime", JodaUtils.stringToDate(userReq.getEndTime().trim()));
        }
        com.github.pagehelper.Page<UserForManagerVO> user = userMapperExt.getBySelect(map);
        for (int i = 0; i < user.size(); i++) {
            user.get(i).setUserType(UserTypeEnum.getMessageByCode(user.get(i).getType()));
        }
        return user.toPageInfo();
    }

    /**
     * 修改用户信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 14:57
     * @since 1.0
     */
    @Override
    public Result<String> updateUser(UserReq userReq) {
        User user = User.builder()
                .id(userReq.getId())
                .name(userReq.getName())
                .email(userReq.getEmail())
                .phone(userReq.getPhone())
                .credibility(userReq.getCredibility())
                .type(UserTypeEnum.getCodeByMessage(userReq.getUserType()))
                .build();
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result >= 0) {
            return ResultUtil.success("更新用户信息成功！");
        } else {
            return ResultUtil.error("更新用户信息失败！");
        }
    }

    /**
     * 逻辑删除用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:48
     * @since 1.0
     */
    @Override
    public Result<String> deleteUser(Long id) {
        User user = User.builder()
                .id(id)
                .state(StateEnum.DELETE.getCode())
                .build();
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result <= 0) {
            return ResultUtil.error("删除用户失败！");
        }
        return ResultUtil.success("删除用户成功！");
    }

    /**
     * 展示个人用户信息
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    @Override
    public UserVO getUser() {
        UserInfo user = ArticleController.addUserInfo();
        UserDTO userDTO = userMapperExt.getUser(user.getOpenId());
        UserVO userVO = UserVO.of(userDTO);
        if (userDTO.getCredibility() != null && userDTO.getCredibility() > Integer.parseInt(score)) {
            userVO.setCredibility(userDTO.getCredibility() + "(正常)");
        } else {
            userVO.setCredibility(userDTO.getCredibility() + "(禁言)");
        }
        System.out.println(userVO);
        return userVO;
    }

    /**
     * 获取和房间绑定的用户
     *
     * @author zhoujinmu
     * @date 2020/3/3
     * @since 1.0
     */
    @Override
    public List<UserVO> getUserOfRoom(RoomReq roomReq) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("area", roomReq.getArea());
        conditions.put("building", roomReq.getBuilding());
        conditions.put("unit", roomReq.getUnit());
        conditions.put("floor", roomReq.getFloor());
        conditions.put("roomCode", roomReq.getRoomCode());
        List<UserDTO> userDTOS = userMapperExt.getUserOfRoom(conditions);
        List<UserVO> userVOS = userDTOS.stream().map(UserVO::of).collect(Collectors.toList());
        log.info("UserServiceImpl getUserOfRoom userVOS:{}", userVOS);
        return userVOS;
    }

    @Override
    public Result<String> loginWithOpenId(String openId, LoginReq loginReq) {
        Long id = userMapperExt.getUserByOpenId(openId);
        System.out.println("登录检查是否有openId用户" + id);
        if (id == null) {
            User user = User.builder()
                    .createTime(JodaUtils.getCurrentDate())
                    .icon(loginReq.getIcon())
                    .nickname(loginReq.getNick())
                    .sex(loginReq.getSex())
                    .state(StateEnum.NORMAL.getCode())
                    .type((byte) 0)
                    .openId(openId)
                    .build();
            int result = userMapper.insertSelective(user);
            if (result <= 0) {
                return ResultUtil.error("登录失败！");
            }
            id = user.getId();
        }
        //放入redis
        return ResultUtil.success("登录成功！");
    }

    /**
     * 客户端：获取同小区用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @Override
    public List<UserVO> listSameArea() {
        UserInfo userInfo = ArticleController.addUserInfo();
        RoomDTO roomDTO = userMapperExt.getRoomInfoOfUser(userInfo.getId());
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("area", roomDTO.getArea());
        List<UserDTO> userDTOS = userMapperExt.getUserOfRoom(conditions);
        List<UserVO> userVOS = userDTOS.stream().map(UserVO::of).collect(Collectors.toList());
        log.info("UserServiceImpl listSameArea userVOS:{}", userVOS);
        return userVOS;
    }

    /**
     * 获取同单元用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @Override
    public List<UserVO> listSameUnit() {
        UserInfo userInfo = ArticleController.addUserInfo();
        RoomDTO roomDTO = userMapperExt.getRoomInfoOfUser(userInfo.getId());
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("area", roomDTO.getArea());
        conditions.put("building", roomDTO.getBuilding());
        conditions.put("unit", roomDTO.getUnit());
        List<UserDTO> userDTOS = userMapperExt.getUserOfRoom(conditions);
        List<UserVO> userVOS = userDTOS.stream().map(UserVO::of).collect(Collectors.toList());
        log.info("UserServiceImpl listSameUnit userVOS:{}", userVOS);
        return userVOS;
    }

    /**
     * 获取同楼栋用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @Override
    public List<UserVO> listSameBuilding() {
        UserInfo userInfo = ArticleController.addUserInfo();
        RoomDTO roomDTO = userMapperExt.getRoomInfoOfUser(userInfo.getId());
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("area", roomDTO.getArea());
        conditions.put("building", roomDTO.getBuilding());
        List<UserDTO> userDTOS = userMapperExt.getUserOfRoom(conditions);
        List<UserVO> userVOS = userDTOS.stream().map(UserVO::of).collect(Collectors.toList());
        log.info("UserServiceImpl listSameBuilding userVOS:{}", userVOS);
        return userVOS;
    }

    /**
     * 获取同楼层用户
     *
     * @author zhoujinmu
     * @date 2020/4/3
     * @since 1.0
     */
    @Override
    public List<UserVO> listSameFloor() {
        UserInfo userInfo = ArticleController.addUserInfo();
        RoomDTO roomDTO = userMapperExt.getRoomInfoOfUser(userInfo.getId());
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("area", roomDTO.getArea());
        conditions.put("building", roomDTO.getBuilding());
        conditions.put("unit", roomDTO.getUnit());
        conditions.put("floor", roomDTO.getFloor());
        List<UserDTO> userDTOS = userMapperExt.getUserOfRoom(conditions);
        List<UserVO> userVOS = userDTOS.stream().map(UserVO::of).collect(Collectors.toList());
        log.info("UserServiceImpl listSameFloor userVOS:{}", userVOS);
        return userVOS;
    }

    @Override
    @Transactional
    public Result<String> update(UserReq userReq) {
        Result<String> stringResult = validateBeforeUpdate(userReq);
        if (stringResult.getCode() == BaseEnum.FAILED.getCode()) {
            return stringResult;
        }
        UserInfo userInfo = ArticleController.addUserInfo();
        User user = User.builder()
                .id(userInfo.getId())
                .nickname(userReq.getNickname())
                .email(userReq.getEmail())
                .name(userReq.getName())
                .phone(userReq.getPhone())
                .sex(SexEnum.getCodeByMessage(userReq.getSex()))
                .age(userReq.getAge())
                .signature(userReq.getSignature())
                .build();
        int result = userMapper.updateByPrimaryKeySelective(user);
        articleCommentExtMapper.updateNickByUserId(userInfo.getId(), userReq.getNickname());
        if (result > 0) {
            return ResultUtil.success("更新信息成功！");
        } else {
            return ResultUtil.error("更新信息失败！");
        }
    }

    /**
     * 客户端：根据id他人详细信息
     *
     * @author zhoujinmu
     * @date 2020/4/17
     * @since 1.0
     */
    @Override
    public UserVO getUserOfOther(Long id) {
        UserDTO userDTO = userMapperExt.getUserOfOther(id);
        UserVO userVO = UserVO.of(userDTO);
        if (userDTO.getCredibility() != null && userDTO.getCredibility() > Integer.parseInt(score)) {
            userVO.setCredibility(userDTO.getCredibility() + "(正常)");
        } else {
            userVO.setCredibility(userDTO.getCredibility() + "(禁言)");
        }
        return userVO;
    }

    private Result<String> validate(UserReq userReq) {
        if (!StringUtils.isBlank(userReq.getSex()) &&
                !(userReq.getSex().equals("男") || userReq.getSex().equals("女"))) {
            return ResultUtil.error("请输入正确性别！");
        }
        if (StringUtils.isBlank(userReq.getUsername())) {
            return ResultUtil.error("用户名不能为空！");
        }
        if (StringUtils.isBlank(userReq.getName())) {
            return ResultUtil.error("名字不能为空！");
        }
        if (StringUtils.isBlank(userReq.getNickname())) {
            return ResultUtil.error("昵称不能为空！");
        }
        if (StringUtils.isBlank(userReq.getEmail())) {
            return ResultUtil.error("邮箱不能为空！");
        }
        if (StringUtils.isBlank(userReq.getPhone())) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (!ValidateUtils.isAge(userReq.getAge())) {
            return ResultUtil.error("请输入正确年龄！");
        }
        if (!ValidateUtils.isMobile(userReq.getPhone())) {
            return ResultUtil.error("手机格式不正确！");
        }
        if (!ValidateUtils.isEmail(userReq.getEmail())) {
            return ResultUtil.error("邮箱格式不正确！");
        }
        return ResultUtil.success("验证成功！");
    }

    private Result<String> validateBeforeUpdate(UserReq userReq) {
        if (!ValidateUtils.isMobile(userReq.getPhone())) {
            return ResultUtil.error("手机格式不正确！");
        }
        if (!ValidateUtils.isEmail(userReq.getEmail())) {
            return ResultUtil.error("邮箱格式不正确！");
        }
        return ResultUtil.success("验证成功！");
    }


}
