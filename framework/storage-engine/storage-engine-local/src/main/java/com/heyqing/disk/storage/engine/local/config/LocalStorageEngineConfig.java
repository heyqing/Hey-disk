package com.heyqing.disk.storage.engine.local.config;

import com.heyqing.disk.core.utils.FileUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:LocalStorageEngineConfig
 * Package:com.heyqing.disk.storage.engine.local.config
 * Description:
 *
 * @Date:2024/3/19
 * @Author:Heyqing
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.heyqing.disk.storage.engine.local")
public class LocalStorageEngineConfig {

    /**
     * 实际存放路径的前缀
     */
    private String rootFilePath = FileUtil.generateDefaultStoreFileRealPath();
    /**
     * 实际存放文件的路径前缀
     */
    private String rootFileChunkPath = FileUtil.generateDefaultStoreFileChunkRealPath();
}
