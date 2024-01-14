package com.heyqing.disk.server.common.utils;

import com.heyqing.disk.core.constants.HeyDiskConstants;

import java.util.Objects;

/**
 * ClassName:UserIdUtil
 * Package:com.heyqing.disk.server.common.utils
 * Description:
 * 用户id存储工具类
 *
 * @Date:2024/1/14
 * @Author:Heyqing
 */
public class UserIdUtil {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的用户id
     *
     * @param userId
     */
    public static void set(Long userId) {
        threadLocal.set(userId);
    }

    /**
     * 获取当前线程的用户id
     *
     * @return
     */
    public static Long get() {

        Long userId = threadLocal.get();
        if (Objects.isNull(userId)) {
            return HeyDiskConstants.ZERO_LONG;
        }
        return userId;
    }

}
