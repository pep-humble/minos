package com.pep.minos.system.form.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 菜单树形查询表单
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "MenuSearchForm 菜单树形查询表单", description = "菜单树形查询表单")
public class MenuTreeSearchForm {

    /**
     * 菜单名称 模糊查询
     */
    @Schema(description = "菜单名称 模糊查询")
    private String label;

    /**
     * 菜单类型
     */
    @Schema(description = "菜单类型")
    private String menuType;

    /**
     * 菜单状态
     */
    @Schema(description = "菜单状态")
    private boolean status;

    /**
     * 上级节点编号
     */
    @Schema(description = "上级节点编号")
    private String parentId;

    /**
     * ids
     */
    @Size(max = 500)
    @Schema(description = "节点编号集合")
    private Set<@NotBlank String> ids;

    /**
     * 是否平铺
     */
    @Schema(description = "是否平铺，默认false")
    private Boolean tiled;

    /**
     * 是否包含下级子节点
     */
    @Schema(description = "是否包含下级子节点, 默认false")
    private Boolean containChildren;

    /**
     * 是否加载旁系节点
     */
    @Schema(description = "是否加载旁系节点, 默认true")
    private Boolean loadCollateral;

}
