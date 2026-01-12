package top.luxcis.renthouse.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.luxcis.renthouse.anno.ApiLog;
import top.luxcis.renthouse.entity.Config;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.ConfigService;
import top.luxcis.renthouse.utils.SignUtil;
import top.luxcis.renthouse.vo.request.ConfigUpdateVo;

import java.util.List;

/**
 * @author zhuang
 */
@Validated
@RestController
@SaCheckRole("admin")
@Tag(name = "用户接口")
@RequestMapping("config")
@RequiredArgsConstructor
public class ConfigController {
    private final ConfigService configService;

    @GetMapping("list")
    @ApiLog(type = LogType.QUERY)
    @Operation(summary = "配置列表", description = "配置列表", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<List<Config>> list() {
        return Resp.SUCCESS(configService.list());
    }

    @PostMapping("update")
    @ApiLog(type = LogType.SAVE)
    @Operation(summary = "配置列表", description = "配置列表", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Void> update(@RequestBody ConfigUpdateVo vo) {
        SignUtil.checkSign(vo);
        configService.updateConfig(vo.getCode(), vo.getValue());
        return Resp.SUCCESS();
    }
}
