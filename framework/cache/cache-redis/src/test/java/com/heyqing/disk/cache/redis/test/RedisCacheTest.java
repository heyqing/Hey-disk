package com.heyqing.disk.cache.redis.test;

import cn.hutool.core.lang.Assert;
import com.heyqing.disk.cache.core.constants.CacheConstants;
import com.heyqing.disk.cache.redis.test.instance.CacheAnnotationTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:RedisCacheTest
 * Package:com.heyqing.disk.cache.redis.test
 * Description:
 *
 * @Date:2024/1/10
 * @Author:Heyqing
 */
@SpringBootTest(classes = RedisCacheTest.class)
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisCacheTest {

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
