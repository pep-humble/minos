package com.pep.minos.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pep.base.util.StringUtil;
import com.pep.minos.system.entity.SystemMenu;
import com.pep.minos.system.exception.TreeNodeIdIllegalException;
import com.pep.minos.system.form.rbac.MenuCreateForm;
import com.pep.minos.system.form.rbac.MenuModifyForm;
import com.pep.minos.system.form.rbac.MenuTreeChildrenSearchForm;
import com.pep.minos.system.form.rbac.MenuTreeIdsForm;
import com.pep.minos.system.form.rbac.MenuTreeSearchForm;
import com.pep.minos.system.mapper.SystemMenuMapper;
import com.pep.minos.system.service.MenuService;
import com.pep.minos.system.service.TreeCollector;
import com.pep.minos.system.service.TreeNodeIdGenerateStrategy;
import com.pep.minos.system.vo.rbac.MenuTreeVo;
import com.pep.minos.system.vo.rbac.MenuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 菜单服务
 *
 * @author liu.gang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService, InitializingBean {

    /**
     * 系统菜单 数据库操作
     */
    private final SystemMenuMapper systemMenuMapper;

    /**
     * 树形编号生成策略
     */
    private final TreeNodeIdGenerateStrategy treeIdGenerateStrategy;

    /**
     * 根节点
     */
    private SystemMenu rootMenu;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void afterPropertiesSet() {
        // 判断有没有根节点,没有的话则新增
        SystemMenu systemMenu = systemMenuMapper.selectOne(
                Wrappers.lambdaQuery(SystemMenu.class)
                        // 只有根节点为空串 才是根节点
                        .eq(SystemMenu::getTreeId, "")
                        .last("limit 1")
        );
        SystemMenu rootMenu;
        if (Objects.nonNull(systemMenu)) {
            rootMenu = systemMenu;
        } else {
            // 新增根节点
            rootMenu = new SystemMenu();
            rootMenu
                    .setTreeId("")
                    .setParentId("")
                    .setGroupId("system-menu")
                    .setDepth(0)
                    .setIsLeaf(true)
                    .setDeleted(false)
                    .setSort(0)
                    .setLabel("菜单根节点")
                    .setMenuType("")
                    .setUuid(UUID.randomUUID().toString());
            systemMenuMapper.insert(rootMenu);
        }
        this.rootMenu = rootMenu;
    }

    /**
     * 确保树形节点编号已经存在
     *
     * @param id 树形节点编号
     * @return 存在的树形结构
     * @throws TreeNodeIdIllegalException 可能抛出的异常
     */
    private SystemMenu ensureTreeNodeExistence(String id) throws TreeNodeIdIllegalException {
        return Optional
                .ofNullable(
                        this.systemMenuMapper.selectOne(
                                Wrappers.lambdaQuery(SystemMenu.class)
                                        .eq(SystemMenu::getUuid, id)
                        )
                )
                .orElseThrow(TreeNodeIdIllegalException::treeNodeNotExists);
    }

    /**
     * 确保树形结构深度不能过深
     *
     * @param parentId 上级编号
     * @throws TreeNodeIdIllegalException 可能抛出的异常
     */
    protected void ensureTreeNodeDepth(String parentId) throws TreeNodeIdIllegalException {
        treeIdGenerateStrategy.ifPostChildTreeNode(parentId);
    }

    /**
     * 确保同级下,树形节点唯一
     *
     * @param parentId 上级树形编号
     * @param treeId   树形编号
     * @param text     树形节点文本
     * @throws TreeNodeIdIllegalException 可能抛出的异常
     */
    protected void ensureTreeNodeTextUniqueness(String parentId, String treeId, String text) throws TreeNodeIdIllegalException {
        Long count = this.systemMenuMapper.selectCount(
                Wrappers.lambdaQuery(SystemMenu.class)
                        .eq(SystemMenu::getParentId, parentId)
                        .eq(SystemMenu::getLabel, text)
                        .ne(StringUtil.isFriendly(treeId), SystemMenu::getTreeId, treeId)
        );
        if (count > 0) {
            throw TreeNodeIdIllegalException.newTreeNodeIdIllegalException("same text exists within the same level. parentId:{},same text:{}", parentId, text);
        }
    }

    /**
     * 获取父级编号下最大的子级编号
     *
     * @param parentId 父级编号
     * @return 最大的子级节点
     */
    private SystemMenu maxChildTreeId(String parentId) {
        return this.systemMenuMapper.selectOne(
                Wrappers.lambdaQuery(SystemMenu.class)
                        .eq(SystemMenu::getParentId, parentId)
                        .orderBy(true, false, SystemMenu::getTreeId)
                        .last("limit 1")
        );
    }

    /**
     * 树形编号是否存在
     *
     * @param treeId 树形编号
     * @return 是否存在
     */
    private boolean existsTreeId(String treeId) {
        return this.systemMenuMapper.exists(
                Wrappers.lambdaQuery(SystemMenu.class)
                        .eq(SystemMenu::getTreeId, treeId)
                        .last("for update")
        );
    }

    protected SystemMenu convertTreeNodeEntity(String treeId,
                                               MenuCreateForm createForm,
                                               Consumer<SystemMenu> treeNodeEntityConsumer) {
        SystemMenu treeNodeEntity = new SystemMenu();
        treeNodeEntity
                .setTreeId(treeId)
                .setTitle(createForm.getTitle())
                .setLabel(createForm.getLabel())
                .setMenuType(createForm.getMenuType())
                .setSort(createForm.getSort())
                .setAddition(createForm.getAddition())
                .setLabel(createForm.getLabel())
                .setTitle(createForm.getTitle())
                .setMenuType(createForm.getMenuType())
                .setRouterPath(createForm.getRouterPath())
                .setComponentPath(createForm.getComponentPath())
                .setVisible(createForm.getVisible())
                .setStatus(createForm.getStatus())
                .setPerms(createForm.getPerms())
                .setIcon(createForm.getIcon())
                .setRemark(createForm.getRemark())
                .setCache(createForm.getCache())
                .setLinkPath(createForm.getLinkPath())
                .setLinkOpenType(createForm.getLinkOpenType())
                .setViewTab(createForm.getViewTab())
                .setQuery(createForm.getQuery())
        ;
        Optional
                .ofNullable(treeNodeEntityConsumer)
                .ifPresent(consumer -> consumer.accept(treeNodeEntity));
        return treeNodeEntity;
    }

    /**
     * 编辑树形节点
     *
     * @return 影响的行数
     */
    protected int updateTreeNodeEntity(SystemMenu systemMenu, Wrapper<SystemMenu> updateWrapper) {
        int updateCount = Optional.of(systemMenuMapper.update(systemMenu, updateWrapper)).orElse(0);
        if (updateCount > 0) {
            if (Boolean.TRUE.equals(systemMenu.getDeleted())) {
                // todo 如果删除项的父类只有这一个子级, 则修改删除项父类的leaf为true

            }
        }
        return updateCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createMenu(MenuCreateForm createForm) {
        SystemMenu parentMenu;
        if (StringUtil.isUnfriendly(createForm.getParentId()) && Objects.nonNull(rootMenu)) {
            parentMenu = rootMenu;
        } else {
            parentMenu = ensureTreeNodeExistence(createForm.getParentId());
        }
        this.ensureTreeNodeDepth(parentMenu.getTreeId());
        String parentMenuUuid = parentMenu.getUuid();
        this.ensureTreeNodeTextUniqueness(parentMenuUuid, null, createForm.getLabel());
        // 自旋获取下一个树形节点编号
        String nextTreeId;
        SystemMenu maxChildTreeNode;
        do {
            maxChildTreeNode = this.maxChildTreeId(parentMenuUuid);
            String maxChildTreeId = Optional
                    .ofNullable(maxChildTreeNode)
                    .map(SystemMenu::getTreeId)
                    .orElse("");
            // 获取编号
            nextTreeId = treeIdGenerateStrategy.nextTreeId(parentMenu.getTreeId(), maxChildTreeId);
            // 检查编号是否存在, 存在时则继续获取, 直到获取的编号不存在
        } while (this.existsTreeId(nextTreeId));
        // 获取深度
        int depth = treeIdGenerateStrategy.inferDepth(nextTreeId);
        // 获取序号
        Integer index = Optional.ofNullable(maxChildTreeNode)
                .map(SystemMenu::getSort)
                .map(sort -> Integer.sum(sort, 1))
                .orElse(0);
        SystemMenu treeNodeEntity = convertTreeNodeEntity(
                nextTreeId,
                createForm,
                nodeEntity ->
                        nodeEntity
                                .setParentId(parentMenuUuid)
                                .setGroupId(parentMenu.getGroupId())
                                .setDepth(depth)
                                .setSort(Optional.ofNullable(nodeEntity.getSort()).orElse(index))
                                .setIsLeaf(true)
                                .setDeleted(false)
                                .setUuid(UUID.randomUUID().toString())
        );
        // 新增子级节点
        int rows = this.systemMenuMapper.insert(treeNodeEntity);
        if (rows > 0) {
            // 更新父节点是否叶子节点的标识为false
            SystemMenu updateWrapper = new SystemMenu();
            updateWrapper.setUuid(parentMenuUuid);
            this.updateTreeNodeEntity(new SystemMenu().setIsLeaf(false), Wrappers.lambdaQuery(updateWrapper));
        }
        return treeNodeEntity.getUuid();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String modifyMenu(MenuModifyForm modifyForm) {
        SystemMenu parentMenu;
        if (StringUtil.isUnfriendly(modifyForm.getParentId()) && Objects.nonNull(rootMenu)) {
            parentMenu = rootMenu;
        } else {
            parentMenu = ensureTreeNodeExistence(modifyForm.getParentId());
        }
        SystemMenu modifyMenu = ensureTreeNodeExistence(modifyForm.getId());
        String parentMenuUuid = parentMenu.getUuid();
        this.ensureTreeNodeTextUniqueness(parentMenuUuid, modifyMenu.getTreeId(), modifyForm.getLabel());
        this.ensureTreeNodeDepth(parentMenu.getTreeId());
        String currentParentId = modifyMenu.getParentId();
        String modifyMenuUuid = modifyMenu.getUuid();
        if (Strings.CS.equals(currentParentId, parentMenuUuid)) {
            // 节点未发生移动 更新树形节点基本信息
            SystemMenu modify = convertTreeNodeEntity(
                    modifyMenu.getTreeId(),
                    modifyForm,
                    nodeEntity ->
                            nodeEntity
                                    .setParentId(parentMenuUuid)
                                    .setGroupId(parentMenu.getGroupId())
                                    .setDepth(modifyMenu.getDepth())
                                    .setIsLeaf(modifyMenu.getIsLeaf())
                                    .setDeleted(modifyMenu.getDeleted())
                                    .setUuid(modifyMenuUuid)
            );
            this.updateTreeNodeEntity(modify, Wrappers.lambdaQuery(SystemMenu.class).eq(SystemMenu::getUuid, modifyMenuUuid));
        } else {
            // 发生移动

        }
        return modifyMenuUuid;
    }

    @Override
    public void deleteMune(Set<String> ids) {

    }

    @Override
    public void index(String id, String targetParentId) {

    }

    @Override
    public List<MenuTreeVo> tree(MenuTreeChildrenSearchForm searchForm) {
        return List.of();
    }

    @Override
    public List<MenuTreeVo> search(MenuTreeSearchForm searchForm) {
        return List.of();
    }

    @Override
    public List<MenuVo> list(MenuTreeIdsForm form) {
        return List.of();
    }

    @Override
    public List<MenuTreeVo> completeTree() {
        List<SystemMenu> systemMenus = systemMenuMapper.selectList(
                Wrappers.lambdaQuery(SystemMenu.class)
                        .orderByAsc(SystemMenu::getSort)
                        .ne(SystemMenu::getTreeId, "")
        );
        return new TreeCollector<SystemMenu, MenuTreeVo, String, String>()
                .setSourceIdMapper(SystemMenu::getUuid)
                .setTargetIdMapper(MenuTreeVo::getId)
                .setSourceParentIdMapper(SystemMenu::getParentId)
                .setSourceTargetIdMapper(SystemMenu::getUuid)
                .setBatchConverter(sources ->
                        sources
                                .stream()
                                .map(systemMenu -> {
                                            MenuTreeVo menuTreeVo = new MenuTreeVo();
                                            menuTreeVo
                                                    .setId(systemMenu.getUuid())
                                                    .setParentId(systemMenu.getParentId())
                                                    .setTitle(systemMenu.getLabel());
                                            return menuTreeVo;
                                        }
                                )
                                .collect(Collectors.toCollection(ArrayList::new))
                )
                .setChildrenConsumer(MenuTreeVo::setChildren)
                .collect(systemMenus);
    }


}
