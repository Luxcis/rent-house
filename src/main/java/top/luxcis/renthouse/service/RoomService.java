package top.luxcis.renthouse.service;

import com.mybatisflex.core.service.IService;
import top.luxcis.renthouse.entity.Room;
import top.luxcis.renthouse.enums.RoomStatus;
import top.luxcis.renthouse.vo.request.RoomSaveVo;

/**
 * @author zhuang
 */
public interface RoomService extends IService<Room> {
    /**
     * 保存
     *
     * @param vo vo
     */
    void save(RoomSaveVo vo);

    /**
     * 更新状态
     *
     * @param id     ID
     * @param status 状态
     */
    Room updateStatus(String id, RoomStatus status);
}
