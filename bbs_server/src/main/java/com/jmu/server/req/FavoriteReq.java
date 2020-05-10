package com.jmu.server.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/3/13
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class FavoriteReq extends Page {
    private Long id;
    /**
     * 文章id
     */
    private Long articleId;

    private Long userId;
}
