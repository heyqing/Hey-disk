package com.heyqing.disk.schedule.test;

import com.heyqing.disk.schedule.ScheduleManager;
import com.heyqing.disk.schedule.test.config.ScheduleTestConfig;
import com.heyqing.disk.schedule.test.task.SimpleScheduleTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:ScheduleTaskTest
 * Package:com.heyqing.disk.schedule.test
 * Description:
 *          定时任务模块单元测试
 * @Date:2024/1/11
 * @Author:Heyqing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ScheduleTestConfig.class)
public class ScheduleTaskTest {

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private SimpleScheduleTask scheduleTask;

    @Test
    public void testRunScheduleTask() throws Exception{
        String cron = "0/5 * * * * ? ";
        String key = scheduleManager.startTask(scheduleTask, cron);
        Thread.sleep(10000);
        cron = "0/1 * * * * ? ";
        key = scheduleManager.changeTask(key,cron);
        Thread.sleep(10000);
        scheduleManager.stopTask(key);
    }
}
