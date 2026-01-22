package top.luxcis.renthouse.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
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
import top.luxcis.renthouse.entity.Log;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.LogService;

import java.util.List;

/**
 * @author zhuang
 */
@Validated
@RestController
@SaCheckRole("admin")
@Tag(name = "日志接口")
@RequestMapping("log")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping("list")
    @ApiLog(type = LogType.QUERY)
    @Operation(summary = "日志列表", description = "日志列表", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<List<Log>> list(String next) {
        return Resp.SUCCESS(logService.list(next));
    }
}
