package top.luxcis.renthouse.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author zhuang
 */
@Slf4j
@Configuration
@EnableAsync
public class VirtualThreadAsyncConfig implements AsyncConfigurer {

    /**
     * 虚拟线程异步执行器
     * Spring Boot 3.2+ 会自动创建虚拟线程执行器，这里提供手动配置示例
     */
    @Bean(name = "virtualThreadExecutor")
    @ConditionalOnProperty(name = "spring.threads.virtual.enabled", havingValue = "true")
    public Executor virtualThreadExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    /**
     * 默认异步执行器
     * 如果启用虚拟线程，Spring Boot会自动使用虚拟线程执行器
     */
    @Override
    public Executor getAsyncExecutor() {
        // Spring Boot 3.2+ 会自动使用虚拟线程执行器（如果启用）
        // 如果需要手动指定，可以返回 virtualThreadExecutor()
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
