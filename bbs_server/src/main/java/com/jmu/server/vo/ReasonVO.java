package com.jmu.server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jmu.server.entity.Reason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/5
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReasonVO implements Serializable {

    private Long id;
    private String content;
    private Byte score;
    private String opName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String opTime;
    private String opCode;

    public static ReasonVO of(Reason reason) {
        return ReasonVO.builder()
                .id(reason.getId())
                .content(reason.getReason())
                .score(reason.getFraction())
                .build();
    }
}
