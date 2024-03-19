package com.heyqing.disk.storage.engine.core;

import cn.hutool.core.lang.Assert;
import com.heyqing.disk.cache.core.constants.CacheConstants;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import com.heyqing.disk.storage.engine.core.context.DeleteFileContext;
import com.heyqing.disk.storage.engine.core.context.StoreFileContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.IOException;
import java.util.Objects;

/**
 * ClassName:AbstractStorageEngine
 * Package:com.heyqing.disk.storage.engine.core
 * Description:
 * 顶级文件存储引擎的公用父类
 *
 * @Date:2024/3/1
 * @Author:Heyqing
 */
public abstract class AbstractStorageEngine implements StorageEngine {

    @Autowired
    private CacheManager cacheManager;

    /**
     * 公共的获取缓存方法
     *
     * @return
     */
    protected Cache getCache() {
        if (Objects.isNull(cacheManager)) {
            throw new HeyDiskBusinessException("the cache manage is empty!");
        }
        return cacheManager.getCache(CacheConstants.HEY_DISK_CACHE_NAME);
    }

    /**
     * 保存物理文件
     * <p>
     * 1、参数校验
     * 2、执行动作
     *
     * @param context
     * @throws IOException
     */
    @Override
    public void store(StoreFileContext context) throws IOException {
        checkStoreFileContext(context);
        doStore(context);
    }


    /**
     * 删除物理文件
     * <p>
     * 1、参数校验
     * 2、执行动作
     *
     * @param context
     * @throws IOException
     */
    @Override
    public void delete(DeleteFileContext context) throws IOException {
        checkDeleteFileContext(context);
        doDelete(context);
    }


    /*************************************************************private******************************************/

    /**
     * 执行保存物理文件的动作
     * 下沉到具体的子类去实现
     *
     * @param context
     * @throws IOException
     */
    protected abstract void doStore(StoreFileContext context) throws IOException;

    /**
     * 校验上传物理文件的上下文信息
     *
     * @param context
     */
    private void checkStoreFileContext(StoreFileContext context) {
        Assert.notBlank(context.getFilename(), "文件名称不能为空");
        Assert.notNull(context.getTotalSize(), "文件总大小不能为空");
        Assert.notNull(context.getInputStream(), "文件不能为空");
    }

    /**
     * 执行删除物理文件的动作
     * 下沉到具体子类去实现
     *
     * @param context
     * @throws IOException
     */
    protected abstract void doDelete(DeleteFileContext context) throws IOException;

    /**
     * 校验删除物理文件上下文信息
     *
     * @param context
     */
    private void checkDeleteFileContext(DeleteFileContext context) {
        Assert.notEmpty(context.getRealFilePathList(), "要删除的文件路径列表不能为空");
    }
}
