package com.pep.actor.normal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 执行器节点
 *
 * @author liu.gang
 */
@Data
@RequiredArgsConstructor
public abstract class INormalNode {

    /**
     * 下一个执行器节点
     */
    protected final INormalNode nextNode;

    /**
     * 执行方法
     *
     * @param message 消息
     */
    protected final void execute(String message) {
        doExecute(message);
        if (Objects.nonNull(nextNode)) {
            nextNode.execute(message);
        }
    }

    /**
     * 子类实现的执行方法
     *
     * @param message 消息
     */
    abstract void doExecute(String message);
}
