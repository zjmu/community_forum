package com.jmu.client.vo;

import lombok.Data;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/14 9:28
 * @since 1.0
 */
@Data
public class ArticleCommentVO {

    private Long id;
    private String sendNick;
    private Long sendUserId;
    private String returnNick;
    private Long returnUserId;
    private String text;
}
