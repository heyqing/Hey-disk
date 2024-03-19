package com.heyqing.disk.storage.engine.core;

import com.heyqing.disk.storage.engine.core.context.DeleteFileContext;
import com.heyqing.disk.storage.engine.core.context.StoreFileContext;

import java.io.IOException;

/**
 * ClassName:StorageEngine
 * Package:com.heyqing.disk.storage.engine.core
 * Description:
 * 文件存储引擎的顶级接口
 *
 * @Date:2024/3/1
 * @Author:Heyqing
 */
public interface StorageEngine {

    /**
     * 存储物理文件
     *
     * @param context
     * @throws IOException
     */
    void store(StoreFileContext context) throws IOException;

    /**
     * 删除物理文件
     *
     * @param context
     * @throws IOException
     */
    void delete(DeleteFileContext context) throws IOException;
}
