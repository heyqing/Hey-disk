package com.heyqing.disk.schedule;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * ClassName:ScheduleConfig
 * Package:com.heyqing.disk.schedule
 * Description:
 *          定时模块配置类
 *          配置定时器执行器
 * @Date:2024/1/11
 * @Author:Heyqing
 */
@SpringBootConfiguration
public class ScheduleConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

}
