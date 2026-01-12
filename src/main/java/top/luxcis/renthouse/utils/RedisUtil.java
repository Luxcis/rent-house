package top.luxcis.renthouse.utils;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.KeyScanOptions;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import top.luxcis.renthouse.enums.RedisKey;
import top.luxcis.renthouse.enums.TimeUnits;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuang
 */
@Slf4j
@SuppressWarnings("unused")
public class RedisUtil {
    public static final String KEY_PREFIX = "framework:";
    private static final StringRedisTemplate REDIS = SpringUtil.getBean(StringRedisTemplate.class);

    /**
     * 判断是否存在缓存
     *
     * @param key key
     * @return boolean
     */
    public static boolean exists(RedisKey redisKey, String key) {
        return exists(redisKey.getKey(key));
    }

    /**
     * 判断是否存在缓存
     *
     * @param key key
     * @return boolean
     */
    public static boolean exists(String key) {
        return REDIS.hasKey(key);
    }

    /**
     * 设置失效时间
     *
     * @param key    key
     * @param expire 失效时间，单位秒
     */
    public static void expire(RedisKey redisKey, String key, long expire) {
        expire(redisKey.getKey(key), expire);
    }

    /**
     * 设置失效时间
     *
     * @param key    key
     * @param expire 失效时间，单位秒
     */
    public static void expire(String key, long expire) {
        REDIS.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 添加缓存，默认7200秒
     *
     * @param key   key
     * @param value {@link Object } 缓存内容
     * @return boolean 缓存结果
     */
    public static boolean set(RedisKey redisKey, String key, Object value) {
        return set(redisKey, key, value, TimeUnits.HOUR.getSeconds(2));
    }

    /**
     * 添加缓存
     *
     * @param key    key
     * @param value  {@link Object } 缓存内容
     * @param expire 失效时间，单位秒
     * @return boolean
     */
    public static boolean set(RedisKey redisKey, String key, Object value, long expire) {
        return set(redisKey.getKey(key), value, expire);
    }

    /**
     * 添加缓存
     *
     * @param key    key
     * @param value  {@link Object } 缓存内容
     * @param expire 失效时间，单位秒
     * @return boolean
     */
    public static boolean set(String key, Object value, long expire) {
        String json = JSONUtil.toJsonStr(value);
        return setStr(key, json, expire);
    }

    /**
     * 添加缓存，默认7200秒
     *
     * @param key   key
     * @param value {@link String } 缓存内容
     * @return boolean 缓存结果
     */
    public static boolean setStr(RedisKey redisKey, String key, String value) {
        return setStr(redisKey.getKey(key), value, TimeUnits.HOUR.getSeconds(2));
    }

    public static boolean setStr(RedisKey redisKey, String key, String value, long expire) {
        return setStr(redisKey.getKey(key), value, expire);
    }

    /**
     * 添加缓存
     *
     * @param key    key
     * @param value  {@link String } 缓存内容
     * @param expire 失效时间，单位秒
     * @return boolean
     */
    public static boolean setStr(String key, String value, long expire) {
        try {
            REDIS.opsForValue().set(KEY_PREFIX + key, value, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("存缓存异常, key = {}", key, e);
            return false;
        }
    }

    /**
     * 更新缓存
     *
     * @param key    key
     * @param value  value
     * @param encode 是否加密
     * @return boolean
     */
    public static boolean update(RedisKey redisKey, String key, String value, boolean encode) {
        return update(redisKey.getKey(key), value, encode);
    }

    /**
     * 更新缓存
     *
     * @param key    key
     * @param value  value
     * @param encode 是否加密
     * @return boolean
     */
    public static boolean update(String key, String value, boolean encode) {
        try {
            REDIS.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("更新缓存异常, key = {}", key, e);
            return false;
        }
    }

    /**
     * 获取缓存实体类
     *
     * @param key  关键字
     * @param type 实体类type
     * @return {@link T}
     */
    public static <T> T get(RedisKey redisKey, String key, TypeReference<T> type) {
        String str = getStr(redisKey.getKey(key));
        if (StrUtil.isBlank(str)) {
            return null;
        }
        return JSONUtil.toBean(str, type, false);
    }

    /**
     * 获取缓存实体类
     *
     * @param key   key
     * @param clazz 实体类class
     * @return {@link T }
     */
    public static <T> T get(RedisKey redisKey, String key, Class<T> clazz) {
        String str = getStr(redisKey.getKey(key));
        if (StrUtil.isBlank(str)) {
            return null;
        }
        return JSONUtil.toBean(str, clazz);
    }

    /**
     * 获取缓存JsonObject
     *
     * @param key 钥匙
     * @return {@link JSONObject }
     */
    public static JSONObject get(RedisKey redisKey, String key) {
        String str = getStr(redisKey.getKey(key));
        if (StrUtil.isBlank(str)) {
            return null;
        }
        return JSONUtil.parseObj(str);
    }

    public static <T> T get(String key, Class<T> clazz) {
        String str = getStr(key);
        if (StrUtil.isBlank(str)) {
            return null;
        }
        return JSONUtil.toBean(str, clazz);
    }

    /**
     * 获取缓存
     *
     * @param key key
     * @return {@link String }
     */
    public static String getStr(RedisKey redisKey, String key) {
        return getStr(redisKey.getKey(key));
    }

    /**
     * 获取缓存
     *
     * @param key key
     * @return {@link String }
     */
    public static String getStr(String key) {
        try {
            return REDIS.opsForValue().get(key);
        } catch (Exception e) {
            log.error("取缓存异常, key = {}", key, e);
            return null;
        }
    }

    /**
     * 查询redis中key
     *
     * @param redisKey RedisKey
     * @param key      key
     * @return {@link List}<{@link String}>
     */
    public static List<String> keys(RedisKey redisKey, String key) {
        return keys(redisKey.getKey(key));
    }

    /**
     * 查询redis中key
     *
     * @param key key
     * @return {@link List}<{@link String}>
     */
    public static List<String> keys(String key) {
        List<String> keys = new ArrayList<>();
        REDIS.execute((RedisCallback<Object>) connection -> {
            try (Cursor<byte[]> cursor = connection.scan(
                    (KeyScanOptions) KeyScanOptions.scanOptions()
                            .type(DataType.STRING)
                            .count(Long.MAX_VALUE)
                            .match(key)
                            .build()
            )) {
                cursor.forEachRemaining(item -> keys.add(RedisSerializer.string().deserialize(item)));
                return null;
            } catch (Exception e) {
                log.error("key查询异常, key = {}", key, e);
                throw e;
            }
        });
        return keys;
    }

    /**
     * 移除缓存
     *
     * @param key key
     * @return boolean
     */
    public static Boolean remove(RedisKey redisKey, String key) {
        return remove(redisKey.getKey(key));
    }

    /**
     * 移除缓存
     *
     * @param key key
     * @return boolean
     */
    public static Boolean remove(String key) {
        try {
            REDIS.delete(keys(key));
            return true;
        } catch (Exception e) {
            log.error("清除缓存异常, key = {}", key, e);
            return false;
        }
    }
}
