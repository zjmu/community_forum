package com.jmu.server.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/18 14:29
 * @since 1.0
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Page {
    private int pageNum;
    private int pageSize;
}
