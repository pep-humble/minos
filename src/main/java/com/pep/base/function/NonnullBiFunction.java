package com.pep.base.function;

import org.springframework.lang.NonNull;

import java.util.function.BiFunction;

/**
 * 参数和返回值都不为空的双参数方法
 *
 * @param <A1> 参数1类型
 * @param <A2> 参数2类型
 * @param <R>  返回值类型
 * @author gang.liu
 */
@FunctionalInterface
public interface NonnullBiFunction<A1, A2, R> extends BiFunction<A1, A2, R> {

    /**
     * 执行方法
     *
     * @param arg1 参数1
     * @param arg2 参数2
     * @return 返回值
     */
    @NonNull
    @Override
    R apply(@NonNull A1 arg1, @NonNull A2 arg2);
}
