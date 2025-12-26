package com.pep.minos.system.vo.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Properties;

/**
 * 设置项响应视图
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "SettingVo 设置项响应视图", description = "设置项响应视图")
public class SettingVo {

    /**
     * 设置项唯一编码
     */
    @Schema(description = "设置项唯一编码")
    private String id;

    /**
     * 设置项唯一名称
     */
    @Schema(description = "设置项唯一名称")
    private String key;

    /**
     * 设置项描述
     */
    @Schema(description = "设置项描述")
    private String desc;

    /**
     * 是否开启此设置
     */
    @Schema(description = "是否开启此设置")
    private boolean enabled;

    /**
     * 设置项值
     */
    @Schema(description = "设置项值")
    private Properties properties;
}
