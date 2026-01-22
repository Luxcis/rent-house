package top.luxcis.renthouse.service.impl;

import cn.hutool.core.lang.Assert;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.luxcis.renthouse.entity.Role;
import top.luxcis.renthouse.enums.ExceptionEnum;
import top.luxcis.renthouse.exception.BusinessException;
import top.luxcis.renthouse.mapper.RoleMapper;
import top.luxcis.renthouse.service.RoleService;

import java.util.List;

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

    @Override
    @Cacheable(value = "roleCode2Id#12h", key = "#code")
    public String code2Id(String code) {
        List<Role> roles = this.queryChain().eq(Role::getCode, code).list();
        Assert.isTrue(roles.size() == 1, () -> new BusinessException(ExceptionEnum.DATA_ERROR));
        return roles.getFirst().getId();
    }
}
