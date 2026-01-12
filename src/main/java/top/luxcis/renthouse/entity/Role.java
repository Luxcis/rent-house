package top.luxcis.renthouse.entity;

import com.mybatisflex.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.luxcis.renthouse.model.BaseEntity;

import java.io.Serial;

/**
 * @author zhuang
 */
@Table("t_role")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Role extends BaseEntity<String> {
    @Serial
    private static final long serialVersionUID = 3512466396553819353L;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
}
