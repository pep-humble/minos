package com.pep.minos.system.form.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 菜单编辑表单
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "MenuModifyForm 菜单编辑表单", description = "菜单编辑表单")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MenuModifyForm extends MenuCreateForm {

    /**
     * 节点编号
     */
    @NotBlank
    @Schema(description = "节点编号")
    private String id;

}
