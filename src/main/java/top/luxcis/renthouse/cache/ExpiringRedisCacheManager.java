package top.luxcis.renthouse.cache;

import cn.hutool.core.util.NumberUtil;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;
import top.luxcis.renthouse.utils.RedisUtil;

import java.time.Duration;

/**
 * @author zhuang
 */
public class ExpiringRedisCacheManager extends RedisCacheManager {
    public ExpiringRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        // 解析TTL 默认1小时
        String ttlStr = array.length > 1 ? array[1] : "1h";
        Duration duration = convertDuration(ttlStr);
        cacheConfig = cacheConfig.entryTtl(duration);
        return super.createRedisCache(RedisUtil.KEY_PREFIX + name, cacheConfig);
    }

    /**
     * 转换有效期字符串
     *
     * @param ttlStr 有效期字符串(cacheNames = name + "#" + 时间 + d/h/m/s, 例如 xxxCache#12d)
     * @return {@link Duration}
     */
    @SuppressWarnings("preview")
    private Duration convertDuration(String ttlStr) {
        if (NumberUtil.isLong(ttlStr)) {
            return Duration.ofSeconds(Long.parseLong(ttlStr));
        }
        ttlStr = ttlStr.toUpperCase();
        if (ttlStr.lastIndexOf("D") != -1) {
            return Duration.parse(STR."P\{ttlStr}");
        }
        return Duration.parse(STR."PT\{ttlStr}");
    }
}
