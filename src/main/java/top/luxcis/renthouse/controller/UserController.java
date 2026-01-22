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
import top.luxcis.renthouse.entity.User;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.UserService;
import top.luxcis.renthouse.utils.SignUtil;
import top.luxcis.renthouse.vo.request.UserRolesVo;
import top.luxcis.renthouse.vo.request.UserStatusVo;

import java.util.List;

/**
 * @author zhuang
 */
@Validated
@RestController
@SaCheckRole("admin")
@Tag(name = "用户接口")
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("list")
    @ApiLog(type = LogType.QUERY)
    @Operation(summary = "用户列表", description = "用户列表", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<List<User>> list() {
        return Resp.SUCCESS(userService.getMapper().selectAllWithRelations());
    }

    @PostMapping("status")
    @ApiLog(type = LogType.SAVE)
    @Operation(summary = "用户列表", description = "用户列表", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Void> status(@RequestBody UserStatusVo vo) {
        SignUtil.checkSign(vo);
        userService.status(vo.getId(), vo.getActive());
        return Resp.SUCCESS();
    }

    @PostMapping("bindRoles")
    @ApiLog(type = LogType.SAVE)
    @Operation(summary = "绑定角色", description = "绑定角色", method = "GET")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Void> bindRoles(@RequestBody UserRolesVo vo) {
        userService.bindRoles(vo.getId(), vo.getRoles());
        return Resp.SUCCESS();
    }
}
