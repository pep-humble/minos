package com.pep.minos.system.form.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单树形子级查询表单, 根据上级编号查询子级列表
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "MenuTreeChildrenSearchForm 菜单树形子级查询表单, 根据上级编号查询子级列表", description = "菜单树形子级查询表单, 根据上级编号查询子级列表")
public class MenuTreeChildrenSearchForm {

    /**
     * 父节点编号
     */
    @NotBlank
    @Schema(description = "父节点编号")
    private String parentId;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enabled;

    /**
     * 是否返回父节点
     */
    @Schema(description = "是否返回父节点")
    private Boolean containParent = false;

    /**
     * 是否返回所有下级节点
     */
    @Schema(description = "是否返回所有下级节点")
    private Boolean allChildren;

}
