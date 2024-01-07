package com.heyqing.disk.core.utils;

import cn.hutool.core.codec.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * ClassName:AES128Util
 * Package:com.heyqing.disk.core.utils
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */

public class AES128Util {

    /**
     * 默认向量常量
     */
    public static final String IV = "ahewjmfrkfd@#$!%";
    /**
     * 密钥
     */
    private static final String P_KEY = StringUtils.reverse(IV);

    private static final String AES_STR = "AES";
    private static final String INSTANCE_STR = "AES/CBC/PKCS5Padding";

    /**
     * 加密 128位
     * @param content
     * @return
     */
    public static byte[] aesEncrypt(byte[] content){
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(P_KEY.getBytes(),AES_STR);
            Cipher cipher = Cipher.getInstance(INSTANCE_STR);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,iv);
            byte[] encrypt = cipher.doFinal(content);
            return encrypt;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param content
     * @return
     */
    public static byte[] aesDecode(byte[] content){
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(P_KEY.getBytes(),AES_STR);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance(INSTANCE_STR);
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,iv);
            byte[] result = cipher.doFinal(content);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密字符串 128位
     * @param content
     * @return
     */
    public static String aesEncryptString(String content){
        if (StringUtils.isBlank(content)){
            return StringUtils.EMPTY;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(P_KEY.getBytes(), AES_STR);
            Cipher cipher = Cipher.getInstance(INSTANCE_STR);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,iv);
            byte[] encrypt = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.encode(encrypt);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }


    public static String aesDecryptString(String content) {
        if (StringUtils.isBlank(content)) {
            return StringUtils.EMPTY;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(P_KEY.getBytes(), AES_STR);
            Cipher cipher = Cipher.getInstance(INSTANCE_STR);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] result = cipher.doFinal(Base64.decode(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

}
