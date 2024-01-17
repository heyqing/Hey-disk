package com.heyqing.disk.server.common.event.file;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;


import java.util.List;

/**
 * ClassName:DeleteFileEvent
 * Package:com.heyqing.disk.server.common.event.file
 * Description:
 * 文件删除事件
 *
 * @Date:2024/1/17
 * @Author:Heyqing
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DeleteFileEvent extends ApplicationEvent {

    private List<Long> fileIdList;

    public DeleteFileEvent(Object source, List<Long> fileIdList) {
        super(source);
        this.fileIdList = fileIdList;
    }

}
