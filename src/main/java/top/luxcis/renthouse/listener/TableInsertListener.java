package top.luxcis.renthouse.listener;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.annotation.InsertListener;
import top.luxcis.renthouse.model.BaseEntity;

import java.util.Date;

/**
 * @author zhuang
 */
public class TableInsertListener implements InsertListener {
    @Override
    public void onInsert(Object obj) {
        if (obj instanceof BaseEntity<?> entity) {
            String id = StpUtil.isLogin() ? StpUtil.getLoginIdAsString() : null;
            Date now = new Date();
            entity.setCreateTime(now);
            entity.setCreateBy(id);
            entity.setUpdateTime(now);
            entity.setUpdateBy(id);
        }
    }
}
