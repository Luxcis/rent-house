package top.luxcis.renthouse.service;

import com.mybatisflex.core.service.IService;
import top.luxcis.renthouse.entity.Bill;
import top.luxcis.renthouse.vo.request.BillSaveVo;
import top.luxcis.renthouse.vo.response.IncomeVo;
import top.luxcis.renthouse.vo.response.NotificationVo;
import top.luxcis.renthouse.vo.response.StatisticsVo;

import java.util.List;
import java.util.Optional;

/**
 * @author zhuang
 */
public interface BillService extends IService<Bill> {
    /**
     * 账单列表
     *
     * @param roomId 房间ID
     * @param next   分页值
     * @return {@link List }<{@link Bill }>
     */
    List<Bill> list(String roomId, String next);

    /**
     * 创建账单
     *
     * @param vo vo
     */
    void save(BillSaveVo vo);

    /**
     * 最后一次账单
     *
     * @param roomId 房间id
     * @return {@link Optional }<{@link Bill }>
     */
    Optional<Bill> lastBill(String roomId);

    /**
     * 支付
     *
     * @param id     ID
     * @param isPaid 已支付
     */
    void pay(String id, Boolean isPaid);

    /**
     * 统计
     *
     * @return {@link StatisticsVo }
     */
    StatisticsVo statistics();

    /**
     * 收租通知
     *
     * @return {@link List }<{@link NotificationVo }>
     */
    List<NotificationVo> notification();

    /**
     * 最近入账
     *
     * @return {@link List }<{@link IncomeVo }>
     */
    List<IncomeVo> income();
}
