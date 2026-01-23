-- 基础角色
INSERT OR IGNORE INTO t_role (id, name, code, create_time, create_by, update_time,
                              update_by, is_removed)
VALUES (1, '管理员', 'admin', '1767494266867', null, '1767494266867', null, 0);
INSERT OR IGNORE INTO t_role (id, name, code, create_time, create_by, update_time,
                              update_by, is_removed)
VALUES (2, '房东', 'manager', '1767494266867', null, '1767494266867', null, 0);

-- 基础配置
insert OR IGNORE into t_config(id, name, code, value, create_time, create_by, update_time, update_by,
                               is_removed)
VALUES (1, '电费单价', 'electric_unit_price', '1.5', '1767494266867', null, '1767494266867', null, 0);
insert OR IGNORE into t_config(id, name, code, value, create_time, create_by, update_time, update_by,
                               is_removed)
VALUES (2, '水费单价', 'water_unit_price', '1.5', '1767494266867', null, '1767494266867', null, 0);
insert OR IGNORE into t_config(id, name, code, value, create_time, create_by, update_time, update_by,
                               is_removed)
VALUES (3, '管理费', 'management_price', '20', '1767494266867', null, '1767494266867', null, 0);
insert OR IGNORE into t_config(id, name, code, value, create_time, create_by, update_time, update_by,
                               is_removed)
VALUES (4, '垃圾清理费', 'garbage_price', '10', '1767494266867', null, '1767494266867', null, 0);