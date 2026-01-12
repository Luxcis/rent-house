package top.luxcis.renthouse.listener;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.annotation.UpdateListener;
import top.luxcis.renthouse.model.BaseEntity;

import java.util.Date;

/**
 * @author zhuang
 */
public class TableUpdateListener implements UpdateListener {
    @Override
    public void onUpdate(Object obj) {
        if (obj instanceof BaseEntity<?> entity) {
            String id = StpUtil.isLogin() ? StpUtil.getLoginIdAsString() : null;
            Date now = new Date();
            entity.setUpdateTime(now);
            entity.setUpdateBy(id);
        }
    }
}
