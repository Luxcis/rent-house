package top.luxcis.renthouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.RelationManyToMany;
import com.mybatisflex.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.luxcis.renthouse.model.BaseEntity;

import java.io.Serial;
import java.util.List;

/**
 * @author zhuang
 */
@Table("t_user")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class User extends BaseEntity<String> {
    @Serial
    private static final long serialVersionUID = -3714378318851335061L;

    /**
     * 昵称
     */
    private String name;
    /**
     * 用户名
     */
    @JsonIgnore
    private String username;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 微信unionid
     */
    @JsonIgnore
    private String unionid;
    /**
     * 微信openid
     */
    @JsonIgnore
    private String openid;
    /**
     * 是否启用
     */
    private Boolean active;

    @RelationManyToMany(
            joinTable = "t_user_role_ref",
            selfField = "id", joinSelfColumn = "user_id",
            targetField = "id", joinTargetColumn = "role_id",
            targetTable = "t_role", valueField = "code"
    )
    private List<String> roles;
}
