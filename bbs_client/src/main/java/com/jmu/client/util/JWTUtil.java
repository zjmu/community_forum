package com.jmu.client.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/4/24
 * @since 1.0
 */
public class JWTUtil {

    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @param secret   用户的密码
     * @return 正确: true；不正确：false
     */
    public static void verify(String token, String username) throws Exception {
        // 根据密码生成JWT校验器
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)
                .build();
        // 校验TOKEN
        DecodedJWT jwt = verifier.verify(token);
    }

    /**
     * 获取用户名
     *
     * @param token token中包含了用户名
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param username 用户名
     * @param secret   密码
     * @return 加密的TOKEN
     */
    public static String sign(String username) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 附带用户信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
