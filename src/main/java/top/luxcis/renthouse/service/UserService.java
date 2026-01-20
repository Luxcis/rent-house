package top.luxcis.renthouse.service;

import com.mybatisflex.core.service.IService;
import top.luxcis.renthouse.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author zhuang
 */
public interface UserService extends IService<User> {
    /**
     * 通过微信小程序code登录
     *
     * @param code 登陆参数
     * @return {@link String token}
     */
    String loginByWechatMPCode(String code);

    /**
     * 根据unionid和openid查询用户
     *
     * @param unionid unionid
     * @param openid  openid
     * @return {@link Optional<User> }
     */
    Optional<User> queryByOpenid(String unionid, String openid);

    /**
     * 通过微信id创建用户
     *
     * @param unionid unionid
     * @param openId  openId
     * @return {@link User }
     */
    User createUserByOpenid(String unionid, String openId);

    /**
     * 通过密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link String token}
     */
    String loginByPwd(String username, String password);

    /**
     * 状态修改
     *
     * @param id     ID
     * @param status 状态
     */
    void status(String id, boolean status);

    /**
     * 绑定角色
     *
     * @param id    ID
     * @param roles 角色
     */
    void bindRoles(String id, List<String> roles);

    /**
     * 完善用户信息
     *
     * @param id   ID
     * @param name 名字
     */
    void doComplete(String id, String name);
}
