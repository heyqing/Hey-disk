package com.heyqing.disk.storage.engine.local;

import com.heyqing.disk.core.utils.FileUtil;
import com.heyqing.disk.storage.engine.core.AbstractStorageEngine;
import com.heyqing.disk.storage.engine.core.context.*;
import com.heyqing.disk.storage.engine.local.config.LocalStorageEngineConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * ClassName:LocalStorageEngine
 * Package:com.heyqing.disk.storage.engine
 * Description:
 * 本地文件存储引擎实现类
 *
 * @Date:2024/3/1
 * @Author:Heyqing
 */
@Component
public class LocalStorageEngine extends AbstractStorageEngine {

    @Autowired
    private LocalStorageEngineConfig config;

    /**
     * 保存物理文件
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doStore(StoreFileContext context) throws IOException {
        String basePath = config.getRootFilePath();
        String realFilePath = FileUtil.generateStoreFileRealPath(basePath, context.getFilename());
        FileUtil.writeStream2File(context.getInputStream(), new File(realFilePath), context.getTotalSize());
        context.setRealPath(realFilePath);
    }

    /**
     * 删除物理文件
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doDelete(DeleteFileContext context) throws IOException {
        FileUtil.deleteFiles(context.getRealFilePathList());
    }

    /**
     * 保存文件分片
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doStoreChunk(StoreFileChunkContext context) throws IOException {
        String basePath = config.getRootFileChunkPath();
        String realFilePath = FileUtil.generateStoreFileChunkRealPath(basePath, context.getIdentifier(), context.getChunkNumber());
        FileUtil.writeStream2File(context.getInputStream(), new File(realFilePath), context.getTotalSize());
        context.setRealPath(realFilePath);
    }

    /**
     * 文件分片合并
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doMergeFile(MergeFileContext context) throws IOException {
        String basePath = config.getRootFilePath();
        String realFilePath = FileUtil.generateStoreFileRealPath(basePath, context.getFilename());
        FileUtil.createFile(new File(realFilePath));
        List<String> chunkPaths = context.getRealPathList();
        for (String chunkPath : chunkPaths) {
            FileUtil.appendWrite(Paths.get(realFilePath), new File(chunkPath).toPath());
        }
        FileUtil.deleteFiles(chunkPaths);
        context.setRealPath(realFilePath);
    }

    /**
     * 读取文件内容到输出流中
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doReadFile(ReadFileContext context) throws IOException {
        File file = new File(context.getRealPath());
        FileUtil.writeFile2OutputStream(new FileInputStream(file),context.getOutputStream(),file.length());
    }
}
