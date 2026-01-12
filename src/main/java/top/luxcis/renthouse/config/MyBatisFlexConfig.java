package top.luxcis.renthouse.config;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.UpdateListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.AuditMessage;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import top.luxcis.renthouse.listener.TableInsertListener;
import top.luxcis.renthouse.listener.TableUpdateListener;

import java.util.Set;

/**
 * @author zhuang
 */
@Slf4j
@Configuration
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {
    @Override
    public void customize(FlexGlobalConfig config) {
        InsertListener insertListener = new TableInsertListener();
        UpdateListener updateListener = new TableUpdateListener();

        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("top.luxcis.renthouse.entity", Table.class);
        config.registerInsertListener(insertListener, classes.toArray(new Class<?>[0]));
        config.registerUpdateListener(updateListener, classes.toArray(new Class<?>[0]));

        // 开启审计功能
        AuditManager.setAuditEnable(true);
        // 设置 SQL 审计收集器
        AuditManager.setMessageCollector(this::log);
    }

    @SuppressWarnings("preview")
    private void log(AuditMessage auditMessage) {
        String[] ignoreTable = {"t_log", "t_history"};
        String sql = auditMessage.getFullSql();
        boolean ignore = StrUtil.containsAnyIgnoreCase(sql, ignoreTable);
        if (!ignore) {
            String msg = STR."""
                ************************************************
                * sql:    \{sql}
                * cost:   \{auditMessage.getElapsedTime()} ms
                ************************************************
                """;
            log.info(msg);
        }
    }
}
