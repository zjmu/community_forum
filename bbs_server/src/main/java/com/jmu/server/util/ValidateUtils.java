package com.jmu.server.util;

import java.util.regex.Pattern;


/**
 * 校验中文、校验手机号、校验IP地址、校验邮箱
 *
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/6 15:20
 * @since 1.0
 */
public class ValidateUtils {

    /**
     * �ַ����Ƿ�������
     */
    public static final String REGEX_NUM = "^[0-9]+\\.{0,1}[0-9]{0,}$";

    public static boolean isNumber(String str) {
        return Pattern.matches(REGEX_NUM, str);
    }

    /**
     * 验证年龄
     */
    public static final int LOW_AGE = 0;
    public static final int HIGH_AGE = 150;

    public static boolean isAge(Byte age) {
        if (age.byteValue() >= LOW_AGE && age.byteValue() <= HIGH_AGE) {
            return true;
        }
        return false;
    }

    /**
     * У������
     */
    public static final String REGEX_EMAIL = "^\\S+@(\\S+\\.)+\\S+$";

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * ��֤����
     */
    public static final String REGEX_CHINESE = "^[\u4E00-\u9FA5]{0,}$";

    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * �ֻ���
     */
    public static final String MOBILE = "^[0-9]{11}$";

    public static boolean isMobile(String str) {
        return Pattern.matches(MOBILE, str);
    }

    /**
     * ��֤IP��ַ
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

}
