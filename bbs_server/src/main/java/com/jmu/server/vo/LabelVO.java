package com.jmu.server.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/30 16:44
 * @since 1.0
 */
@Data
public class LabelVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 内容
     */
    private String label;

    private String stateString;

    @JsonIgnore
    private Byte state;

    private String opName;

    private String code;

    private Integer weight;

    private String imageUrl;

    private String createTime;
}
