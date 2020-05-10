package com.jmu.server.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lidinglin
 * @date 2019/11/30
 * <p>
 * Description:手机号，身份证，邮箱等信息脱敏工具类
 */
public class PrivacyDimmerUtils {
    private PrivacyDimmerUtils() {
    }

    private static final String OVERLAY = "****";
    private static final int START = 3;
    private static final int END = 7;

    /**
     * 13912310504 -> 139****0504
     *
     * @param mobile 手机号
     * @return 脱敏后邮箱
     */
    public static String maskMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return "";
        }
        return StringUtils.overlay(mobile, OVERLAY, START, END);
    }

    /**
     * 邮箱账号脱敏
     * 627500629@qq.com ->627****29@qq.com
     *
     * @param email 邮箱
     * @return 脱敏后邮箱
     */
    public static String maskEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return "";
        }
        String at = "@";
        if (!email.contains(at)) {
            return email;
        }
        //保留邮箱的注册商 比如@qq.com
        int length = StringUtils.indexOf(email, at);
        String content = StringUtils.substring(email, 0, length);
        String mask = StringUtils.overlay(content, OVERLAY, START, END);
        return mask + StringUtils.substring(email, length);
    }

    /**
     * 身份证脱敏
     * 350511198806174550  ->  350****98806174550
     *
     * @param idCard 身份证号
     * @return 脱敏后身份证号
     */
    public static String maskIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return "";
        }
        return StringUtils.overlay(idCard, OVERLAY, START, END);
    }


}

