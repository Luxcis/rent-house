package top.luxcis.renthouse.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.luxcis.renthouse.anno.ApiLog;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.BillService;
import top.luxcis.renthouse.vo.response.IncomeVo;
import top.luxcis.renthouse.vo.response.IndexVo;
import top.luxcis.renthouse.vo.response.NotificationVo;
import top.luxcis.renthouse.vo.response.StatisticsVo;

import java.util.List;

/**
 * @author zhuang
 */
@Validated
@RestController
@Tag(name = "主页接口")
@RequestMapping("index")
@RequiredArgsConstructor
@SaCheckRole(value = {"admin", "manager"}, mode = SaMode.OR)
public class IndexController {
    private final BillService billService;

    @GetMapping
    @ApiLog(type = LogType.QUERY)
    @Operation(summary = "统计信息", description = "统计信息", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<IndexVo> index() {
        StatisticsVo statistics = billService.statistics();
        List<NotificationVo> notification = billService.notification();
        List<IncomeVo> income = billService.income();
        return Resp.SUCCESS(new IndexVo(statistics, notification, income));
    }
}
