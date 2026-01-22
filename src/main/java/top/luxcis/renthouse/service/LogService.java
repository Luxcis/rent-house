package top.luxcis.renthouse.service;

import com.mybatisflex.core.service.IService;
import top.luxcis.renthouse.entity.Log;
import top.luxcis.renthouse.enums.LogType;

import java.util.List;

/**
 * @author zhuang
 */
public interface LogService extends IService<Log> {
    void logApi(String userId, String businessId, LogType type, boolean logResult, String method, String uri, String parameters, String result, String ipAddr, String exception);

    List<Log> list(String next);
}
