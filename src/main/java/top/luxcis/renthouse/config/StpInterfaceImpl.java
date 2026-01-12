package top.luxcis.renthouse.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import top.luxcis.renthouse.service.UserService;

import java.util.List;

/**
 * @author zhuang
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {
    private final UserService userService;

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return StpUtil.getSession().get("roles", () -> userService.getMapper().selectOneWithRelationsById(StpUtil.getLoginIdAsLong()).getRoles());
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return List.of();
    }
}
