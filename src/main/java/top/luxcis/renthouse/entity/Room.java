package top.luxcis.renthouse.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mybatisflex.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.luxcis.renthouse.enums.RoomStatus;
import top.luxcis.renthouse.model.BaseEntity;
import top.luxcis.renthouse.serial.EnumJsonSerializer;

import java.io.Serial;

/**
 * @author zhuang
 */
@Table("t_room")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Room extends BaseEntity<String> {
    @Serial
    private static final long serialVersionUID = -2976498287888408577L;

    /**
     * 房间名
     */
    private String name;
    /**
     * 基础租金
     */
    private Integer rent;
    /**
     * 出租状态
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private RoomStatus status;
}
