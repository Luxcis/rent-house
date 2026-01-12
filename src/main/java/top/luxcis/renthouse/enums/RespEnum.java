package top.luxcis.renthouse.enums;

import lombok.Getter;

/**
 * @author zhuang
 */
@Getter
public enum RespEnum implements CodeEnum<Integer> {
    OK(0, "操作成功"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误，请联系工作人员"),
    SQL_EXCEPTION(500, "SQL异常"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "请求未授权"),
    FORBIDDEN(403, "非法访问资源"),
    NOT_FOUND(404, "无法找到请求的资源"),
    METHOD_NOT_ALLOWED(405, "不支持的HTTP方法"),
    PAYLOAD_TOO_LARGE(413, "请求实体过大"),
    NOT_MODIFIED(304, "资源未修改"),
    ;

    private final Integer code;
    private final String name;

    RespEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
