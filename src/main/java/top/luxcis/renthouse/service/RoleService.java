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
}
