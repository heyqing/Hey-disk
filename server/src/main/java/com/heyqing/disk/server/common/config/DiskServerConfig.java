package com.heyqing.disk.server.common.config;

import com.heyqing.disk.core.constants.HeyDiskConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:DiskServerConfig
 * Package:com.heyqing.disk.server.common.config
 * Description:
 *
 * @Date:2024/7/10
 * @Author:Heyqing
 */
@Component
@ConfigurationProperties(prefix = "com.heyqing.disk.server")
@Data
public class DiskServerConfig {
    /**
     * 文件分片上传的过期时间（default:1 days）
     */
    private Integer chunkFileExpirationDays = HeyDiskConstants.ONE_INT;
}
