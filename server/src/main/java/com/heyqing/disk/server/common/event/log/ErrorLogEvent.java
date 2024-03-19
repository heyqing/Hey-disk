package com.heyqing.disk.server.common.event.log;

import lombok.*;
import org.springframework.context.ApplicationEvent;

/**
 * ClassName:ErrorLogEvent
 * Package:com.heyqing.disk.server.common.event.log
 * Description:
 * 错误日志事件
 *
 * @Date:2024/3/19
 * @Author:Heyqing
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ErrorLogEvent extends ApplicationEvent {

    /**
     * 错误日志内容
     */
    private String errorMsg;

    /**
     * 当前登录用户
     */
    private Long userId;

    public ErrorLogEvent(Object source, String errorMsg, Long userId) {
        super(source);
        this.errorMsg = errorMsg;
        this.userId = userId;
    }
}
