package com.heyqing.disk.server;

import com.heyqing.disk.core.constants.HeyDiskConstants;
import com.heyqing.disk.core.response.Result;
import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

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
@RestController
@Api("测试接口类")
@Validated
public class HeyDiskServerLauncher {
    public static void main(String[] args) {
        SpringApplication.run(HeyDiskServerLauncher.class,args);
    }

    @GetMapping("hello")
    public Result<String > hello(@NotBlank(message = "name不能为空") String name){
        return Result.success("Hello " + name + " ! ^^ ");
    }


}
