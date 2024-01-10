package com.heyqing.disk.cache.caffeine.test.config;

import com.github.benmanes.caffeine.cache.Caffeine;

import com.heyqing.disk.cache.core.constants.CacheConstants;
import com.heyqing.disk.core.constants.HeyDiskConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName:CaffeineCacheConfig
 * Package:com.heyqing.disk.cache.caffeine.test.config
 * Description:
 *
 * @Date:2024/1/10
 * @Author:Heyqing
 */
@SpringBootConfiguration
@EnableCaching
@ComponentScan(value = HeyDiskConstants.BASE_COMPONENT_SCAN_PATH + ".cache.caffeine.test")
public class CaffeineCacheConfig {

    @Autowired
    private CaffeineCacheProperties properties;

    @Bean
    public CacheManager caffeineCacheManager(){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(CacheConstants.HEY_DISK_CACHE_NAME);
        caffeineCacheManager.setAllowNullValues(properties.getAllowNullValue());
        Caffeine<Object,Object> caffeineBuilder = Caffeine.newBuilder()
                .initialCapacity(properties.getInitCacheCapacity())
                .maximumSize(properties.getMaxCacheCapacity());
        caffeineCacheManager.setCaffeine(caffeineBuilder);
        return caffeineCacheManager;
    }

}