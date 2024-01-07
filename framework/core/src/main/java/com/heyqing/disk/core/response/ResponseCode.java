package com.heyqing.disk.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName:ResponseCode
 * Package:com.heyqing.disk.core.response
 * Description:
 *          项目公用返回状态码
 * @Date:2024/1/6
 * @Author:Heyqing
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS(0,"SUCCESS"),

    /**
     * 错误
     */
    ERROR(1,"ERROR"),

    /**
     * token过期
     */
    TOKEN_EXPIRE(2,"TOKEN_EXPIRE"),

    /**
     * 参数错误
     */
    ERROR_PARAM(3,"ERROR_PARAM"),

    /**
     * 无权限访问
     */
    ACCESS_DENIED(4,"ACCESS_DENIED"),

    /**
     * 需要登录
     */
    NEED_LOGIN(10,"NEED_LOGIN");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String desc;

}
