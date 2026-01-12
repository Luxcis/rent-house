package top.luxcis.renthouse.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * @author zhuang
 */
@Getter
public enum BillStatus implements CodeEnum<Integer> {
    CHECKED_IN(0, "入住"),
    RENEW(1, "续租"),
    CHECKED_OUT(2, "退租"),
    ;

    @EnumValue
    private final Integer code;
    private final String name;

    BillStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
