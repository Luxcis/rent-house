package top.luxcis.renthouse.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import top.luxcis.renthouse.enums.CodeEnum;
import top.luxcis.renthouse.enums.ExceptionEnum;
import top.luxcis.renthouse.exception.BusinessException;
import top.luxcis.renthouse.vo.SignVo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhuang
 */
@Slf4j
@UtilityClass
public class SignUtil {
    /**
     * 允许误差5分钟
     */
    private static final long allowDisparity = 5 * 60 * 1000;
    /**
     * redis缓存key
     */
    private static final String redisKey = "nonce:";

    /**
     * 生成签名
     *
     * @param vo 需要签名的参数
     * @return {@link String }
     */
    @SuppressWarnings("unchecked")
    public static <T extends SignVo> String sign(T vo) {
        // bean转map 忽略空字符串和列表集合
        CopyOptions opt = CopyOptions.create()
                .ignoreNullValue()
                .setIgnoreProperties(SignVo::getSignature)
                .setPropertiesFilter((field, val) -> {
                    if (val instanceof String str) {
                        return StrUtil.isNotBlank(str);
                    } else if (val instanceof Iterable || val instanceof Map) {
                        return false;
                    } else {
                        return ObjectUtil.isNotEmpty(val);
                    }
                })
                .setFieldValueEditor((field, val) -> {
                    if (val instanceof CodeEnum<?> code) {
                        return code.getCode().toString();
                    } else if (val instanceof Number num) {
                        return NumberUtil.decimalFormat("0.###", num);
                    }
                    return val.toString();
                });
        Map<String, Object> map = BeanUtil.beanToMap(vo, new LinkedHashMap<>(), opt);
        String signature = map.keySet().stream().sorted().map(key -> key + map.get(key).toString()).collect(Collectors.joining(""));
        String token = StpUtil.getTokenValue();
        return SecureUtil.sha256(signature + token).toLowerCase();
    }

    /**
     * 校验签名
     *
     * @param vo 需要校验签名的参数
     */
    public static <T extends SignVo> void checkSign(T vo) {
        // 校验签名有效期
        long timestampDisparity = Math.abs(System.currentTimeMillis() - vo.getTimestamp());
        Assert.isTrue(timestampDisparity < allowDisparity, () -> new BusinessException(ExceptionEnum.SIGN_FAIL));
        // 校验签名随机字符串
        Assert.isTrue(StrUtil.isBlank(RedisUtil.getStr(redisKey + vo.getNonce())), () -> new BusinessException(ExceptionEnum.SIGN_FAIL));
        RedisUtil.setStr(redisKey + vo.getNonce(), "1", allowDisparity / 1000 * 2);
        // 校验签名
        Assert.equals(vo.getSignature(), sign(vo), () -> new BusinessException(ExceptionEnum.SIGN_FAIL));
    }
}
