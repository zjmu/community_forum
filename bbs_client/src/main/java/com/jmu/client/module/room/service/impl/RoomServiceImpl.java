package com.jmu.client.module.room.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmu.client.module.article.controller.ArticleController;
import com.jmu.client.module.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jmu.client.dto.UserRoomDTO;
import com.jmu.client.entity.Manager;
import com.jmu.client.entity.Room;
import com.jmu.client.entity.RoomCheck;
import com.jmu.client.entity.TemplateData;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.entity.UserRoom;
import com.jmu.client.enums.BaseEnum;
import com.jmu.client.enums.ReviewResultEnum;
import com.jmu.client.enums.ReviewStatusEnum;
import com.jmu.client.mapper.RoomMapper;
import com.jmu.client.mapper.UserRoomMapper;
import com.jmu.client.module.room.mapper.RoomCheckMapperExt;
import com.jmu.client.module.room.mapper.RoomMapperExt;
import com.jmu.client.module.room.mapper.UserRoomMapperExt;
import com.jmu.client.req.RoomReq;
import com.jmu.client.util.JodaUtils;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.util.WxMessageUtil;
import com.jmu.client.vo.RoomCheckVO;
import com.jmu.client.vo.RoomVO;
import com.jmu.client.vo.UserRoomVO;
import com.jmu.client.vo.WxMessVO;

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
        List<String> unit = roomMapperExt.listUnit(roomReq.getArea(), roomReq.getBuilding());
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
     * 绑定房间信息
     *
     * @author zhoujinmu
     * @date 2020/2/23
     * @since 1.0
     */
    @Override
    @Transactional
    public Result<String> bindRoom(RoomReq roomReq,UserInfo user) {
        //信息是否完善
        Result<String> validateResult = validate(roomReq);
        if (validateResult.getCode() == BaseEnum.FAILED.getCode()) {
            return validateResult;
        }
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
    public Result<String> cancelBind(UserInfo userInfo) {
        Integer result = roomMapperExt.deleteBindRoom(userInfo.getId());
        if (result > 0) {
            log.warn("用户解除房间绑定，用户id:{}", userInfo.getId());
            return ResultUtil.success("解除房间绑定成功！");
        }
        return ResultUtil.error("解除房间绑定失败！");
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
