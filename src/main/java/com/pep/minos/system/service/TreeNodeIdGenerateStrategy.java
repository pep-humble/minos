package com.pep.minos.system.service;

import com.pep.minos.system.exception.TreeNodeIdIllegalException;

import java.util.List;

/**
 * 树形编号生成策略
 *
 * @author liu.gang
 */
public interface TreeNodeIdGenerateStrategy {

    /**
     * 该树形编号是否能继续新增下级
     *
     * @param treeId 树形编号
     * @throws TreeNodeIdIllegalException 不合法异常
     */
    void ifPostChildTreeNode(String treeId) throws TreeNodeIdIllegalException;

    /**
     * 判断树形编号格式是否合法
     *
     * @param treeId         树形编号
     * @param throwException 是否抛异常
     * @return 判断结果
     * @throws TreeNodeIdIllegalException 不合法异常
     */
    boolean validateTreeIdFormat(String treeId, boolean throwException) throws TreeNodeIdIllegalException;

    /**
     * 依赖上级编号生成下一个树形编号
     * <p>
     * 例如: parentId为0001, 已经存在子级编号00010000和00010001, 则生成的nextTreeId为00010002
     *
     * @param parentId       上级编号
     * @param maxChildTreeId 下级最大编号
     * @return 下一个树形顺序编号
     */
    String nextTreeId(String parentId, String maxChildTreeId);

    /**
     * 根据编号推断深度
     *
     * @param treeId 树形编号
     * @return 深度
     */
    int inferDepth(String treeId);

    /**
     * 推断上级编号
     *
     * @param treeId 当前树形编号
     * @return 上级编号
     */
    String inferParentId(String treeId);

    /**
     * 推断全部上级编号
     *
     * @param treeId 当前树形编号
     * @return 全部上级编号集合
     */
    List<String> inferAllParentId(String treeId);

    /**
     * 数值类型转成树形编号
     *
     * @param number 数值类型
     * @return 树形编号
     */
    String numberToTreeId(long number);

    /**
     * 树形编号转成数值类型,便于+1操作
     *
     * @param treeId 树形编号
     * @return 数值类型
     */
    long treeIdToNumber(String treeId);

}
