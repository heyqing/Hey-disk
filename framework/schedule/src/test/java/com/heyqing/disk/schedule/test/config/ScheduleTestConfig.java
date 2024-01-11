package com.heyqing.disk.schedule.test.config;

import com.heyqing.disk.core.constants.HeyDiskConstants;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName:ScheduleTestConfig
 * Package:com.heyqing.disk.schedule.test.config
 * Description:
 *
 * @Date:2024/1/11
 * @Author:Heyqing
 */
@SpringBootConfiguration
@ComponentScan(HeyDiskConstants.BASE_COMPONENT_SCAN_PATH + ".schedule")
public class ScheduleTestConfig {
}
