package top.luxcis.renthouse.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import top.luxcis.renthouse.enums.RoomStatus;
import top.luxcis.renthouse.vo.SignVo;

import java.io.Serial;

/**
 * @author zhuang
 */
@Getter
@Setter
@Validated
public class RoomSaveVo extends SignVo {
    @Serial
    private static final long serialVersionUID = -4170203222984839626L;
    private String id;
    /**
     * 房间名
     */
    @NotBlank
    private String name;
    /**
     * 基础租金
     */
    @NotNull
    private Integer rent;
    /**
     * 水表
     */
    @NotBlank
    private String water;
    /**
     * 电表
     */
    @NotBlank
    private String electric;
    /**
     * 出租状态
     */
    @NotNull
    private RoomStatus status;
}
