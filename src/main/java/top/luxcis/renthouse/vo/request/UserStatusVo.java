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
public class UserStatusVo extends SignVo {
    @Serial
    private static final long serialVersionUID = -4031860600606975960L;
    @NotBlank
    private String id;
    @NotNull
    private Boolean active;
}
