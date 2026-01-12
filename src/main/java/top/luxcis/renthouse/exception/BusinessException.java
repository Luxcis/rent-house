package top.luxcis.renthouse.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import top.luxcis.renthouse.enums.CodeEnum;

import java.io.Serial;

/**
 * @author zhuang
 */
@Getter
@SuppressWarnings("unused")
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7918447875021519560L;

    private final int code;
    private Object data;

    /**
     * 业务异常(HTTP状态码为200)
     *
     * @param code    错误码
     * @param message 错误信息模版
     * @param args    模版参数
     */
    public BusinessException(int code, String message, Object... args) {
        super(StrUtil.format(message, args));
        this.code = code;
    }

    /**
     * 业务异常(HTTP状态码为200)
     *
     * @param exception 业务异常的枚举值
     * @param args      模版参数
     */
    public BusinessException(CodeEnum<Integer> exception, Object... args) {
        this(exception.getCode(), exception.getName(), args);
    }

    /**
     * 业务异常(HTTP状态码为200)
     *
     * @param data      ResponseData
     * @param exception 业务异常的枚举值
     * @param args      模版参数
     */
    public BusinessException(Object data, CodeEnum<Integer> exception, Object... args) {
        this(data, exception.getCode(), exception.getName(), args);
    }

    public BusinessException(Object data, int code, String message, Object... args) {
        super(StrUtil.format(message, args));
        this.code = code;
        this.data = data;
    }

}
