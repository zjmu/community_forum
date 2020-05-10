package com.jmu.server.vo;

import com.jmu.server.dto.UserRoomDTO;
import com.jmu.server.entity.RoomCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/2/25
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RoomCheckVO implements Serializable {
    private String name;
    private String phone;
    private String area;
    private String building;
    private String unit;
    private String floor;
    private String roomCode;
    private List<String> imageUrls;

    public static RoomCheckVO of(UserRoomDTO userRoomDTO) {
        List<String> imgUrls = new ArrayList<>();
        for (RoomCheck roomCheck : userRoomDTO.getRoomChecks()) {
            imgUrls.add(roomCheck.getImgurl());
        }
        return RoomCheckVO.builder()
                .name(userRoomDTO.getUser().getName())
                .phone(userRoomDTO.getUser().getPhone())
                .area(userRoomDTO.getRoom().getArea())
                .building(userRoomDTO.getRoom().getBuilding())
                .unit(userRoomDTO.getRoom().getUnit())
                .floor(userRoomDTO.getRoom().getFloor())
                .roomCode(userRoomDTO.getRoom().getRoomCode())
                .imageUrls(imgUrls)
                .build();
    }
}
