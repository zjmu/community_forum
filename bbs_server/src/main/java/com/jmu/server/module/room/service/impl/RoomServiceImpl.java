package com.jmu.server.module.room.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.server.dto.ManagerDTO;
import com.jmu.server.module.article.controller.ArticleController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jmu.server.dto.UserRoomDTO;
import com.jmu.server.entity.Manager;
import com.jmu.server.entity.Room;
import com.jmu.server.entity.RoomCheck;
import com.jmu.server.entity.TemplateData;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.entity.UserRoom;
import com.jmu.server.enums.BaseEnum;
import com.jmu.server.enums.ReviewResultEnum;
import com.jmu.server.enums.ReviewStatusEnum;
import com.jmu.server.mapper.RoomMapper;
import com.jmu.server.mapper.UserRoomMapper;
import com.jmu.server.module.room.mapper.RoomCheckMapperExt;
import com.jmu.server.module.room.mapper.RoomMapperExt;
import com.jmu.server.module.room.mapper.UserRoomMapperExt;
import com.jmu.server.module.room.service.RoomService;
import com.jmu.server.req.RoomReq;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.util.WxMessageUtil;
import com.jmu.server.vo.RoomCheckVO;
import com.jmu.server.vo.RoomVO;
import com.jmu.server.vo.UserRoomVO;
import com.jmu.server.vo.WxMessVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/18
 * @since 1.0
 */
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapperExt roomMapperExt;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private UserRoomMapper userRoomMapper;
    @Autowired
    private UserRoomMapperExt userRoomMapperExt;
    @Autowired
    private RoomCheckMapperExt roomCheckMapperExt;
    @Autowired
    private WxMessageUtil wxMessageUtil;
    @Value("${wx.message.check_result_template_id}")
    private String templateId;

    /**
     * 分页列举房屋信息
     *
     * @author zhoujinmu
     * @date 2020/2/22
     * @since 1.0
     */
    @Override
    public PageInfo<RoomVO> listRoomByPage(RoomReq roomReq) {
        Map<String, Object> conditions = new HashMap<>();
        if (!StringUtils.isBlank(roomReq.getArea())) {
            conditions.put("area", roomReq.getArea());
        }
        if (!StringUtils.isBlank(roomReq.getBuilding())) {
            conditions.put("building", roomReq.getBuilding());
        }
        if (!StringUtils.isBlank(roomReq.getUnit())) {
            conditions.put("unit", roomReq.getUnit());
        }
        if (!StringUtils.isBlank(roomReq.getFloor())) {
            conditions.put("floor", roomReq.getFloor());
        }
        System.out.println("房屋搜索条件" + conditions);
        Page<RoomVO> page = roomMapperExt.listRoomByPage(conditions);
        return page.toPageInfo();
    }

    /**
     * 获取小区
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @Override
    public List<String> listArea() {
        return roomMapperExt.listArea();
    }

    /**
     * 获取楼栋
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @Override
    public List<String> listBuilding(RoomReq roomReq) {
        if (StringUtils.isBlank(roomReq.getArea())) {
            throw new RuntimeException("请先选择小区！");
        }
        System.out.println("要获取楼栋的小区" + roomReq.getArea());
        return roomMapperExt.listBuilding(roomReq.getArea());
    }

    /**
     * 获取单元
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @Override
    public List<String> listUnit(RoomReq roomReq) {
        if (StringUtils.isBlank(roomReq.getBuilding())) {
            throw new RuntimeException("请先选择楼栋！");
        }
        System.out.println("要获取单元的小区：" + roomReq.getArea() + "楼栋：" + roomReq.getBuilding());
        List<String> unit = roomMapperExt.listUnit(roomReq.getArea(), roomReq.getBuilding());
        System.out.println("得到的单元：" + unit);
        return unit;
    }

    /**
     * 获取楼层
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @Override
    public List<String> listFloor(RoomReq roomReq) {
        if (StringUtils.isBlank(roomReq.getUnit())) {
            throw new RuntimeException("请先选择单元！");
        }
        return roomMapperExt.listFloor(roomReq.getArea(), roomReq.getBuilding(), roomReq.getUnit());
    }

    /**
     * 获取房间
     *
     * @author zhoujinmu
     * @date 2020/2/22
     * @since 1.0
     */
    @Override
    public List<String> listRoomCode(RoomReq roomReq) {
        if (StringUtils.isBlank(roomReq.getFloor())) {
            throw new RuntimeException("请先选择楼层！");
        }
        return roomMapperExt.listRoomCode(roomReq.getArea(), roomReq.getBuilding(), roomReq.getUnit(), roomReq.getFloor());
    }

    /**
     * 删除房间信息
     *
     * @author zhoujinmu
     * @date 2020/2/21
     * @since 1.0
     */
    @Override
    public int deleteRoom(Long id) {
        return roomMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新建房屋
     *
     * @author zhoujinmu
     * @date 2020/2/22
     * @since 1.0
     */
    @Override
    public Result<String> createRoom(RoomReq roomReq) {
        Result<String> validateResult = validate(roomReq);
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
        Room room = Room.builder()
                .area(roomReq.getArea())
                .building(roomReq.getBuilding())
                .unit(roomReq.getUnit())
                .floor(roomReq.getFloor())
                .roomCode(roomReq.getRoomCode())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int result = roomMapper.insertSelective(room);
        if (result > 0) {
            return ResultUtil.success("添加房屋信息成功！");
        }
        return ResultUtil.error("添加房屋信息失败！");
    }

    /**
     * 绑定房间信息
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    @Override
    @Transactional
    public Result<String> bindRoom(RoomReq roomReq) {
        //信息是否完善
        Result<String> validateResult = validate(roomReq);
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
        UserInfo user = ArticleController.addUserInfo();
        //是否存在房间
        Long roomId = roomMapperExt.getIdOfRoom(roomReq.getArea(), roomReq.getBuilding(), roomReq.getUnit(), roomReq.getFloor(), roomReq.getRoomCode());
        if (roomId == null) {
            log.error("RoomServiceImpl bindRoom 没有找到房间id,roomReq:{}", roomReq);
            return ResultUtil.error("没有找到该房间！");
        }
        //是否已绑定
        Integer count = userRoomMapperExt.getCountOfbind(user.getId());
        if (count > 0) {
            return ResultUtil.error("正在审核材料，或您已绑定房间!");
        }
        UserRoom userRoom = UserRoom.builder()
                .roomId(roomId)
                .userId(user.getId())
                .status(ReviewStatusEnum.UNREVIEWD.getCode())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        int result1 = userRoomMapper.insert(userRoom);
        List<RoomCheck> roomChecks = new ArrayList<>();
        for (String imageUrl : roomReq.getImgUrls()) {
            RoomCheck roomCheck = new RoomCheck();
            roomCheck = RoomCheck.builder()
                    .userRoomId(userRoom.getRoomId())
                    .imgurl(imageUrl)
                    .createTime(JodaUtils.getCurrentTime())
                    .build();
            roomChecks.add(roomCheck);
        }
        int result2 = roomCheckMapperExt.insertMore(roomChecks);
        if (result1 > 0 && result2 > 0) {
            return ResultUtil.success("绑定房间成功！");
        }
        return ResultUtil.error("绑定房间失败！");
    }

    /**
     * 解除房间绑定
     *
     * @author zhoujinmu
     * @date 2020/2/24
     * @since 1.0
     */
    @Override
    public Result<String> cancelBind() {
        UserInfo userInfo = ArticleController.addUserInfo();
        Integer result = roomMapperExt.deleteBindRoom(userInfo.getId());
        if (result > 0) {
            log.warn("用户解除房间绑定，用户id:{}", userInfo.getId());
            return ResultUtil.success("解除房间绑定成功！");
        }
        return ResultUtil.error("解除房间绑定失败！");
    }

    /**
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    @Override
    public PageInfo<UserRoomVO> listRoomCheck(RoomReq roomReq) {
        PageHelper.startPage(roomReq.getPageNum(), roomReq.getPageSize());
        Map<String, Object> conditions = new HashMap<>();
        if (!StringUtils.isBlank(roomReq.getCheckResult())) {
            conditions.put("result", ReviewResultEnum.getCodeByMessage(roomReq.getCheckResult()));
        }
        if (!StringUtils.isBlank(roomReq.getReviewStatus())) {
            conditions.put("status", ReviewStatusEnum.getCodeByMessage(roomReq.getReviewStatus()));
        }
        if (!StringUtils.isBlank(roomReq.getOpCode())) {
            conditions.put("opCode", roomReq.getOpCode());
        }
        if (!StringUtils.isBlank(roomReq.getStartTime()) && !StringUtils.isBlank(roomReq.getEndTime())) {
            conditions.put("startTime", roomReq.getStartTime());
            conditions.put("endTime", roomReq.getEndTime());
        }
        List<UserRoomDTO> roomCheckPage = userRoomMapperExt.listRoomCheck(conditions);
        List<UserRoomVO> userRoomVOS = roomCheckPage.stream().map(UserRoomVO::of).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(userRoomVOS);
        System.out.println("RoomServiceImpl listRoomCheck roomVOPageInfo" + pageInfo);
        return pageInfo;
    }

    /**
     * 审查房间详情
     *
     * @author zhoujinmu
     * @date 2020/2/25
     * @since 1.0
     */
    @Override
    public RoomCheckVO getRoomCheck(Long id) {
        UserRoomDTO userRoomDTO = userRoomMapperExt.getRoomCheck(id);
        RoomCheckVO roomCheckVO = RoomCheckVO.of(userRoomDTO);
        return roomCheckVO;
    }

    /**
     * 房屋绑定审查结果
     *
     * @author zhoujinmu
     * @date 2020/2/27
     * @since 1.0
     */
    @Override
    public Result<String> roomCheckResult(RoomReq roomReq, ManagerDTO manager) {
        if (ReviewResultEnum.getCodeByMessage(roomReq.getCheckResult()) == null) {
            return ResultUtil.error("请输入正确审查结果！");
        }
        UserRoom userRoom = UserRoom.builder()
                .id(roomReq.getId())
                .status(ReviewStatusEnum.AUDITED.getCode())
                .result(ReviewResultEnum.getCodeByMessage(roomReq.getCheckResult()))
                .opName(manager.getName())
                .opCode(manager.getManagerCode())
                .createTime(JodaUtils.getCurrentTime())
                .build();
        Integer result = roomCheckMapperExt.updateRoomCheckResult(userRoom);
        if (result > 0) {
            //发审核结果推送消息
            UserInfo userInfo = ArticleController.addUserInfo();
            WxMessVO wxMessVO = new WxMessVO();
            wxMessVO.setTouser(userInfo.getOpenId());
            wxMessVO.setTemplate_id(templateId);
            wxMessVO.setPage("pages/hoem/home");
            Map<String, TemplateData> m = new HashMap<>(3);
            m.put("phrase2", new TemplateData(roomReq.getCheckResult()));
            if (roomReq.getCheckResult().equals("通过")) {
                m.put("thing3", new TemplateData("恭喜你，通过了住户审核！"));
            } else {
                m.put("thing3", new TemplateData("抱歉，您未通过住户审核！"));
            }
            wxMessVO.setData(m);
            String messResult = wxMessageUtil.push(wxMessVO);
            log.info("住户信息绑定审查结果通知状态:{}", messResult);
            return ResultUtil.success("修改审查信息成功！");
        }
        log.error("RoomServiceImpl roomCheckResult userRoom:{}", userRoom);
        return ResultUtil.error("修改审查信息失败！");
    }

    private Result<String> validate(RoomReq roomReq) {
        if (StringUtils.isBlank(roomReq.getArea())) {
            return ResultUtil.error("请输入小区名字！");
        }
        if (StringUtils.isBlank(roomReq.getBuilding())) {
            return ResultUtil.error("请输入楼栋信息！");
        }
        if (StringUtils.isBlank(roomReq.getUnit())) {
            return ResultUtil.error("请输入单元消息！");
        }
        if (StringUtils.isBlank(roomReq.getFloor())) {
            return ResultUtil.error("请输入楼层信息！");
        }
        if (StringUtils.isBlank(roomReq.getRoomCode())) {
            return ResultUtil.error("请输入房间编号！");
        }
        return ResultUtil.success("验证成功！");
    }
}
