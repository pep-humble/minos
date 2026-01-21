package com.pep.base.function;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

/**
 * 映射表收集器
 *
 * @param <T> 元素类型
 * @param <K> 键类型
 * @param <U> 值类型
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class MapCollector<T, K, U> implements Collector<T, Map<K, U>, Map<K, U>> {

    /**
     * 特征
     */
    private static final Set<Characteristics> CHARACTERISTICS = Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));

    /**
     * 键映射器
     */
    private Function<T, K> keyMapper;

    /**
     * 值映射器
     */
    private Function<T, U> valueMapper;

    /**
     * 初始大小
     */
    private int initialCapacity = 16;

    /**
     * 加载因子
     */
    private float loadFactor = 0.8F;

    /**
     * 构造器
     */
    private NonnullBiFunction<Integer, Float, Map<K, U>> constructor = HashMap::new;

    /**
     * 生产者
     */
    private Supplier<Map<K, U>> supplier = () -> this.constructor.apply(this.initialCapacity, this.loadFactor);

    /**
     * 合并方法
     */
    private BinaryOperator<U> mergeFunction = (u, v) -> {
        throw new IllegalStateException(String.format("Duplicate key %s", u));
    };

    /**
     * 合并器
     */
    private BinaryOperator<Map<K, U>> combiner = (map1, map2) -> {
        for (Map.Entry<K, U> entry : map2.entrySet()) {
            map1.merge(entry.getKey(), entry.getValue(), this.mergeFunction);
        }
        return map1;
    };

    /**
     * 叠加器
     */
    private BiConsumer<Map<K, U>, T> accumulator = (map, element) -> map.merge(this.keyMapper.apply(element), this.valueMapper.apply(element), this.mergeFunction);

    /**
     * 最终处理器
     */
    private UnaryOperator<Map<K, U>> finisher = item -> item;

    /**
     * 构造函数
     *
     * @param keyMapper   键映射器
     * @param valueMapper 值映射器
     */
    public MapCollector(Function<T, K> keyMapper, Function<T, U> valueMapper) {
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }

    /**
     * 构造函数
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     * @param mergeFunction   合并方法
     * @param keyMapper       键映射器
     * @param valueMapper     值映射器
     */
    public MapCollector(int initialCapacity, float loadFactor, BinaryOperator<U> mergeFunction, Function<T, K> keyMapper, Function<T, U> valueMapper) {
        this(keyMapper, valueMapper);
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.mergeFunction = mergeFunction;
    }

    /**
     * 构造函数
     *
     * @param size        元素个数
     * @param keyMapper   键映射器
     * @param valueMapper 值映射器
     */
    public MapCollector(int size, Function<T, K> keyMapper, Function<T, U> valueMapper) {
        this(keyMapper, valueMapper);
        this.setSize(size);
    }

    /**
     * 构造函数
     *
     * @param size          元素个数
     * @param mergeFunction 合并方法
     * @param keyMapper     键映射器
     * @param valueMapper   值映射器
     */
    public MapCollector(int size, BinaryOperator<U> mergeFunction, Function<T, K> keyMapper, Function<T, U> valueMapper) {
        this(size, keyMapper, valueMapper);
        this.mergeFunction = mergeFunction;
    }

    /**
     * 设置元素个数
     *
     * @param size 元素个数
     */
    public void setSize(int size) {
        this.initialCapacity = size;
    }

    @Override
    public Supplier<Map<K, U>> supplier() {
        return this.supplier;
    }

    @Override
    public BiConsumer<Map<K, U>, T> accumulator() {
        return this.accumulator;
    }

    @Override
    public BinaryOperator<Map<K, U>> combiner() {
        return this.combiner;
    }

    @Override
    public Function<Map<K, U>, Map<K, U>> finisher() {
        return this.finisher;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return CHARACTERISTICS;
    }
}
