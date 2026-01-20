package top.luxcis.renthouse.model;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.luxcis.renthouse.enums.RespEnum;

import java.io.Serial;
import java.io.Serializable;

/**
 * 返回体
 *
 * @author zhuang
 */
@Getter
@Setter
@NoArgsConstructor
public class Resp<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 5939697113340271505L;

    private Integer code;
    private String message;
    private boolean success;
    private long timestamp = System.currentTimeMillis();
    private T data;

    private Resp(RespEnum respEnum) {
        this(respEnum, null);
    }

    private Resp(RespEnum respEnum, T data) {
        this(respEnum, null, data);
    }

    private Resp(RespEnum respEnum, String message, T data) {
        this(respEnum == RespEnum.OK, respEnum.getCode(), StrUtil.blankToDefault(message, respEnum.getName()), data);
    }

    private Resp(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Resp<T> SUCCESS() {
        return new Resp<>(RespEnum.OK);
    }

    public static <T> Resp<T> SUCCESS(T data) {
        return new Resp<>(RespEnum.OK, data);
    }

    public static <T> Resp<T> ERROR(RespEnum respEnum, String description) {
        return ERROR(respEnum, description, null);
    }

    public static <T> Resp<T> ERROR(RespEnum respEnum, String description, T data) {
        return new Resp<>(respEnum, description, data);
    }

    public static <T> Resp<T> ERROR(int code, String description, T data) {
        return new Resp<>(false, code, description, data);
    }

    public String toString() {
        return JSONUtil.toJsonStr(this);

    }
}
