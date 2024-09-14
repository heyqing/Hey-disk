package com.heyqing.disk.storage.engine.local.initializer;

import com.heyqing.disk.storage.engine.local.config.LocalStorageEngineConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * ClassName:UploadFolderAndChunksFolderInitializer
 * Package:com.heyqing.disk.storage.engine.local.initializer
 * Description:
 * 初始化上传文件根目录和文件分片存储根目录的初始器
 *
 * @Date:2024/9/14
 * @Author:Heyqing
 */
@Component
@Slf4j
public class UploadFolderAndChunksFolderInitializer implements CommandLineRunner {

    @Autowired
    private LocalStorageEngineConfig config;

    @Override
    public void run(String... args) throws Exception {
        FileUtils.forceMkdir(new File(config.getRootFilePath()));
        log.info("the RootFilePath has been create!");
        log.info("RootFilePath:{}", config.getRootFilePath());
        FileUtils.forceMkdir(new File(config.getRootFileChunkPath()));
        log.info("the RootFileChunkPath has been create!");
        log.info("RootFilePath:{}", config.getRootFileChunkPath());
    }
}
