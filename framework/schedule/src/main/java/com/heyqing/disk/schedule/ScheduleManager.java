package com.heyqing.disk.schedule;

import com.heyqing.disk.core.exception.HeyDiskFrameworkException;
import com.heyqing.disk.core.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * ClassName:ScheduleManager
 * Package:com.heyqing.disk.schedule
 * Description:
 *          定时任务管理器
 *          1、创建并启动一个定时任务
 *          2、停止一个定时任务
 *          3、更新一个定时任务
 * @Date:2024/1/11
 * @Author:Heyqing
 */
@Component
@Slf4j
public class ScheduleManager {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * 内部正在执行的定时任务
     */
    private Map<String ,ScheduleTaskHolder> cache = new ConcurrentHashMap<>();//线程安全的

    /**
     * 启动一个定时任务
     * @param scheduleTask
     * @param cron
     * @return
     */
    public String startTask(ScheduleTask scheduleTask ,String cron){
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule((Runnable) scheduleTask,new CronTrigger(cron));
        String key = UUIDUtil.getUUID();
        ScheduleTaskHolder holder = new ScheduleTaskHolder(scheduleTask,scheduledFuture);
        cache.put(key,holder);
        log.info("{} 启动成功！唯一标识为：{}",scheduleTask.getName(),key);
        return key;
    }

    /**
     * 停止一个定时任务
     * @param key
     */
    public void stopTask(String key){
        if (StringUtils.isBlank(key)){
            return;
        }
        ScheduleTaskHolder holder = cache.get(key);
        if (Objects.isNull(holder)){
            return;
        }
        ScheduledFuture scheduledFuture = holder.getScheduledFuture();
        boolean cancel = scheduledFuture.cancel(true);
        if (cancel){
            log.info("{} 停止成功！唯一标识为：{}",holder.getScheduleTask().getName(),key);
        }else {
            log.error("{} 停止失败！唯一标识为：{}",holder.getScheduleTask().getName(),key);
        }
    }

    /**
     * 更新一个定时任务的时间
     * @param key
     * @param cron
     * @return
     */
    public String changeTask(String key,String cron){
        if (StringUtils.isAnyBlank(key,cron)){
            throw new HeyDiskFrameworkException("定时任务的唯一标识以及新的执行表达式不能为空");
        }
        ScheduleTaskHolder holder = cache.get(key);
        if (Objects.isNull(holder)){
            throw new HeyDiskFrameworkException(key + " 唯一标识不存在！");
        }
        stopTask(key);
        return startTask(holder.getScheduleTask(), cron);
    }

}
