package top.luxcis.renthouse.vo.request;

import jakarta.validation.constraints.NotBlank;
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
public class ConfigUpdateVo extends SignVo {
    @Serial
    private static final long serialVersionUID = 5079921276787937498L;
    @NotBlank
    private String code;
    @NotBlank
    private Double value;
}
