package com.heyqing.disk.schedule;

/**
 * ClassName:ScheduleTask
 * Package:com.heyqing.disk.schedule
 * Description:
 *          定时任务的任务接口
 * @Date:2024/1/11
 * @Author:Heyqing
 */
public interface ScheduleTask extends Runnable{

    /**
     * 获取定时任务的名称
     * @return
     */
    String getName();

}
