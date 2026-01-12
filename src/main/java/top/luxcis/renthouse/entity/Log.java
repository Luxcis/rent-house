package top.luxcis.renthouse.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.luxcis.renthouse.enums.LogType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhuang
 */
@Table("t_log")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Log implements Serializable {
    @Serial
    private static final long serialVersionUID = 8551548723524711347L;
    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    /**
     * 接口类型
     */
    private LogType type;
    /**
     * 请求结果
     */
    private Boolean isSuccess;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 接口地址
     */
    private String api;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 请求参数
     */
    private String parameters;
    /**
     * 请求返回值
     */
    private String result;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 访问ip
     */
    private String ip;
    /**
     * 日志时间
     */
    private Date logTime;
}
