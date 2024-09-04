package com.heyqing.disk.storage.engine.oss;

import com.heyqing.disk.storage.engine.core.AbstractStorageEngine;
import com.heyqing.disk.storage.engine.core.context.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ClassName:OSSStorageEngine
 * Package:com.heyqing.disk.storage.engine.oss
 * Description:
 *
 * @Date:2024/3/1
 * @Author:Heyqing
 */
@Component
public class OSSStorageEngine extends AbstractStorageEngine {

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
