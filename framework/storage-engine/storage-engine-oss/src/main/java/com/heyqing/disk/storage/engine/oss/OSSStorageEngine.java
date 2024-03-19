package com.heyqing.disk.storage.engine.oss;

import com.heyqing.disk.storage.engine.core.AbstractStorageEngine;
import com.heyqing.disk.storage.engine.core.context.DeleteFileContext;
import com.heyqing.disk.storage.engine.core.context.StoreFileContext;
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
}
