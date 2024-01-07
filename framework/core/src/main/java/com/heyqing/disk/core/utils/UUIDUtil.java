package com.heyqing.disk.core.utils;

import java.util.UUID;

/**
 * ClassName:UUIDUtil
 * Package:com.heyqing.disk.core.utils
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */
public class UUIDUtil {

    public static final String EMPTY_STR = "";
    public static final String HYPHEN_STR = "-";

    public static String getUUID(){
        return UUID.randomUUID().toString().replace(HYPHEN_STR,EMPTY_STR).toUpperCase();
    }
}
