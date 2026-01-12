package top.luxcis.renthouse;

import cn.hutool.core.util.NumberUtil;
import org.junit.jupiter.api.Test;

/**
 * @author zhuang
 */
public class CommonTest {
    @Test
    public void number() {
        String num = "100";
        String num2 = "100.5";
        String num3 = "100.4567";
        System.out.println(NumberUtil.decimalFormat("0.###", NumberUtil.parseNumber(num, 0)));
        System.out.println(NumberUtil.decimalFormat("0.###", NumberUtil.parseNumber(num2, 0)));
        System.out.println(NumberUtil.decimalFormat("0.###", NumberUtil.parseNumber(num3, 0)));
    }
}
