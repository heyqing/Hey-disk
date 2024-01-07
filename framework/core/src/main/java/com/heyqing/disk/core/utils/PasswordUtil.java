package com.heyqing.disk.core.utils;

/**
 * ClassName:PasswordUtil
 * Package:com.heyqing.disk.core.utils
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */

public class PasswordUtil {

    /**
     * 随机生成盐值
     * @return
     */
    public static String getSalt(){
        return MessageDigestUtil.md5(UUIDUtil.getUUID());
    }

    /**
     * 密码加密
     * @param salt
     * @param inputPassword
     * @return
     */
    public static String encryptPassword(String salt,String inputPassword){
        return MessageDigestUtil.sha256(MessageDigestUtil.sha1(inputPassword)+salt);
    }
}
