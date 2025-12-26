package com.pep.minos.system.form.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Properties;

/**
 * 系统设置项创建表单
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "SettingCreateForm 系统设置项创建表单", description = "系统设置项创建表单")
public class SettingCreateForm {

    /**
     * 设置项唯一名称
     */
    @NotEmpty
    @Schema(name = "key", description = "设置项唯一名称")
    private String key;

    /**
     * 设置项描述
     */
    @NotEmpty
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
