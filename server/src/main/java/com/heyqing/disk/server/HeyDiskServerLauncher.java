package com.heyqing.disk.server;

import com.heyqing.disk.core.constants.HeyDiskConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * ClassName:HeyDiskServerLauncher
 * Package:com.heyqing.disk.server
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */
@SpringBootApplication(scanBasePackages = HeyDiskConstants.BASE_COMPONENT_SCAN_PATH)
@ServletComponentScan(basePackages = HeyDiskConstants.BASE_COMPONENT_SCAN_PATH)
@EnableTransactionManagement
@MapperScan(basePackages = HeyDiskConstants.BASE_COMPONENT_SCAN_PATH + ".server.modules.**.mapper")
public class HeyDiskServerLauncher {
    public static void main(String[] args) {
        SpringApplication.run(HeyDiskServerLauncher.class,args);
    }
}
