package com.jmu.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/6
 * @since 1.0
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SysArticleVO {
    private Long id;
    private String title;
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String sendTime;
    @JsonIgnore
    private Byte status;
    private Integer viewNum;
    private String managerName;
    private String managerCode;
    private String statusString;
    private String markdown;
}
