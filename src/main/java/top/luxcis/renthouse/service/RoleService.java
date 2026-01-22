package top.luxcis.renthouse.service;

import com.mybatisflex.core.service.IService;
import top.luxcis.renthouse.entity.Role;

/**
 * @author zhuang
 */
public interface RoleService extends IService<Role> {
    /**
     * 绑定角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void bindRole(String userId, String roleId);

    /**
     * 角色code转角色id
     *
     * @param code 代码
     * @return {@link String }
     */
    String code2Id(String code);
}
