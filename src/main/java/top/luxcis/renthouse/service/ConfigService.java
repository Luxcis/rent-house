package top.luxcis.renthouse.service;

import com.mybatisflex.core.service.IService;
import top.luxcis.renthouse.entity.Config;
import top.luxcis.renthouse.enums.ConfigEnum;

/**
 * @author zhuang
 */
public interface ConfigService extends IService<Config> {
    /**
     * 通过枚举获取浮点数配置值
     *
     * @param key 枚举
     * @return {@link String }
     */
    Double getDoubleByEnum(ConfigEnum key);

    /**
     * 通过枚举获取整数配置值
     *
     * @param key 钥匙
     * @return {@link Integer }
     */
    Integer getIntegerByEnum(ConfigEnum key);

    /**
     * 按code获取配置
     *
     * @param code code
     * @return {@link Config }
     */
    Config getConfigByCode(String code);

    /**
     * 保存配置
     *
     * @param code  code
     * @param value 配置值
     */
    void updateConfig(String code, Double value);
}
