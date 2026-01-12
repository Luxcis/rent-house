package top.luxcis.renthouse.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author zhuang
 */
@Getter
@Setter
@Validated
public class UserRolesVo {
    @NotBlank
    private String id;
    @NotNull
    private List<String> roles;
}
