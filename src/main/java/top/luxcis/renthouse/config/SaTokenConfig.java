package top.luxcis.renthouse.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaFirewallStrategy;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.luxcis.renthouse.enums.RespEnum;
import top.luxcis.renthouse.model.Resp;

import java.io.PrintWriter;

/**
 * @author zhuang
 */
@Slf4j
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(_ -> StpUtil.checkLogin()))
                .addPathPatterns("/**");
    }

    @PostConstruct
    public void saTokenPostConstruct() {
        // 指定防火墙校验不通过时的处理方案
        SaFirewallStrategy.instance.checkFailHandle = (e, _, res, _) -> {
            log.error("防火墙拦截：{}", e.getMessage());
            try {
                HttpServletResponse response = (HttpServletResponse) res.getSource();
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.print(Resp.ERROR(RespEnum.FORBIDDEN, e.getMessage()));
                writer.flush();
            } catch (Exception ignored) {
            }
        };
    }
}
