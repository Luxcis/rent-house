package top.luxcis.renthouse.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * @author zhuang
 */
@Getter
public enum RoomStatus implements CodeEnum<Integer> {
    FOR_RENT(0, "空置中"),
    RENTED(1, "已出租"),
    STOP_RENT(2, "暂停出租"),
    ;

    @EnumValue
    private final Integer code;
    private final String name;

    RoomStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RoomStatus parse(BillStatus status) {
        return switch (status) {
            case CHECKED_IN, RENEW -> RoomStatus.RENTED;
            case CHECKED_OUT -> RoomStatus.FOR_RENT;
        };
    }
}
