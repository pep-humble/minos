package com.pep.base.function;

import org.springframework.lang.NonNull;

/**
 * 三参数断言器
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
public interface TerPredicate<A1, A2, A3> {

    /**
     * 测试
     *
     * @param arg1 参数1
     * @param arg2 参数2
     * @param arg3 参数3
     * @return 是否测试成功
     */
    boolean test(A1 arg1, A2 arg2, A3 arg3);

    /**
     * 永真
     *
     * @param <A1> 第一个参数的类型
     * @param <A2> 第二个参数的类型
     * @param <A3> 第三个参数的类型
     * @return 断言器
     */
    @NonNull
    static <A1, A2, A3> TerPredicate<A1, A2, A3> yes() {
        return (arg1, arg2, arg3) -> true;
    }

    /**
     * 永假
     *
     * @param <A1> 第一个参数的类型
     * @param <A2> 第二个参数的类型
     * @param <A3> 第三个参数的类型
     * @return 断言器
     */
    @NonNull
    static <A1, A2, A3> TerPredicate<A1, A2, A3> no() {
        return (arg1, arg2, arg3) -> false;
    }
}
