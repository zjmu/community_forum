package com.jmu.server.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/21 17:18
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class ArticleReviewReq extends Page {
    private Long id;
    private Long articleId;
    private String articleCode;
    private Long userId;
    private Long labelId;
    private String reviewStatus;
    private String startTime;
    private String endTime;
    /**
     * 禁止原因
     */
    private String content;
    /**
     * 扣除分值
     */
    private Byte score;
}
