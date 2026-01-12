package top.luxcis.renthouse.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.luxcis.renthouse.entity.Room;
import top.luxcis.renthouse.enums.RoomStatus;
import top.luxcis.renthouse.mapper.RoomMapper;
import top.luxcis.renthouse.service.RoomService;
import top.luxcis.renthouse.vo.request.RoomSaveVo;

/**
 * @author zhuang
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Override
    public void save(RoomSaveVo vo) {
        Room room = BeanUtil.copyProperties(vo, Room.class);
        this.saveOrUpdate(room);
    }

    @Override
    public Room updateStatus(String id, RoomStatus status) {
        Room room = this.getById(id);
        room.setStatus(status);
        this.updateById(room);
        return room;
    }
}
