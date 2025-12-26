package com.pep.base.function;

/**
 * 三参数方法
 * <p>
 * Ter是Ternary的缩写
 *
 * @param <A1> 参数1类型
 * @param <A2> 参数2类型
 * @param <A3> 参数3类型
 * @param <R>  返回值类型
 * @author gang.liu
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface TerFunction<A1, A2, A3, R> {

    /**
     * 执行方法
     *
     * @param arg1 参数1
     * @param arg2 参数2
     * @param arg3 参数3
     * @return 返回值
     */
    R apply(A1 arg1, A2 arg2, A3 arg3);
}
