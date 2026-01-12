package top.luxcis.renthouse.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.luxcis.renthouse.entity.Log;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.mapper.LogMapper;
import top.luxcis.renthouse.service.LogService;

import java.util.Date;

/**
 * @author zhuang
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Async
    @Override
    public void logApi(String userId, String businessId, LogType type, boolean logResult, String method, String uri, String parameters, String result, String ipAddr, String exception) {
        Log log = new Log();
        log.setBusinessId(businessId);
        log.setType(type);
        log.setIsSuccess(logResult);
        log.setApi(uri);
        log.setMethod(method);
        log.setParameters(parameters);
        log.setResult(result);
        log.setIp(ipAddr);
        log.setException(exception);
        log.setLogTime(new Date());
        log.setUserId(userId);
        this.save(log);
    }
}
