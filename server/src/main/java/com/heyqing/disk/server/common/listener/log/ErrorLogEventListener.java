package com.heyqing.disk.server.common.listener.log;

import com.heyqing.disk.core.utils.IdUtil;
import com.heyqing.disk.server.common.event.log.ErrorLogEvent;
import com.heyqing.disk.server.modules.log.entity.HeyDiskErrorLog;
import com.heyqing.disk.server.modules.log.service.IErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName:ErrorLogEventListener
 * Package:com.heyqing.disk.server.common.listener.log
 * Description:
 * 系统错误日志监听器
 *
 * @Date:2024/3/19
 * @Author:Heyqing
 */
@Component
public class ErrorLogEventListener {

    @Autowired
    private IErrorLogService iErrorLogService;

    /**
     * 监听系统错误日志事件，并保存在数据库
     *
     * @param event
     */
    @EventListener(ErrorLogEvent.class)
    public void saveEventLog(ErrorLogEvent event) {
        HeyDiskErrorLog record = new HeyDiskErrorLog();
        record.setId(IdUtil.get());
        record.setLogContent(event.getErrorMsg());
        record.setLogStatus(0);
        record.setCreateUser(event.getUserId());
        record.setCreateTime(new Date());
        record.setUpdateUser(event.getUserId());
        record.setUpdateTime(new Date());
        iErrorLogService.save(record);
    }

}
