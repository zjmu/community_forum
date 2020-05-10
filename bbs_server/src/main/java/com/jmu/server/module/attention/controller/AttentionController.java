package com.jmu.server.module.attention.controller;

import com.jmu.server.expection.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jmu.server.module.article.controller.ArticleController;
import com.jmu.server.module.attention.service.AttentionService;
import com.jmu.server.entity.UserInfo;
import com.jmu.server.req.AttentionReq;
import com.jmu.server.util.Result;
import com.jmu.server.util.ResultUtil;
import com.jmu.server.vo.AttentionVO;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/14 15:28
 * @since 1.0
 */
@RestController
@RequestMapping("/attention")
@Slf4j
public class AttentionController {

    @Autowired
    private AttentionService attentionService;


    /**
     * 获取本人关注用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 9:55
     * @since 1.0
     */
    @GetMapping("/listAttentionOfUser")
    public Result<List<AttentionVO>> listAttention() {
        UserInfo userInfo = ArticleController.addUserInfo();
        List<AttentionVO> attentionVOS = attentionService.listAttention(userInfo.getId());
        return ResultUtil.success(attentionVOS);
    }


    /**
     * 获取其他人专注用户
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 9:55
     * @since 1.0
     */
    @GetMapping("/listAttentionOfOther")
    public Result<List<AttentionVO>> listAttentionOfOther(Long userId) {
        System.out.println(userId);
        List<AttentionVO> attentionVOS = attentionService.listAttention(userId);
        return ResultUtil.success(attentionVOS);
    }

    /**
     * 取消关注
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/15 9:56
     * @since 1.0
     */
    @DeleteMapping("/cancelAttention/{id}")
    public Result<String> cancel(@PathVariable("id") Long id) {
        log.info("AttentionController cancel userId:{}", id);
        try {
            attentionService.cancelAttention(ArticleController.addUserInfo().getId(), id);
        } catch (BusinessException e) {
            return ResultUtil.error("取消关注失败！");
        }
        return ResultUtil.success("取消关注成功！");
    }

    /**
     * 新建关注
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> create(@RequestBody AttentionReq attentionReq) {
        return attentionService.createAttention(attentionReq);
    }

    /**
     * 获取关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    @GetMapping("/getAttentionNum")
    public Result<Integer> getAttentionNum() {
        return ResultUtil.success(attentionService.getAttentionNum());
    }

    /**
     * 获取关注数
     *
     * @author zhoujinmu
     * @date 2020/4/13
     * @since 1.0
     */
    @GetMapping("/getAttentionNumOfOther")
    public Result<Integer> getAttentionNumOfOther(Long id) {
        return ResultUtil.success(attentionService.getAttentionNumOfOther(id));
    }
}
