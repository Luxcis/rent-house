package top.luxcis.renthouse.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.luxcis.renthouse.anno.ApiLog;
import top.luxcis.renthouse.entity.User;
import top.luxcis.renthouse.enums.LogType;
import top.luxcis.renthouse.model.Resp;
import top.luxcis.renthouse.service.UserService;
import top.luxcis.renthouse.vo.request.WechatLoginVo;

/**
 * @author zhuang
 */
@Validated
@RestController
@Tag(name = "用户认证")
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @SaIgnore
    @PostMapping("doLogin/wechat")
    @ApiLog(type = LogType.LOGIN)
    @Operation(summary = "微信小程序登陆", description = "微信小程序登陆", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<String> doLogin(@RequestBody @NotNull WechatLoginVo vo) {
        return Resp.SUCCESS(userService.loginByWechatMPCode(vo));
    }

    @SaIgnore
    @PostMapping("doLogin")
    @ApiLog(type = LogType.LOGIN)
    @Operation(summary = "密码登陆", description = "密码登陆", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<String> doLogin(@NotBlank String username, @NotBlank String password) {
        return Resp.SUCCESS(userService.loginByPwd(username, password));
    }

    @SaIgnore
    @PostMapping("ping")
    @ApiLog(type = LogType.LOGIN)
    @Operation(summary = "检查登陆状态", description = "检查登陆状态", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Boolean> ping() {
        return Resp.SUCCESS(StpUtil.isLogin());
    }

    @PostMapping("user_info")
    @ApiLog(type = LogType.QUERY)
    @Operation(summary = "当前用户信息", description = "当前用户信息", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<User> userInfo() {
        return Resp.SUCCESS(userService.getMapper().selectOneWithRelationsById(StpUtil.getLoginIdAsLong()));
    }

    @PostMapping("doLogout")
    @ApiLog(type = LogType.LOGOUT)
    @Operation(summary = "退出登陆", description = "退出登陆", method = "POST")
    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Resp.class)))
    public Resp<Void> doLogout() {
        StpUtil.logout();
        return Resp.SUCCESS();
    }
}
