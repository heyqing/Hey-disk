package com.heyqing.disk.swagger2;

import com.heyqing.disk.core.constants.HeyDiskConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:Swagger2ConfigProperties
 * Package:com.heyqing.disk.swagger2
 * Description:
 *              swagger2配置属性
 * @Date:2024/1/7
 * @Author:Heyqing
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2ConfigProperties {

    private boolean show = true;

    private String groupName = "Hey-Disk";

    private String basePackage = HeyDiskConstants.BASE_COMPONENT_SCAN_PATH;

    private String title = "Hey-Disk-Server";

    private String description = "Hey-Disk-Server";

    private String termsOfServiceUrl = "http://127.0.0.1:${server.port}";

    private String contactName = "heyqing";

    private String contactUrl = "https://gitee.com/heyqing";

    private String contactEmail = "heyqing0@126.com ";

    private String version = "1.0";

}
