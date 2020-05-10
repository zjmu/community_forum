package com.jmu.client.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/11
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class ArticleLabelReq extends Page {
    /**
     * 主键
     */
    private Long id;

    /**
     * 标签id
     */
    private Long labelId;

}
