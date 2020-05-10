package com.jmu.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jmu.client.dto.UserDTO;
import com.jmu.client.enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/18 15:24
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private Long id;

    private String name;

    private String icon;

//    /**
//     * 头像
//     */
//    private String icon;

    private String nick;

    /**
     * 0女1男
     */
    private String sex;

    private String username;

    private String password;

    private String signature;

    private Integer age;

    private String email;

    private String phone;

    private String credibility;

    /**
     * 账号状态：0可用，1冻结，2删除
     */
    private Byte state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Date modifyTime;

    private String roomInfo;

    public static UserVO of(UserDTO userDTO) {
        UserVO userVO = UserVO.builder()
                .id(userDTO.getId())
                .age(userDTO.getAge())
                .name(userDTO.getName())
                .signature(userDTO.getSignature())
                .nick(userDTO.getNick())
                .icon(userDTO.getIcon())
                .sex(SexEnum.getMessageByCode(userDTO.getSex()))
                .state(userDTO.getState())
                .createTime(userDTO.getCreateTime())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .build();
        if (userDTO.getRoomDTO() != null && userDTO.getRoomDTO().getResult() != null && userDTO.getRoomDTO().getResult() == (byte) 1) {
            userVO.setRoomInfo(userDTO.getRoomDTO().getArea() + userDTO.getRoomDTO().getBuilding() + userDTO.getRoomDTO().getUnit() + userDTO.getRoomDTO().getFloor() + userDTO.getRoomDTO().getRoomCode());
        }

        return userVO;
    }

}
