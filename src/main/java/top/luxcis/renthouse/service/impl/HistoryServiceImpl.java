package top.luxcis.renthouse.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.luxcis.renthouse.entity.History;
import top.luxcis.renthouse.mapper.HistoryMapper;
import top.luxcis.renthouse.service.HistoryService;

/**
 * @author zhuang
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
}
