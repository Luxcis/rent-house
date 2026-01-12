package top.luxcis.renthouse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhuang
 */
@Getter
@Setter
public class BaseEntity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 8468980158037156759L;

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private ID id;
    @JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonIgnore
    private String createBy;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private String updateBy;
    @JsonIgnore
    @Column(isLogicDelete = true)
    private Boolean isRemoved;
}
