package com.pep.base.function;

import org.springframework.lang.NonNull;

import java.util.function.Function;

/**
 * 参数和返回值都非空的方法
 *
 * @param <T> 参数类型
 * @param <R> 返回值类型
 * @author gang.liu
 */
@FunctionalInterface
public interface NonnullFunction<T, R> extends Function<T, R> {

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @return a unary operator that always returns its input argument
     */
    static <T> NonnullFunction<T, T> identity() {
        return item -> item;
    }

    /**
     * 执行方法
     *
     * @param arg 参数
     * @return 返回值
     */
    @NonNull
    @Override
    R apply(@NonNull T arg);
}
