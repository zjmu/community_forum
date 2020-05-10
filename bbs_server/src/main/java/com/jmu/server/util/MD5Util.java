package com.jmu.server.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/25
 * @since 1.0
 */
public class MD5Util {

    private static final String PASSWORD_SALT = "5371f568a45e5ab1f442c38e0932aef24447139b";

    public static String encrypt(String username, String password) {
        String hashAlgorithmName = "md5";
        String salt = PASSWORD_SALT + username + PASSWORD_SALT;
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
        return new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations).toString();
    }
}
