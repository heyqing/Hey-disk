package com.heyqing.disk.cache.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * ClassName:RedisCacheConfig
 * Package:com.heyqing.disk.cache.redis.config
 * Description:
 *          redis cache 配置类
 *          该缓存方案不支持事务
 *          该缓存方案直接集成spring-boot-starter-data-redis，所以舍弃自定义配置，直接默认使用spring的配置
 * @Date:2024/1/10
 * @Author:Heyqing
 */
@SpringBootConfiguration
@EnableCaching
@Slf4j
public class RedisCacheConfig {

    /**
     * 定制连接和操作redis的客户端工具
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String ,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        StringRedisSerializer serializer = new StringRedisSerializer();
        RedisTemplate<String ,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        return redisTemplate;

    }

    /**
     * 定制化redis的缓存管理器
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class)));
        RedisCacheManager cacheManager = RedisCacheManager
                .builder(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .transactionAware().build();
        log.info("the redis cache manager is loaded successfully !");
        return cacheManager;
    }
}
