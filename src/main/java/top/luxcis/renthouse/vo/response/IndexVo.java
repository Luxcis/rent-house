package top.luxcis.renthouse.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhuang
 */
@Getter
@Setter
@AllArgsConstructor
public class IndexVo {
    private StatisticsVo statistics;
    private List<NotificationVo> notification;
    private List<IncomeVo> income;
}
