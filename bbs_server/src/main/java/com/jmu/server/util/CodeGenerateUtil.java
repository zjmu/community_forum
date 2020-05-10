package com.jmu.server.util;

import java.util.Calendar;

/**
 * 生成文章编号
 *
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/23 15:40
 * @since 1.0
 */
public class CodeGenerateUtil {

    private final static String SYSTEM_ARTICLE_PRE = "S";

    /**
     * 生成文章编号
     *
     * @author zhoujinmu
     * @date 2020/2/16
     * @since 1.0
     */
    public static String generateArticleCode(Long userId) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        String code = userId + String.valueOf(timeInMillis);
        return code;
    }

    /**
     * 标签编号：管理员编号加时间
     *
     * @param opCode
     * @return 编号
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 9:22
     * @since 1.0
     */
    public static String generateLabelCode(String opCode) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        return opCode + timeInMillis;
    }

    /**
     * 系统文章编号：管理员编号加时间
     *
     * @param opCode
     * @return 编号
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/21 9:22
     * @since 1.0
     */
    public static String generateSystemArticleCode(String opCode) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        return opCode + timeInMillis;
    }

     /**
       * 生成管理员编号
       * @author zhoujinmu
       * @date 2020/4/29
       * @since 1.0
       */
    public static String generateManagerCode() {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        return "M" + timeInMillis;
    }
}
