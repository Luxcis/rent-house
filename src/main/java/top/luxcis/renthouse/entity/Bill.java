package top.luxcis.renthouse.entity;

import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mybatisflex.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.luxcis.renthouse.enums.BillStatus;
import top.luxcis.renthouse.model.BaseEntity;
import top.luxcis.renthouse.serial.EnumJsonSerializer;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author zhuang
 */
@Table("t_bill")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Bill extends BaseEntity<String> {
    @Serial
    private static final long serialVersionUID = -7762031782859981543L;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 基础租金
     */
    private Integer rent;
    /**
     * 水表读数
     */
    private Double water;
    /**
     * 用水量
     */
    private Double waterUsage;
    /**
     * 水费单价
     */
    private Double waterPrice;
    /**
     * 水费
     */
    private Double waterFee;
    /**
     * 电表读数
     */
    private Double electric;
    /**
     * 用电量
     */
    private Double electricUsage;
    /**
     * 电费单价
     */
    private Double electricPrice;
    /**
     * 电费
     */
    private Double electricFee;
    /**
     * 管理费
     */
    private Integer managementPrice;
    /**
     * 垃圾费
     */
    private Integer garbagePrice;
    /**
     * 额外费用
     */
    private Integer extPrice;
    /**
     * 额外费用备注
     */
    private String extRemark;
    /**
     * 合计租金
     */
    private Double sumPrice;
    /**
     * 状态
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private BillStatus status;
    /**
     * 是否已支付
     */
    private Boolean isPaid;

    public void calc() {
        // 用水量 x 水单价
        this.waterFee = NumberUtil.mul(this.waterPrice, this.waterUsage);
        // 用电量 x 电单价
        this.electricFee = NumberUtil.mul(this.electricPrice, this.electricUsage);
        // 基础租金 + 水费 + 电费 + 管理费 + 垃圾费 + 额外费用
        BigDecimal sum = NumberUtil.add(this.rent, this.waterFee, this.electricFee, this.managementPrice, this.garbagePrice, this.extPrice);
        this.sumPrice = NumberUtil.toDouble(sum);
    }
}
