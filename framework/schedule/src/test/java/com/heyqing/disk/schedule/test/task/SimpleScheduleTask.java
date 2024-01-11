package com.heyqing.disk.schedule.test.task;

import com.heyqing.disk.schedule.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClassName:SimpleScheduleTask
 * Package:com.heyqing.disk.schedule.test.task
 * Description:
 *          简单的定时任务执行逻辑
 * @Date:2024/1/11
 * @Author:Heyqing
 */
@Component
@Slf4j
public class SimpleScheduleTask implements ScheduleTask {
    @Override
    public String getName() {
        return "测试定时任务";
    }

    @Override
    public void run(){
        log.info(getName() + " 正在执行...");
    }

}
