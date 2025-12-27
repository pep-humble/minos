package com.pep.minos.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import com.pep.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Properties;

/**
 * 系统设置项
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "System_Setting", autoResultMap = true)
public class SystemSetting extends BaseEntity {

    /**
     * 配置项唯一名称
     */
    @TableField(value = "`Key`")
    private String key;

    /**
     * 配置项描述
     */
    @TableField(value = "`Desc`")
    private String desc;

    /**
     * 此配置项是否开启
     */
    @TableField(value = "Enabled")
    private boolean enabled;

    /**
     * 详细配置信息
     */
    @TableField(value = "Properties", typeHandler = Fastjson2TypeHandler.class)
    private Properties properties;
}
