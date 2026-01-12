package top.luxcis.renthouse.enums;

/**
 * @author zhuang
 */
public interface CodeEnum<T> {
    /**
     * 获取代码
     *
     * @return {@link T}
     */
    T getCode();

    /**
     * 获取中文
     *
     * @return {@link String}
     */
    String getName();
}