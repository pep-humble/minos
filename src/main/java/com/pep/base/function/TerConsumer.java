package com.pep.base.function;

/**
 * 三参数消费者
 * <p>
 * Ter是Ternary的缩写
 *
 * @param <A1> 参数1类型
 * @param <A2> 参数2类型
 * @param <A3> 参数3类型
 * @author gang.liu
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface TerConsumer<A1, A2, A3> {

    /**
     * 消费参数
     *
     * @param arg1 参数1
     * @param arg2 参数2
     * @param arg3 参数3
     */
    void accept(A1 arg1, A2 arg2, A3 arg3);
}
