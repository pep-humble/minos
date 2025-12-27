package com.pep.minos.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pep.minos.system.entity.SystemSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统设置项 数据库操作
 *
 * @author liu.gang
 */
@Mapper
public interface SystemSettingMapper extends BaseMapper<SystemSetting> {
}
