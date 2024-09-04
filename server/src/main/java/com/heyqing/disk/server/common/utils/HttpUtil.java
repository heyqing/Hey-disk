package com.heyqing.disk.server.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:HttpUtil
 * Package:com.heyqing.disk.server.common.utils
 * Description:
 *
 * @Date:2024/9/4
 * @Author:Heyqing
 */
public class HttpUtil {

    /**
     * 添加跨域响应头
     *
     * @param response
     */
    public static void addCorsResponseHeader(HttpServletResponse response) {
        for (CorsConfigEnum corsConfigEnum : CorsConfigEnum.values()) {
            response.setHeader(corsConfigEnum.getKey(), corsConfigEnum.getValue());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CorsConfigEnum {
        /**
         * 允许所有远程访问
         */
        CORS_ORIGIN("Access-Control-Allow-Origin", "*"),
        /**
         * 允许认证
         */
        CORS_CREDENTIALS("Access-Control-Allow-Credentials", "true"),
        /**
         * 允许远程调用的请求类型
         */
        CORS_METHODS("Access-Control-Allow-Methods", "POST,GET,PATCH,DELETE,PUT"),
        /**
         * 指定本次预检请求有效期，单位：秒
         */
        CORS_MAX_AGE("Access-Control-Max-Age", "3600"),
        /**
         * 允许所有请求头
         */
        CORS_HEADERS("Access-Control-Allow-Headers", "*");
        private String key;
        private String value;
    }
}
