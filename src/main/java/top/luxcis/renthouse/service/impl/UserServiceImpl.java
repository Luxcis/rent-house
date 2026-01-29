package top.luxcis.renthouse.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.luxcis.renthouse.config.WechatProperties;
import top.luxcis.renthouse.entity.User;
import top.luxcis.renthouse.enums.ExceptionEnum;
import top.luxcis.renthouse.exception.BusinessException;
import top.luxcis.renthouse.mapper.UserMapper;
import top.luxcis.renthouse.service.RoleService;
import top.luxcis.renthouse.service.UserService;
import top.luxcis.renthouse.utils.PasswordUtil;
import top.luxcis.renthouse.vo.response.WechatLoginResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static top.luxcis.renthouse.entity.def.UserDef.USER;

/**
 * @author zhuang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String WECHAT_SESSION_KEY = "session_key";
    private final WechatProperties properties;
    private final RoleService roleService;

    @Override
    public String loginByWechatMPCode(String code) {
        Map<String, Object> params = Map.of("appid", properties.getAppid(), "secret", properties.getSecret(), "js_code", code, "grant_type", "authorization_code");
        String res = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", params);
        WechatLoginResponse result = JSONUtil.toBean(res, WechatLoginResponse.class);
        if (result.getErrcode() == 0) {
            String openId = result.getOpenid();
            String unionId = result.getUnionid();
            String sessionKey = result.getSessionKey();
            User user = this.queryByOpenid(unionId, openId)
                    .orElseGet(() -> this.createUserByOpenid(unionId, openId));
            Assert.isTrue(user.getActive(), () -> new BusinessException(ExceptionEnum.USER_NOTE_ACTIVE));
            StpUtil.login(user.getId(), "wechat");
            StpUtil.getSession().set(WECHAT_SESSION_KEY, sessionKey);
            return StpUtil.getTokenValue();
        } else {
            throw new BusinessException(ExceptionEnum.WECHAT_API_ERROR, result.getErrcode().toString(), result.getErrmsg());
        }
    }

    @Override
    public Optional<User> queryByOpenid(String unionid, String openId) {
        QueryWrapper query = QueryWrapper.create()
                .select()
                .where(USER.OPENID.eq(openId))
                .and(USER.UNIONID.eq(unionid, StrUtil.isNotBlank(unionid)));
        List<User> list = this.list(query);
        if (list.size() > 1) {
            throw new BusinessException(ExceptionEnum.DATA_ERROR);
        }
        return list.stream().findFirst();
    }

    @Override
    public User createUserByOpenid(String unionid, String openId) {
        User user = new User();
        user.setUnionid(unionid);
        user.setOpenid(openId);
        user.setActive(true);
        this.save(user);
        long count = this.count();
        if (count == 1) {
            // 第一个用户绑定管理员
            roleService.bindRole(user.getId(), "1");
        }
        return user;
    }

    @Override
    public String loginByPwd(String username, String password) {
        QueryWrapper query = QueryWrapper.create()
                .select()
                .where(USER.USERNAME.eq(username));
        List<User> list = this.list(query);
        if (list.size() > 1) {
            throw new BusinessException(ExceptionEnum.DATA_ERROR);
        }
        return list.stream()
                .findFirst()
                .filter(u -> PasswordUtil.match(password, u.getPassword()))
                .map(u -> {
                    Assert.isTrue(u.getActive(), () -> new BusinessException(ExceptionEnum.USER_NOTE_ACTIVE));
                    StpUtil.login(u.getId(), "main");
                    return StpUtil.getTokenValue();
                })
                .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_LOGIN_FAIL));
    }

    @Override
    public void status(String id, boolean status) {
        User user = this.getById(id);
        user.setActive(status);
        this.updateById(user);
    }

    @Override
    public void bindRoles(String id, List<String> roles) {
        Db.deleteByQuery("t_user_role_ref", QueryWrapper.create().where("user_id = ?", id));
        List<Row> rows = roles.stream().map(roleService::code2Id).map(role -> new Row().set("user_id", id).set("role_id", role)).toList();
        Optional.of(rows).filter(CollUtil::isNotEmpty).ifPresent((data) -> Db.insertBatch("t_user_role_ref", data));
        StpUtil.kickout(id);
    }

    @Override
    public void doComplete(String id, String name) {
        this.updateChain()
                .set(USER.NAME, name)
                .where(USER.ID.eq(id))
                .update();
    }
}
