package com.pep.minos.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pep.minos.system.entity.SystemMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统菜单 数据库操作
 *
 * @author liu.gang
 */
@Mapper
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {
}
