package com.jmu.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/21 9:08
 * @since 1.0
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ManagerInfo {
    private Long managerId;
    private String name;
    private String code;
}
