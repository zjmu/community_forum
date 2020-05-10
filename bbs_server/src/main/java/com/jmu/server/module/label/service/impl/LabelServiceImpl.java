package com.jmu.server.module.label.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmu.server.entity.Label;
import com.jmu.server.entity.ManagerInfo;
import com.jmu.server.enums.BaseEnum;
import com.jmu.server.enums.StateEnum;
import com.jmu.server.mapper.LabelMapper;
import com.jmu.server.module.label.controller.LabelController;
import com.jmu.server.module.label.mapper.LabelMapperExt;
import com.jmu.server.module.label.service.LabelService;
import com.jmu.server.req.LabelReq;
import com.jmu.server.util.GenerateLabelCodeUtil;
import com.jmu.server.util.JodaUtils;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.LabelVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/30 16:50
 * @since 1.0
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private LabelMapperExt labelMapperExt;


    /**
     * 用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 17:28
     * @since 1.0
     */
    @Override
    public List<LabelVO> getLabel() {
        return labelMapper.listLabel();
    }


    /**
     * 管理员
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 17:28
     * @since 1.0
     */
    @Override
    public PageInfo<LabelVO>
    listLabel(LabelReq labelReq) {
        Map<String, Object> map = new HashMap<>();
        map.put("label", labelReq.getLabel());
        map.put("state", StateEnum.getCodeByMessage(labelReq.getState()));
        PageHelper.startPage(labelReq.getPageNum(), labelReq.getPageSize());
        Page<LabelVO> labelVOS = labelMapperExt.listLabel(map);
        for (int i = 0; i < labelVOS.size(); i++) {
            labelVOS.get(i).setStateString(StateEnum.getMessageByCode(labelVOS.get(i).getState()));
        }
        System.out.println(labelVOS.toPageInfo());
        return labelVOS.toPageInfo();
    }


    /**
     * 增加标签
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 9:25
     * @since 1.0
     */
    @Override
    public Result<String> createLabel(LabelReq labelReq, ManagerInfo managerInfo) {
        Result<String> stringResult = validateBeforeInsert(labelReq);
        if (stringResult.getCode() == BaseEnum.FAILED.getCode()) {
            return stringResult;
        }
        Label label = Label.builder()
                .label(labelReq.getLabel().trim())
                .weight(labelReq.getWeight())
                .code(GenerateLabelCodeUtil.generate(managerInfo.getCode()))
                .createTime(JodaUtils.getCurrentDate())
                .imageUrl(labelReq.getImageUrl())
                .opCode(managerInfo.getCode())
                .state(StateEnum.NORMAL.getCode())
                .build();
        int result = labelMapper.insert(label);
        if (result >= 0) {
            return ResultUtil.success("创建标签成功！");
        }
        return ResultUtil.error("创建标签失败！");
    }

    /**
     * 更新标签
     *
     * @param labelReq 更新内容
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 10:48
     * @since 1.0
     */
    @Override
    public Result<String> updateLabel(LabelReq labelReq) {
        ManagerInfo managerInfo = LabelController.getManager();
        Result<String> stringResult = validateBeforeUpdate(labelReq);
        if (stringResult.getCode() == BaseEnum.FAILED.getCode()) {
            return stringResult;
        }
        Label label = Label.builder()
                .id(labelReq.getId())
                .weight(labelReq.getWeight())
                .code(labelReq.getCode().trim())
                .label(labelReq.getLabel().trim())
                .createTime(JodaUtils.getCurrentDate())
                .opCode(managerInfo.getCode())
                .state(StateEnum.getCodeByMessage(labelReq.getState()))
                .build();
        int result = labelMapper.updateByPrimaryKeySelective(label);
        if (result > 0) {
            return ResultUtil.success("更新标签成功！");
        }
        return ResultUtil.error("更新标签失败！");
    }


    /**
     * 逻辑删除标签
     *
     * @param id 主键
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:06
     * @since 1.0
     */
    @Override
    public Result<String> deleteLabel(Long id) {
        Label label = Label.builder()
                .id(id)
                .state(StateEnum.DELETE.getCode())
                .build();
        int result = labelMapper.updateByPrimaryKeySelective(label);
        if (result >= 0) {
            return ResultUtil.success("删除标签成功！");
        }
        return ResultUtil.error("删除标签失败！");
    }

    /**
     * 添加标签前验证
     *
     * @param labelReq 验证的内容
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 10:39
     * @since 1.0
     */
    private Result<String> validateBeforeInsert(LabelReq labelReq) {
        if (StringUtils.isBlank(labelReq.getLabel())) {
            return ResultUtil.error("标签内容不能为空！");
        }
        if (labelReq.getWeight() < 0 || labelReq.getWeight() > 100) {
            return ResultUtil.error("标签权重要在0-100");
        }
        return ResultUtil.success("验证成功！");
    }

    /**
     * 更新标签前验证
     *
     * @param labelReq 验证的内容
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 10:39
     * @since 1.0
     */
    private Result<String> validateBeforeUpdate(LabelReq labelReq) {
        if (labelReq.getId() == null || labelReq.getId() < 0) {
            return ResultUtil.error("id号错误");
        }
        if (StringUtils.isBlank(labelReq.getLabel())) {
            return ResultUtil.error("标签内容不能为空！");
        }
        if (labelReq.getWeight() < 0 || labelReq.getWeight() > 100) {
            return ResultUtil.error("标签权重要在0-100！");
        }
        if (StringUtils.isBlank(labelReq.getCode())) {
            return ResultUtil.error("标签编号不能为空！");
        }
        if (labelReq.getState() == null) {
            return ResultUtil.error("标签状态不能为空！");
        }
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put("label", labelReq.getLabel().trim());
        map2.put("code", labelReq.getCode().trim());
        Long idForLabel = labelMapperExt.selectCount(map1);
        Long idForCode = labelMapperExt.selectCount(map2);
        if (idForLabel != null && !idForLabel.equals(labelReq.getId())) {
            return ResultUtil.error("标签名字已存在！");
        }
        if (idForCode != null && !idForCode.equals(labelReq.getId())) {
            return ResultUtil.error("标签编号已存在！");
        }
        return ResultUtil.success("验证成功！");
    }
}
