package com.pep.minos.system.form.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单移动表单
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "MenuTreeIndexForm 菜单移动表单", description = "菜单移动表单")
public class MenuTreeIndexForm {

    /**
     * 菜单编号
     */
    @NotBlank
    @Schema(description = "菜单编号")
    private String id;

    /**
     * 转移到的目标父级编号
     */
    @NotBlank
    @Schema(description = "转移到的目标父级编号")
    private String targetParentId;

}
