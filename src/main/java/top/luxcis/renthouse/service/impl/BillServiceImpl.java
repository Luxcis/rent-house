package top.luxcis.renthouse.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.luxcis.renthouse.entity.Bill;
import top.luxcis.renthouse.entity.Room;
import top.luxcis.renthouse.enums.BillStatus;
import top.luxcis.renthouse.enums.ConfigEnum;
import top.luxcis.renthouse.enums.ExceptionEnum;
import top.luxcis.renthouse.enums.RoomStatus;
import top.luxcis.renthouse.exception.BusinessException;
import top.luxcis.renthouse.mapper.BillMapper;
import top.luxcis.renthouse.service.BillService;
import top.luxcis.renthouse.service.ConfigService;
import top.luxcis.renthouse.service.RoomService;
import top.luxcis.renthouse.vo.request.BillSaveVo;
import top.luxcis.renthouse.vo.response.IncomeVo;
import top.luxcis.renthouse.vo.response.NotificationVo;
import top.luxcis.renthouse.vo.response.StatisticsVo;

import java.util.List;
import java.util.Optional;

import static top.luxcis.renthouse.entity.def.BillDef.BILL;
import static top.luxcis.renthouse.entity.def.RoomDef.ROOM;

/**
 * @author zhuang
 */
@Service
@RequiredArgsConstructor
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {
    private final RoomService roomService;
    private final ConfigService configService;

    @Override
    public List<Bill> list(String roomId, String next) {
        return this.queryChain()
                .eq(Bill::getRoomId, roomId, StrUtil.isNotBlank(roomId))
                .lt(Bill::getId, next, StrUtil.isNotBlank(next))
                .orderBy(Bill::getCreateTime, false)
                .limit(5)
                .list();
    }

    @Override
    @Transactional
    public void save(BillSaveVo vo) {
        Room room = roomService.updateStatus(vo.getRoomId(), RoomStatus.parse(vo.getStatus()));
        Optional<Bill> lastOpt = this.lastBill(vo.getRoomId());
        Bill bill = BeanUtil.copyProperties(vo, Bill.class).setRent(room.getRent());
        Assert.isTrue(vo.getWater() >= lastOpt.map(Bill::getWater).orElse(0.0), () -> new BusinessException(ExceptionEnum.BILL_WATER_ERROR));
        Assert.isTrue(vo.getElectric() >= lastOpt.map(Bill::getElectric).orElse(0.0), () -> new BusinessException(ExceptionEnum.BILL_WATER_ERROR));
        if (vo.getStatus() != BillStatus.CHECKED_IN) {
            Bill last = lastOpt.orElseThrow(() -> new BusinessException(ExceptionEnum.DATA_ERROR));
            bill.setWaterUsage(NumberUtil.sub(vo.getWater(), last.getWater()))
                    .setWaterPrice(configService.getDoubleByEnum(ConfigEnum.WATER_UNIT_PRICE))
                    .setElectricUsage(NumberUtil.sub(vo.getElectric(), last.getElectric()))
                    .setElectricPrice(configService.getDoubleByEnum(ConfigEnum.ELECTRIC_UNIT_PRICE))
                    .setManagementPrice(configService.getIntegerByEnum(ConfigEnum.MANAGEMENT_PRICE))
                    .setGarbagePrice(configService.getIntegerByEnum(ConfigEnum.GARBAGE_PRICE))
                    .calc();
        }
        this.save(bill);
    }

    @Override
    public Optional<Bill> lastBill(String roomId) {
        return this.queryChain()
                .eq(Bill::getRoomId, roomId)
                .orderBy(Bill::getCreateTime, false)
                .oneOpt();
    }

    @Override
    public void pay(String id, Boolean isPaid) {
        this.updateChain()
                .eq(Bill::getId, id)
                .set(Bill::getIsPaid, isPaid)
                .update();
    }

    @Override
    public StatisticsVo statistics() {
        QueryWrapper emptyQuery = QueryWrapper.create()
                .eq(Room::getStatus, RoomStatus.FOR_RENT);
        long empty = roomService.count(emptyQuery);
        QueryWrapper sumQuery = QueryWrapper.create()
                .select(QueryMethods.sum(Bill::getSumPrice).as("total"))
                .ne(Bill::getStatus, BillStatus.CHECKED_IN)
                .eq(Bill::getIsPaid, true)
                .ge(Bill::getCreateTime, DateUtil.lastMonth().toJdkDate());
        Double total = this.getObjAs(sumQuery, Double.class);
        return new StatisticsVo(empty, total);
    }

    @Override
    public List<NotificationVo> notification() {
        return this.queryChain()
                .select(ROOM.NAME.as(NotificationVo::getRoom), BILL.SUM_PRICE.as(NotificationVo::getPrice), BILL.CREATE_TIME.as(NotificationVo::getDate))
                .from(BILL)
                .leftJoin(ROOM)
                .on(BILL.ROOM_ID.eq(ROOM.ID))
                .where(BILL.STATUS.ne(BillStatus.CHECKED_IN))
                .and(BILL.IS_PAID.eq(false).or(BILL.IS_PAID.isNull()))
                .orderBy(BILL.CREATE_TIME.desc())
                .listAs(NotificationVo.class);
    }

    @Override
    public List<IncomeVo> income() {
        return this.queryChain()
                .select(ROOM.NAME.as(IncomeVo::getRoom), BILL.SUM_PRICE.as(IncomeVo::getPrice), BILL.UPDATE_TIME.as(IncomeVo::getDate))
                .from(BILL)
                .leftJoin(ROOM)
                .on(BILL.ROOM_ID.eq(ROOM.ID))
                .where(BILL.STATUS.ne(BillStatus.CHECKED_IN))
                .and(BILL.IS_PAID.eq(true))
                .orderBy(BILL.CREATE_TIME.desc())
                .limit(5)
                .listAs(IncomeVo.class);
    }
}
