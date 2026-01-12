package top.luxcis.renthouse.enums;

import lombok.Getter;

/**
 * @author zhuang
 */
@Getter
public enum ConfigEnum implements CodeEnum<String> {
    ELECTRIC_UNIT_PRICE("electric_unit_price", "电费单价", "1.5"),
    WATER_UNIT_PRICE("water_unit_price", "水费单价", "1.5"),
    MANAGEMENT_PRICE("management_price", "管理费", "20"),
    GARBAGE_PRICE("garbage_price", "垃圾清理费", "10"),
    ;

    private final String code;
    private final String name;
    private final String value;

    ConfigEnum(String code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }
}
