package com.jmu.server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jmu.server.dto.UserRoomDTO;
import com.jmu.server.enums.ReviewResultEnum;
import com.jmu.server.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/25
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomVO implements Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private String phone;
    private String status;
    private String result;
    private String opName;
    private String opCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public static UserRoomVO of(UserRoomDTO userRoomDTO) {
        return UserRoomVO.builder()
                .id(userRoomDTO.getId())
                .opCode(userRoomDTO.getOpCode())
                .opName(userRoomDTO.getOpName())
                .phone(userRoomDTO.getUser().getPhone())
                .userId(userRoomDTO.getUser().getId())
                .userName(userRoomDTO.getUser().getName())
                .status(ReviewStatusEnum.getMessageByCode(userRoomDTO.getStatus()))
                .result(ReviewResultEnum.getMessageByCode(userRoomDTO.getResult()))
                .time(userRoomDTO.getCreateTime())
                .build();
    }

}
