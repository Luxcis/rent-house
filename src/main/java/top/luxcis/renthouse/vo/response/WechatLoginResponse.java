package top.luxcis.renthouse.vo.response;

import cn.hutool.core.annotation.Alias;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuang
 */
@Getter
@Setter
public class WechatLoginResponse {
    private String openid;
    private String unionid;
    @Alias("session_key")
    private String sessionKey;
    private Integer errcode = 0;
    private String errmsg;
}
