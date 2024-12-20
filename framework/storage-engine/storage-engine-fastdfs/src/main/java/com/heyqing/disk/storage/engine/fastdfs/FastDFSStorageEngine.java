package com.heyqing.disk.storage.engine.fastdfs;

import com.heyqing.disk.storage.engine.core.AbstractStorageEngine;
import com.heyqing.disk.storage.engine.core.context.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ClassName:FastDFSStorageEngine
 * Package:com.heyqing.disk.storage.engine.fastdfs
 * Description:
 * 基于fastDFS实现的文件存储引擎
 *
 * @Date:2024/3/1
 * @Author:Heyqing
 */
@Component
public class FastDFSStorageEngine extends AbstractStorageEngine {
    /**
     * 保存物理文件
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doStore(StoreFileContext context) throws IOException {

    }

    /**
     * 删除物理文件
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doDelete(DeleteFileContext context) throws IOException {

    }

    /**
     * 保存文件分片
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doStoreChunk(StoreFileChunkContext context) throws IOException {

    }

    /**
     * 文件分片合并
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doMergeFile(MergeFileContext context) throws IOException {

    }

    /**
     * 读取文件内容到输出流中
     *
     * @param context
     * @throws IOException
     */
    @Override
    protected void doReadFile(ReadFileContext context) throws IOException {

    }
}
