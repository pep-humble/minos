package com.pep.minos.system.vo.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 菜单树形响应视图
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "MenuTreeVo 菜单树形响应视图", description = "菜单树形响应视图")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MenuTreeVo extends MenuVo {

    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    private List<MenuTreeVo> children;
}
