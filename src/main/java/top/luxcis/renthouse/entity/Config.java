package top.luxcis.renthouse.entity;

import cn.hutool.core.util.NumberUtil;
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
@Table("t_config")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Config extends BaseEntity<String> {
    @Serial
    private static final long serialVersionUID = 5334591107711218718L;
    /**
     * 配置名称
     */
    private String name;
    /**
     * 配置编码
     */
    private String code;
    /**
     * 配置内容
     */
    private String value;

    public Double toDouble() {
        return NumberUtil.parseDouble(value);
    }

    public Integer toInteger() {
        return NumberUtil.parseInt(value);
    }
}
