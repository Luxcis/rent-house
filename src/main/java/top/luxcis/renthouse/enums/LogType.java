package top.luxcis.renthouse.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * @author zhuang
 */
@Getter
public enum LogType implements CodeEnum<String> {
    // 系统操作
    LOGIN("LOGIN", "登录"),
    LOGOUT("LOGOUT", "登出"),
    RESET_PW("RESET_PW", "重置密码"),

    // 数据操作
    SAVE("SAVE", "保存数据"),
    VIEW("VIEW", "查看详情"),
    EDIT("EDIT", "修改数据"),
    DELETE("DELETE", "删除数据"),
    QUERY("QUERY", "查询数据"),
    TASK("TASK", "任务执行"),
    OTHER("OTHER", "其他请求");

    @EnumValue
    private final String code;
    private final String name;

    LogType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
