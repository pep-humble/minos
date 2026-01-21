package com.pep.minos.system.service;

import com.pep.minos.system.form.rbac.MenuCreateForm;
import com.pep.minos.system.form.rbac.MenuModifyForm;
import com.pep.minos.system.form.rbac.MenuTreeChildrenSearchForm;
import com.pep.minos.system.form.rbac.MenuTreeIdsForm;
import com.pep.minos.system.form.rbac.MenuTreeSearchForm;
import com.pep.minos.system.vo.rbac.MenuTreeVo;
import com.pep.minos.system.vo.rbac.MenuVo;

import java.util.List;
import java.util.Set;

/**
 * 菜单服务
 *
 * @author liu.gang
 */
public interface MenuService {

    /**
     * 创建菜单
     *
     * @param createForm 创建表单
     */
    String createMenu(MenuCreateForm createForm);

    /**
     * 修改菜单
     *
     * @param modifyForm 修改表单
     */
    String modifyMenu(MenuModifyForm modifyForm);

    /**
     * 根据编号删除菜单
     *
     * @param ids 编号集合
     */
    void deleteMune(Set<String> ids);

    /**
     * 移动菜单
     *
     * @param id             菜单编号
     * @param targetParentId 转移到的目标父级编号
     */
    void index(String id, String targetParentId);

    /**
     * 根据上级编号查询子级列表
     *
     * @param searchForm 菜单树形子级查询表单
     * @return 子节点集合
     */
    List<MenuTreeVo> tree(MenuTreeChildrenSearchForm searchForm);

    /**
     * 依赖查询条件, 查询出符合结果的整棵树
     *
     * @param searchForm 菜单树形查询表单
     * @return 节点集合
     */

    List<MenuTreeVo> search(MenuTreeSearchForm searchForm);

    /**
     * 批量查询节点
     *
     * @param form 表单
     * @return 节点集合
     */
    List<MenuVo> list(MenuTreeIdsForm form);

    /**
     * 整树查询, 适用于小数据量的全量查询
     *
     * @return 菜单整树
     */
    List<MenuTreeVo> completeTree();
}
