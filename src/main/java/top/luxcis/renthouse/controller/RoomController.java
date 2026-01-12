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
import org.springframework.web.bind.annotation.*;
import top.luxcis.renthouse.anno.ApiLog;
import top.luxcis.renthouse.entity.Room;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.RoomService;
import top.luxcis.renthouse.utils.SignUtil;
import top.luxcis.renthouse.vo.request.RoomSaveVo;

import java.util.List;

/**
 * @author zhuang
 */
@Validated
@RestController
@Tag(name = "房间接口")
@RequestMapping("room")
@RequiredArgsConstructor
@SaCheckRole(value = {"admin", "manager"}, mode = SaMode.OR)
public class RoomController {
    private final RoomService roomService;

    @GetMapping("list")
    @ApiLog(type = LogType.QUERY)
    @Operation(summary = "房间列表", description = "房间列表", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<List<Room>> list() {
        return Resp.SUCCESS(roomService.list());
    }

    @PostMapping("save")
    @ApiLog(type = LogType.SAVE)
    @Operation(summary = "保存房间", description = "保存房间", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Void> save(@RequestBody RoomSaveVo vo) {
        SignUtil.checkSign(vo);
        roomService.save(vo);
        return Resp.SUCCESS();
    }

    @GetMapping("{id}")
    @ApiLog(type = LogType.QUERY, businessId = "#id")
    @Operation(summary = "房间信息", description = "房间信息", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Room> info(@PathVariable String id) {
        return Resp.SUCCESS(roomService.getById(id));
    }
}
