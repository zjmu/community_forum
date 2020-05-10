package com.jmu.server.util;

import java.util.Calendar;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/1/21 9:19
 * @since 1.0
 */
public class GenerateLabelCodeUtil {


    /**
     * 管理员编号加时间
     *
     * @param opCode
     * @return 编号
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 9:22
     * @since 1.0
     */
    public static String generate(String opCode) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        return opCode + timeInMillis;
    }
}
