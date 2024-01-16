package com.heyqing.disk.server.common.annotation;

import java.lang.annotation.*;

/**
 * ClassName:LoginIgnore
 * Package:com.heyqing.disk.server.common.annotation
 * Description:
 * 该注解主要影响那些不需要登录的接口
 * 标注该注解的方法会自动屏蔽同一的登录校验逻辑
 *
 * @Date:2024/1/16
 * @Author:Heyqing
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginIgnore {
}
