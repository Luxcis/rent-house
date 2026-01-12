package top.luxcis.renthouse.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.engine.spel.SpELEngine;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.luxcis.renthouse.anno.ApiLog;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.LogService;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * @author zhuang
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnWebApplication
public class ApiLogAspect {
    private final SpELEngine spelEngine = new SpELEngine();
    private final LogService logService;

    @Pointcut("@annotation(top.luxcis.renthouse.anno.ApiLog)")
    public void apiLog() {
    }

    @AfterReturning(value = "apiLog()", returning = "ret")
    public void afterReturning(JoinPoint joinPoint, Object ret) {
        boolean result = true;
        // 返回值如果为ResponseEntity单独处理获取body
        if (ret instanceof ResponseEntity<?> response) {
            ret = response.getBody();
        }
        if (ret instanceof Resp<?> resp) {
            result = resp.isSuccess();
        }
        this.log(joinPoint, result, JSONUtil.toJsonStr(ret), null);
    }

    @AfterThrowing(value = "apiLog()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        this.log(joinPoint, false, null, exception.getMessage());
    }

    private void log(JoinPoint joinPoint, boolean logResult, String apiResult, String exception) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ApiLog annotation = method.getAnnotation(ApiLog.class);

            Object[] args = joinPoint.getArgs();
            Parameter[] parameters = method.getParameters();
            Map<String, Object> context = MapUtil.newHashMap();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                context.put(parameter.getName(), args[i]);
            }

            String businessId = annotation.businessId();
            if (StrUtil.isNotBlank(annotation.businessId())) {
                try {
                    businessId = Convert.toStr(spelEngine.eval(businessId, context, Collections.emptyList()));
                } catch (Exception e) {
                    log.warn("解析业务ID失败:{}", e.getMessage());
                }
            }

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String uri = null;
            String requestMethod = null;
            String userId = StpUtil.isLogin() ? StpUtil.getLoginIdAsString() : null;

            Object[] apiParameters = context.entrySet()
                    .stream()
                    .map(entry -> {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        return StrUtil.format("{}={}", key, JSONUtil.toJsonStr(value));
                    }).toArray();

            String ipAddr = null;
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                uri = request.getRequestURI();
                requestMethod = request.getMethod();
                ipAddr = JakartaServletUtil.getClientIP(request);
            }

            logService.logApi(
                    userId,
                    StrUtil.blankToDefault(businessId, null),
                    annotation.type(),
                    logResult,
                    requestMethod,
                    uri,
                    StrUtil.subWithLength(Arrays.toString(apiParameters), 0, 1500),
                    StrUtil.isBlank(apiResult) ? null : StrUtil.subWithLength(apiResult, 0, 1500),
                    ipAddr,
                    StrUtil.blankToDefault(exception, null)
            );
        } catch (Exception e) {
            log.error("记录API日志失败", e);
        }
    }
}
