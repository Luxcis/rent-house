package top.luxcis.renthouse.config;

import cn.dev33.satoken.fun.strategy.SaCorsHandleFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaFirewallStrategy;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
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
        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin()))
                .addPathPatterns("/**");
    }

    @PostConstruct
    public void saTokenPostConstruct() {
        // 指定防火墙校验不通过时的处理方案
        SaFirewallStrategy.instance.checkFailHandle = (e, req, res, handler) -> {
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

    /**
     * CORS 跨域处理策略
     */
    @Bean
    public SaCorsHandleFunction corsHandle() {
        return (req, res, sto) -> {
            // 获得客户端domain
            String origin = req.getHeader("Origin");
            if (origin == null) {
                origin = req.getHeader("Referer");
            }
            res
                    // 允许指定域访问跨域资源
                    .setHeader("Access-Control-Allow-Origin", origin)
                    // 允许所有请求方式
                    .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS")
                    // 有效时间
                    .setHeader("Access-Control-Max-Age", "3600")
                    // 允许的header参数
                    .setHeader("Access-Control-Allow-Headers", "*");

            // 如果是预检请求，则立即返回到前端
            SaRouter.match(SaHttpMethod.OPTIONS)
                    // .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
                    .back();
        };
    }
}
