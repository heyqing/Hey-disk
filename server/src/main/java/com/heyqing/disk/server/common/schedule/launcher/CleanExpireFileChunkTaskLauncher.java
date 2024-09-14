package com.heyqing.disk.server.common.schedule.launcher;

import com.heyqing.disk.schedule.ScheduleManager;
import com.heyqing.disk.server.common.schedule.task.CleanExpireChunkFileTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * ClassName:CleanExpireFileChunkTaskLauncher
 * Package:com.heyqing.disk.server.common.schedule.launcher
 * Description:
 * 定时清理过期的文件分片触发器
 *
 * @Date:2024/9/14
 * @Author:Heyqing
 */
@Slf4j
@Component
public class CleanExpireFileChunkTaskLauncher implements CommandLineRunner {

    private static final String CRON = "1 0 0 * * ? ";

    @Autowired
    private CleanExpireChunkFileTask task;
    @Autowired
    private ScheduleManager scheduleManager;


    @Override
    public void run(String... args) throws Exception {
        scheduleManager.startTask(task, CRON);
    }
}
