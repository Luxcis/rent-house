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
@Table("t_history")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class History extends BaseEntity<String> {
    @Serial
    private static final long serialVersionUID = 5404126661096002336L;
    /**
     * 实体名
     */
    private String beanName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 数据id
     */
    private Long businessId;
    /**
     * 旧值
     */
    private String oldValue;
    /**
     * 新值
     */
    private String newValue;
}
