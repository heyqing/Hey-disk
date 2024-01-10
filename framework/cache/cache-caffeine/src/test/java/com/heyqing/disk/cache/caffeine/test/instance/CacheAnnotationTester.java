package com.heyqing.disk.cache.caffeine.test.instance;

import com.heyqing.disk.cache.core.constants.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * ClassName:CacheAnnotationTester
 * Package:com.heyqing.disk.cache.caffeine.test.instance
 * Description:
 *
 * @Date:2024/1/10
 * @Author:Heyqing
 */
@Component
@Slf4j
public class CacheAnnotationTester {

    /**
     * 测试自适应注解
     * id 1 id -1布隆过滤器
     * @param name
     * @return
     */
    @Cacheable(cacheNames = CacheConstants.HEY_DISK_CACHE_NAME,key = "#name" ,sync = true)
    public String testCacheable(String name){
        log.info("call com.heyqing.disk.cache.caffeine.test.instance.CacheAnnotationTester.testCacheable,param is {}",name);
        return new StringBuilder("hello ").append(name).toString();
    }
}
