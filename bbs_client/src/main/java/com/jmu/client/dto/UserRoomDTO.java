package com.jmu.client.dto;

import com.jmu.client.entity.Room;
import com.jmu.client.entity.RoomCheck;
import com.jmu.client.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/25
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomDTO implements Serializable {
    private Long id;
    private Room room;
    private User user;
    private Byte status;
    private Byte result;
    private String opName;
    private String opCode;
    private Date createTime;
    private List<RoomCheck> roomChecks;
}
