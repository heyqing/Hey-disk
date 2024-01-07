package com.heyqing.disk.core.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.core.instrument.util.StringUtils;

import java.util.Date;

/**
 * ClassName:JWTUtil
 * Package:com.heyqing.disk.core.utils
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */


public class JWTUtil{

    public static final Long TWO_LONG = 2l;
    /**
     * 密钥
     */
    private final static String JWT_PRIVATE_KEY = "0CB16040A41140E48F2F93A7BE222C46";
    /**
     * 刷新时间
     */
    private final static String RENEWAL_TIME = "RENEWAL_TIME";

    /**
     * 生成token
     * @param subject
     * @param claimKey
     * @param claimValue
     * @param expire
     * @return
     */
    public static String generateToken(String subject,String claimKey,Object claimValue,Long expire) {
        String token = Jwts.builder()
                .setSubject(subject)
                .claim(claimKey, claimValue)
                .claim(RENEWAL_TIME, new Date(System.currentTimeMillis() + expire / TWO_LONG))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS256, JWT_PRIVATE_KEY)
                .compact();
        return token;
    }

    /**
     * 解析token
     * @param token
     * @param claimKey
     * @return
     */
    public static Object analyzeToken(String token,String claimKey){
        if(StringUtils.isBlank(token)){
            return null;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_PRIVATE_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get(claimKey);
        }catch (Exception e){
            return null;
        }
    }
}
