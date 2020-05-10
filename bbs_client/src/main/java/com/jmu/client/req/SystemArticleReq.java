package com.jmu.client.req;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/14
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class SystemArticleReq extends Page {
    private Long sysArticleId;
    private String title;
    private String markdown;
    private String imageUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    private String status;
    private String opCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    private Integer viewNum;
}
