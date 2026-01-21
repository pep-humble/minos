package com.pep.minos.system.controller;

import com.pep.minos.system.form.rbac.MenuCreateForm;
import com.pep.minos.system.form.rbac.MenuModifyForm;
import com.pep.minos.system.form.rbac.MenuTreeChildrenSearchForm;
import com.pep.minos.system.form.rbac.MenuTreeIdsForm;
import com.pep.minos.system.form.rbac.MenuTreeIndexForm;
import com.pep.minos.system.form.rbac.MenuTreeSearchForm;
import com.pep.minos.system.service.MenuService;
import com.pep.minos.system.vo.rbac.MenuTreeVo;
import com.pep.minos.system.vo.rbac.MenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 系统设置 菜单管理
 *
 * @author liu.gang
 */
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
@Tag(name = "系统设置-菜单管理")
public class MenuController {

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 创建菜单
     *
     * @param createForm 创建表单
     */
    @PostMapping
    @Operation(summary = "创建菜单")
    public String createMenu(@RequestBody @Valid MenuCreateForm createForm) {
        return menuService.createMenu(createForm);
    }

    /**
     * 修改菜单
     *
     * @param modifyForm 修改表单
     */
    @Operation(summary = "修改菜单")
    @PutMapping
    public String modifyMenu(@RequestBody @Valid MenuModifyForm modifyForm) {
        return menuService.modifyMenu(modifyForm);
    }

    /**
     * 根据编号删除菜单
     *
     * @param form 编号集合
     */
    @Operation(summary = "根据编号删除菜单")
    @DeleteMapping
    public void deleteMune(@RequestBody @Valid MenuTreeIdsForm form) {
        if (CollectionUtils.isNotEmpty(form.getIds())) {
            menuService.deleteMune(form.getIds());
        }
    }

    /**
     * 移动菜单
     *
     * @param indexForm 菜单移动表单
     */
    @Operation(summary = "移动菜单")
    @PatchMapping("/index")
    public void index(@RequestBody @Valid MenuTreeIndexForm indexForm) {
        menuService.index(indexForm.getId(), indexForm.getTargetParentId());
    }

    /**
     * 根据上级编号查询子级列表
     *
     * @param searchForm 菜单树形子级查询表单
     * @return 子节点集合
     *
     */
    @GetMapping("/tree")
    @Operation(summary = "根据上级编号查询子级列表")
    public List<MenuTreeVo> tree(@RequestBody @Valid MenuTreeChildrenSearchForm searchForm) {
        return menuService.tree(searchForm);
    }

    /**
     * 依赖查询条件, 查询出符合结果的整棵树
     *
     * @param searchForm 菜单树形查询表单
     * @return 节点集合
     */
    @GetMapping("/search")
    @Operation(summary = "依赖查询条件, 查询出符合结果的整棵树")
    public List<MenuTreeVo> search(@RequestBody @Valid MenuTreeSearchForm searchForm) {
        return menuService.search(searchForm);
    }

    /**
     * 查询单个节点详细信息
     *
     * @param id 单个编号
     * @return 单个节点详细信息
     */
    @Operation(summary = "查询单个节点详细信息", description = "查询单个节点详细信息")
    @GetMapping("/{id}")
    public MenuVo first(@PathVariable String id) {
        List<MenuVo> list = menuService.list(new MenuTreeIdsForm().setIds(Collections.singleton(id)));
        if (CollectionUtils.isNotEmpty(list)) {
            return list.iterator().next();
        }
        return null;
    }

    /**
     * 批量查询节点
     *
     * @param form 表单
     * @return 节点集合
     */
    @Operation(summary = "批量查询节点", description = "批量查询节点")
    @GetMapping("/list")
    public List<MenuVo> list(@RequestBody @Valid MenuTreeIdsForm form) {
        if (CollectionUtils.isNotEmpty(form.getIds())) {
            return menuService.list(form);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 整树查询, 适用于小数据量的全量查询
     *
     * @return 菜单整树
     */
    @GetMapping("/complete-tree")
    @Operation(summary = "整树查询, 适用于小数据量的全量查询", description = "整树查询, 适用于小数据量的全量查询")
    public List<MenuTreeVo> completeTree() {
        return menuService.completeTree();
    }
}
