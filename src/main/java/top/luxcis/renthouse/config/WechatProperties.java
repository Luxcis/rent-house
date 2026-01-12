package top.luxcis.renthouse.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhuang
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {
    private String appid;
    private String secret;
}
