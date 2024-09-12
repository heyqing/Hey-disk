package com.heyqing.disk.server.common.event.search;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * ClassName:UserSearchEvent
 * Package:com.heyqing.disk.server.common.event.search
 * Description:
 * 用户搜索事件
 *
 * @Date:2024/9/12
 * @Author:Heyqing
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserSearchEvent extends ApplicationEvent {
    private String keyword;
    private Long userId;

    public UserSearchEvent(Object source, String keyword, Long userId) {
        super(source);
        this.keyword = keyword;
        this.userId = userId;
    }
}
