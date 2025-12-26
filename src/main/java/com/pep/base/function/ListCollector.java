package com.pep.base.function;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

/**
 * List收集器
 *
 * @param <T> 元素类型
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class ListCollector<T> implements Collector<T, List<T>, List<T>> {

    /**
     * 特征
     */
    private static final Set<Characteristics> CHARACTERISTICS = Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));

    /**
     * 初始大小
     */
    private int initialCapacity = 10;

    /**
     * 构造器
     */
    private NonnullFunction<Integer, List<T>> constructor = ArrayList::new;

    /**
     * 生产者
     */
    private Supplier<List<T>> supplier = () -> this.constructor.apply(this.initialCapacity);

    /**
     * 叠加器
     */
    private BiConsumer<List<T>, T> accumulator = List::add;

    /**
     * 合并器
     */
    private BinaryOperator<List<T>> combiner = (left, right) -> {
        left.addAll(right);
        return left;
    };

    /**
     * 最终处理器
     */
    private UnaryOperator<List<T>> finisher = item -> item;

    /**
     * 构造函数
     */
    public ListCollector() {

    }

    /**
     * 构造函数
     *
     * @param initialCapacity 初始大小
     */
    public ListCollector(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    @Override
    public Supplier<List<T>> supplier() {
        return this.supplier;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return this.accumulator;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return this.combiner;
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return this.finisher;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return CHARACTERISTICS;
    }

    /**
     * 链表收集器
     *
     * @param <T> 元素类型
     * @return 链表收集器
     */
    public static <T> ListCollector<T> linkedList() {
        return new ListCollector<T>().setConstructor(size -> new LinkedList<>());
    }

    /**
     * 数组列表收集器
     *
     * @param <T>  元素类型
     * @param size 元素个数
     * @return 数组列表收集器
     */
    public static <T> ListCollector<T> arrayList(int size) {
        return new ListCollector<>(size);
    }
}