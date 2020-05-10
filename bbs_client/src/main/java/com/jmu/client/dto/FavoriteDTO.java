package com.jmu.client.dto;

import lombok.Data;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/11
 * @since 1.0
 */
@Data
public class FavoriteDTO {
    private Long userId;
    private Long articleId;
}
