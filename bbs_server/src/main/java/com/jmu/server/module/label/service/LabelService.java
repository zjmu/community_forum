package com.jmu.server.module.label.service;

import com.github.pagehelper.PageInfo;
import com.jmu.server.entity.ManagerInfo;
import com.jmu.server.req.LabelReq;
import com.jmu.server.util.Result;
import com.jmu.server.vo.LabelVO;

import java.util.List;

public interface LabelService {


    /**
     * 用户获取标签
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 17:26
     * @since 1.0
     */
    List<LabelVO> getLabel();


    /**
     * 管理员获取标签
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/20 17:26
     * @since 1.0
     */
    PageInfo<LabelVO> listLabel(LabelReq labelReq);


    /**
     * 添加标签
     *
     * @param labelReq    内容
     * @param managerInfo 操作人
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 9:24
     * @since 1.0
     */
    Result<String> createLabel(LabelReq labelReq, ManagerInfo managerInfo);

    /**
     * 更新标签
     *
     * @param labelReq 更新内容
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 10:39
     * @since 1.0
     */
    Result<String> updateLabel(LabelReq labelReq);


    /**
     * 删除标签
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 14:04
     * @since 1.0
     */
    Result<String> deleteLabel(Long id);
}
