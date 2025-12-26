package com.pep.minos.system.form.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 系统设置项创建表单
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "SettingModifyForm 系统设置项创建表单", description = "系统设置项创建表单")
public class SettingModifyForm extends SettingCreateForm {

    /**
     * 设置项唯一编码
     */
    @NotEmpty
    @Schema(name = "id", description = "设置项唯一编码")
    private String id;

}
