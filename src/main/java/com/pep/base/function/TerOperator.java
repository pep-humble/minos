/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.pep.base.function;

/**
 * 三参数操作器
 * <p>
 * Ter是Ternary的缩写
 *
 * @param <T> 数据类型
 * @author gang.liu
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface TerOperator<T> extends TerFunction<T, T, T, T> {

}
