package com.jmu.client.vo;

import lombok.Data;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/14
 * @since 1.0
 */
@Data
public class SystemArticleVO {

    private Long id;
    private String title;
    private String markdown;
    private String image;
    private String viewNum;
    private String time;
    private String icon;
    private String nickname;

}
