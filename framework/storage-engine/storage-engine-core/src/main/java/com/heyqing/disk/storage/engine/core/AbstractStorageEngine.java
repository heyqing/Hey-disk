package com.heyqing.disk.storage.engine.core;

import com.heyqing.disk.cache.core.constants.CacheConstants;
import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

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

}
