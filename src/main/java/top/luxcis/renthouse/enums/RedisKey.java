package top.luxcis.renthouse.enums;

/**
 * @author zhuang
 */
public interface RedisKey {
    /**
     * 获取RedisKey
     *
     * @param value value
     * @return {@link String}
     */
    String getKey(String value);
}
