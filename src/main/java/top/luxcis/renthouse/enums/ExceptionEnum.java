package top.luxcis.renthouse.enums;

import lombok.Getter;

/**
 * @author zhuang
 */
@Getter
public enum ExceptionEnum implements CodeEnum<Integer> {
    USER_NOTE_ACTIVE(40001, "用户未激活"),
    USER_LOGIN_FAIL(40002, "用户名或密码错误"),
    SIGN_FAIL(40003, "签名校验失败"),
    DATA_ERROR(50000, "数据异常"),
    WECHAT_API_ERROR(59001, "微信接口调用失败({}): {}"),
    CONFIG_NOT_EXISTS(51001, "配置不存在"),
    CONFIG_NOT_NUMBER(51002, "配置内容不是数字"),
    BILL_WATER_ERROR(52003, "水表读数不能比上月读数小"),
    BILL_ELECTRIC_ERROR(52004, "电表读数不能比上月读数小"),
    ;

    private final Integer code;
    private final String name;

    ExceptionEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
