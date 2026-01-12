package top.luxcis.renthouse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import top.luxcis.renthouse.cache.ExpiringRedisCacheManager;

/**
 * @author zhuang
 */
@EnableCaching
@Configuration
@RequiredArgsConstructor
public class CacheConfig {
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public CacheManager cacheManager() {
        return new ExpiringRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), RedisCacheConfiguration.defaultCacheConfig());
    }
}
