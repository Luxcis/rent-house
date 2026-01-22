package top.luxcis.renthouse.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.luxcis.renthouse.entity.Config;
import top.luxcis.renthouse.enums.ConfigEnum;
import top.luxcis.renthouse.enums.ExceptionEnum;
import top.luxcis.renthouse.exception.BusinessException;
import top.luxcis.renthouse.mapper.ConfigMapper;
import top.luxcis.renthouse.service.ConfigService;

import java.util.List;

import static top.luxcis.renthouse.entity.def.ConfigDef.CONFIG;

/**
 * @author zhuang
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Override
    @Cacheable(value = "config", key = "#key.code")
    public Double getDoubleByEnum(ConfigEnum key) {
        return this.getConfigByCode(key.getCode()).toDouble();
    }

    @Override
    @Cacheable(value = "config", key = "#key.code")
    public Integer getIntegerByEnum(ConfigEnum key) {
        return this.getConfigByCode(key.getCode()).toInteger();
    }

    @Override
    public Config getConfigByCode(String code) {
        List<Config> list = this.list(QueryWrapper.create().eq("code", code));
        if (list.isEmpty()) {
            throw new BusinessException(ExceptionEnum.CONFIG_NOT_EXISTS);
        } else if (list.size() > 1) {
            throw new BusinessException(ExceptionEnum.DATA_ERROR);
        } else {
            return list.getFirst();
        }
    }

    @Override
    @CachePut(value = "config", key = "#code")
    public void updateConfig(String code, Double value) {
        this.updateChain().set(CONFIG.VALUE, value)
                .where(CONFIG.CODE.eq(code))
                .update();
    }
}
