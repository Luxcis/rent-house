package top.luxcis.renthouse.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.luxcis.renthouse.anno.ApiLog;
import top.luxcis.renthouse.entity.Bill;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.BillService;
import top.luxcis.renthouse.utils.SignUtil;
import top.luxcis.renthouse.vo.request.BillPayVo;
import top.luxcis.renthouse.vo.request.BillSaveVo;

import java.util.List;

/**
 * @author zhuang
 */
@Validated
@RestController
@Tag(name = "账单接口")
@RequestMapping("bill")
@RequiredArgsConstructor
@SaCheckRole(value = {"admin", "manager"}, mode = SaMode.OR)
public class BillController {
    private final BillService billService;

    @GetMapping("list")
    @ApiLog(type = LogType.QUERY)
    @Operation(summary = "账单列表", description = "账单列表", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<List<Bill>> list(@NotBlank String roomId, String next) {
        return Resp.SUCCESS(billService.list(roomId, next));
    }

    @PostMapping("save")
    @ApiLog(type = LogType.SAVE)
    @Operation(summary = "创建账单", description = "创建账单", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Void> save(@RequestBody BillSaveVo vo) {
        SignUtil.checkSign(vo);
        billService.save(vo);
        return Resp.SUCCESS();
    }

    @PostMapping("pay")
    @ApiLog(type = LogType.EDIT)
    @Operation(summary = "支付状态", description = "支付状态", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Void> pay(@RequestBody BillPayVo vo) {
        billService.pay(vo.getId(), vo.getIsPaid());
        return Resp.SUCCESS();
    }
}
