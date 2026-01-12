package top.luxcis.renthouse.vo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhuang
 */
@Getter
@Setter
@Validated
public class WechatLoginVo {
    @NotBlank
    private String code;
    private String name;
}
