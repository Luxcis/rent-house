package top.luxcis.renthouse.service.impl;

import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.luxcis.renthouse.entity.Role;
import top.luxcis.renthouse.mapper.RoleMapper;
import top.luxcis.renthouse.service.RoleService;

/**
 * @author zhuang
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public void bindRole(String userId, String roleId) {
        Row row = new Row();
        row.set("user_id", userId);
        row.set("role_id", roleId);
        Db.insert("t_user_role_ref", row);
    }
}
