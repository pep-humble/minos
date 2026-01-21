package com.pep.minos.system.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 树形编号不合法异常
 *
 * @author liu.gang
 */
public class TreeNodeIdIllegalException extends IllegalArgumentException {

    /**
     * 构造器
     *
     * @param message 异常信息
     */
    private TreeNodeIdIllegalException(String message) {
        super(message);
    }

    public static TreeNodeIdIllegalException treeNodeNotExists() {
        return new TreeNodeIdIllegalException("tree node not exists");
    }

    public static TreeNodeIdIllegalException exceededMaximumDepth() {
        return new TreeNodeIdIllegalException("exceeded maximum depth, cannot continue creating new tree nodes");
    }

    public static TreeNodeIdIllegalException exceededMaximumLength() {
        return new TreeNodeIdIllegalException("exceeded maximum length, cannot continue creating new tree nodes");
    }

    public static TreeNodeIdIllegalException newTreeNodeIdIllegalException(String message) {
        return new TreeNodeIdIllegalException(message);
    }

    public static TreeNodeIdIllegalException newTreeNodeIdIllegalException(String message, Object... args) {
        // 仿造slf4j的日志
        FormattingTuple format = MessageFormatter.arrayFormat(message, args);
        return new TreeNodeIdIllegalException(format.getMessage());
    }
}
