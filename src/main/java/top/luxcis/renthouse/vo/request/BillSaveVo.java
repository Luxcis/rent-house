package top.luxcis.renthouse.vo.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import top.luxcis.renthouse.enums.BillStatus;
import top.luxcis.renthouse.vo.SignVo;

import java.io.Serial;

/**
 * @author zhuang
 */
@Getter
@Setter
@Validated
public class BillSaveVo extends SignVo {
    @Serial
    private static final long serialVersionUID = -8361511946931072429L;
    /**
     * 房间ID
     */
    @NotBlank
    private String roomId;
    /**
     * 水表读数
     */
    @NotNull
    @Min(0)
    private Double water;
    /**
     * 电表读数
     */
    @NotNull
    @Min(0)
    private Double electric;
    /**
     * 额外费用
     */
    @Min(0)
    private Double extPrice;
    /**
     * 额外费用备注
     */
    private String extRemark;
    /**
     * 状态
     */
    private BillStatus status;
}
