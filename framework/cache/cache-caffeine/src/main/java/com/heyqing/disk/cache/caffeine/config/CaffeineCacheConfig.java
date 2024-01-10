package com.heyqing.disk.cache.caffeine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.heyqing.disk.cache.core.constants.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * ClassName:CaffeineCacheConfig
 * Package:com.heyqing.disk.cache.caffeine.config
 * Description:
 *
 * @Date:2024/1/10
 * @Author:Heyqing
 */
@SpringBootConfiguration
@EnableCaching
@Slf4j
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
        log.info("the caffeine cache manager is loaded successfully ! ");
        return caffeineCacheManager;
    }

}
