package com.heyqing.disk.server.modules.user.constants;

/**
 * ClassName:UserConstants
 * Package:com.heyqing.disk.server.modules.user.constants
 * Description:
 * 用户模块的常量类
 *
 * @Date:2024/1/14
 * @Author:Heyqing
 */
public interface UserConstants {

    /**
     * 登录用户的用户id的key值
     */
    String LOGIN_USER_ID = "LOGIN_USER_ID";

    /**
     * 用户登录缓存前缀
     */
    String USER_LOGIN_PREFIX = "USER_LOGIN_";

    /**
     *用户忘记密码-重置密码临时token的key
     */
    String FORGET_USERNAME = "FORGET_USERNAME";

    /**
     * 一天的毫秒值
     */
    Long ONE_DAY_LONG = 24L * 60L * 60L * 1000L;

    /**
     * 五分钟的毫秒值
     */
    Long FIVE_MINUTES_LONG = 5L * 60L * 1000L;
}
