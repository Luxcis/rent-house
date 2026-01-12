package top.luxcis.renthouse.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zhuang
 */
@Getter
@Setter
@Validated
public class SignVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2902984333547243933L;
    /**
     * 随机字符串
     */
    @Size(min = 5, max = 32)
    private String nonce;
    /**
     * 时间戳 1767667002247
     */
    @NotNull
    private Long timestamp;
    /**
     * 签名
     */
    @NotBlank
    private String signature;
}
