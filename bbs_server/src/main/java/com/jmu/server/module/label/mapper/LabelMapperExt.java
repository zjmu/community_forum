package com.jmu.server.module.label.mapper;

import com.github.pagehelper.Page;
import com.jmu.server.vo.LabelVO;

import java.util.Map;

public interface LabelMapperExt {


    /**
     * 条件获取标签列表
     *
     * @param map 条件封装
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 11:15
     * @since 1.0
     */
    Page<LabelVO> listLabel(Map<String, Object> map);

    Long selectCount(Map<String, Object> map);
}
