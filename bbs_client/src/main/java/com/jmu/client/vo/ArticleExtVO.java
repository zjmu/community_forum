package com.jmu.client.vo;

import com.jmu.client.entity.ArticleExt;
import com.jmu.client.enums.ResourceEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/24 17:18
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ArticleExtVO {
    private String type;
    private String resource;

    public static ArticleExtVO of(ArticleExt articleExt) {
        return ArticleExtVO.builder()
                .type(ResourceEnum.getMenu(articleExt.getType()).getMessage())
                .resource(articleExt.getResourceUrl())
                .build();
    }
}
