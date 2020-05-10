package com.jmu.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/21 15:24
 * @since 1.0
 */
@Data
public class ArticleReviewVO {

    private Long id;
    private Long articleId;
    private Long userId;
    private String nickname;
    private String phone;
    @JsonIgnore
    private Byte status;
    private String statusString;

    private String result;
    private String opName;
    private String opCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String time;
    @JsonIgnore
    private Byte overhead;
    private String featured;
}
