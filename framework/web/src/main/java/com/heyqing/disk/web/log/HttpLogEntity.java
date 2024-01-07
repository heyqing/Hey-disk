package com.heyqing.disk.web.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Map;

/**
 * ClassName:HttpLogEntity
 * Package:com.heyqing.disk.web.log
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Log4j2
public class HttpLogEntity {

    /**
     * 请求资源
     */
    private String requestUrl;

    /**
     * 被调方法
     */
    private String method;

    /**
     * 调用者地址
     */
    private String remoteAddr;

    /**
     * 调用的IP地址
     */
    private String ip;

    /**
     * 请求头
     */
    private Map<String ,String> requestHeaders;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 响应头
     */
    private Map<String ,String > responseHeaders;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 接口耗时
     */
    private String resolveTime;

    /**
     * 打印日志
     */
    public void print(){
        log.info("====================HTTP CALL START====================");
        log.info("callTime: {}", DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(new Date()));
        log.info("requestUrl: {}",getRequestUrl());
        log.info("method: {}",getMethod());
        log.info("remoteAddr: {}",getRemoteAddr());
        log.info("ip: {}",getIp());
        log.info("requestHeaders: {}",getRequestHeaders());
        log.info("requestParam: {}",getRequestParam());
        log.info("status: {}",getStatus());
        log.info("responseHeaders: {}",getResponseHeaders());
        log.info("responseData: {}",getResponseData());
        log.info("resolveTime: {}",getResolveTime());
        log.info("====================HTTP CALL FINISH====================");
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("====================HTTP CALL START====================");
        stringBuilder.append("callTime");
        stringBuilder.append(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(new Date()));
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("request:");
        stringBuilder.append(getRequestUrl());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("method:");
        stringBuilder.append(getMethod());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("remoteAddr:");
        stringBuilder.append(getRemoteAddr());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("ip:");
        stringBuilder.append(getIp());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("requestHeaders:");
        stringBuilder.append(getRequestHeaders());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("requestParam:");
        stringBuilder.append(getRequestParam());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("status:");
        stringBuilder.append(getStatus());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("responseHeaders:");
        stringBuilder.append(getResponseHeaders());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("responseData:");
        stringBuilder.append(getResponseData());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("resolveTime:");
        stringBuilder.append(getResolveTime());
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("====================HTTP CALL FINISH====================");
        return stringBuilder.toString();
    }
}
