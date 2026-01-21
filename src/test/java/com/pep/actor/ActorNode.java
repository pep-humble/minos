package com.pep.actor;

import java.util.Objects;

/**
 * actor节点
 *
 * @author liu.gang
 */
public abstract class ActorNode {

    /**
     * 下一个节点的信箱
     */
    private final ActorMailbox nextMailBox;

    protected ActorNode(ActorMailbox nextMailBox) {
        this.nextMailBox = nextMailBox;
    }

    /**
     * 执行方法
     *
     * @param message 消息
     */
    protected final void execute(String message) {
        doExecute(message);
        if (Objects.nonNull(nextMailBox)) {
            nextMailBox.enqueue(message);
        }
    }

    /**
     * 子类实现的执行方法
     *
     * @param message 消息
     */
    abstract void doExecute(String message);
}
