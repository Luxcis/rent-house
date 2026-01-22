package top.luxcis.renthouse.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zhuang
 */
@Getter
@Setter
@AllArgsConstructor
public class IncomeVo {
    private String room;
    private Double price;
    @JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd")
    private Date date;
}
