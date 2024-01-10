package com.heyqing.disk.cache.caffeine.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:CaffeineCacheProperties
 * Package:com.heyqing.disk.cache.caffeine.config
 * Description:
 *          Caffeine Cache自定义配置属性类
 * @Date:2024/1/10
 * @Author:Heyqing
 */

@Data
@Component
@ConfigurationProperties(prefix = "com.heyqing.disk.cache.caffeine")
public class CaffeineCacheProperties {

    /**
     * 缓存初始容量
     */
    private Integer initCacheCapacity = 256;

    /**
     * 缓存最大容量,超过之后根据 recently or very often （最近最少）策略进行缓存剔除
     */
    private Long maxCacheCapacity = 10000L;

    /**
     * 是否允许null最为缓存的value
     */
    private Boolean allowNullValue = Boolean.TRUE;

}
