package top.luxcis.renthouse.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import top.luxcis.renthouse.vo.SignVo;

import java.io.Serial;

/**
 * @author zhuang
 */
@Getter
@Setter
@Validated
public class BillPayVo extends SignVo {
    @Serial
    private static final long serialVersionUID = -7359579509988755148L;

    @NotBlank
    private String id;
    @NotNull
    private Boolean isPaid;
}
