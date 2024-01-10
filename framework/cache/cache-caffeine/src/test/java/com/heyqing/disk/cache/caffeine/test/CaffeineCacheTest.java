package com.heyqing.disk.cache.caffeine.test;


import cn.hutool.core.lang.Assert;
import com.heyqing.disk.cache.caffeine.test.config.CaffeineCacheConfig;
import com.heyqing.disk.cache.caffeine.test.instance.CacheAnnotationTester;
import com.heyqing.disk.cache.core.constants.CacheConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:CaffeineCacheTest
 * Package:com.heyqing.disk.cache.caffeine.test
 * Description:
 *          caffeine缓存单元测试
 * @Date:2024/1/10
 * @Author:Heyqing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CaffeineCacheConfig.class)
public class CaffeineCacheTest {
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private CacheAnnotationTester cacheAnnotationTester;

    /**
     * 简单测试CacheCaffeine的功能以及获取Cache对象的功能
     */
    @Test
    public void caffeineCacheManagerTest(){
        Cache cache = cacheManager.getCache(CacheConstants.HEY_DISK_CACHE_NAME);
        Assert.notNull(cache);
        cache.put("name","value");
        String value = cache.get("name",String.class);
        Assert.isTrue("value".equals(value));
    }

    @Test
    public void caffeineCacheAnnotationTest(){
        for (int i = 0; i < 2; i++) {
            cacheAnnotationTester.testCacheable("heyqing");
        }
    }
}
