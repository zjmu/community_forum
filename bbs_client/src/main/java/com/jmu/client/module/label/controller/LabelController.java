package com.jmu.client.module.label.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.client.module.label.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.client.entity.ManagerInfo;
import com.jmu.client.req.LabelReq;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.LabelVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/30 16:42
 * @since 1.0
 */
@RestController
@RequestMapping("/label")
@Slf4j
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 用户获取标签
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 17:26
     * @since 1.0
     */
    @GetMapping("/listLabel")
    public Result<List<LabelVO>> getLabel() {
        return ResultUtil.success(labelService.getLabel());
    }

    /**
     * 管理员获取标签
     *
     * @param labelReq 请求的条件
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 17:24
     * @since 1.0
     */
    @GetMapping("/listLabelByCondition")
    public Result<PageInfo<LabelVO>> getLabel(LabelReq labelReq) {
        log.info("getLabel labelReq:{}", labelReq);
        return ResultUtil.success(labelService.listLabel(labelReq));
    }


    /**
     * 添加标签
     *
     * @param labelReq 添加的数据
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 9:11
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> create(@RequestBody LabelReq labelReq) {
        log.info("create labelReq:{}", labelReq);
        ManagerInfo managerInfo = LabelController.getManager();
        return labelService.createLabel(labelReq, managerInfo);
    }


    /**
     * 更新标签
     *
     * @param labelReq 更新数据
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:09
     * @since 1.0
     */
    @PutMapping("/updateLabel")
    public Result<String> update(@RequestBody LabelReq labelReq) {
        return labelService.updateLabel(labelReq);
    }


    /**
     * 逻辑删除标签
     *
     * @param id 主键
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:09
     * @since 1.0
     */
    @DeleteMapping("/deleteLabel/{id}")
    public Result<String> deleteLabel(@PathVariable("id") Long id) {
        return labelService.deleteLabel(id);
    }


    /**
     * redis获取信息
     *
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 9:23
     * @since 1.0
     */
    public static ManagerInfo getManager() {
        ManagerInfo managerInfo = new ManagerInfo();
        managerInfo.setManagerId(1L);
        managerInfo.setName("小陈");
        managerInfo.setCode("A0001");
        return managerInfo;
    }
}
