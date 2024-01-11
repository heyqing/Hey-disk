package com.heyqing.disk.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

/**
 * ClassName:ScheduleTaskHolder
 * Package:com.heyqing.disk.schedule
 * Description:
 *
 * @Date:2024/1/11
 * @Author:Heyqing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTaskHolder implements Serializable {

    /**
     * 执行任务的实体
     */
    private ScheduleTask scheduleTask;

    /**
     * 执行任务的结果实体
     */
    private ScheduledFuture scheduledFuture;

}
