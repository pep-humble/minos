package com.pep.minos.system.service;

import com.pep.base.function.ListCollector;
import com.pep.base.function.MapCollector;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 树收集器
 *
 * @param <S>  源类型
 * @param <T>  目标类型
 * @param <S1> 源编号类型
 * @param <T1> 目标编号类型
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class TreeCollector<S, T, S1, T1> {

    /**
     * 源编号映射器
     */
    private Function<S, S1> sourceIdMapper;

    /**
     * 源父编号映射器
     */
    private Function<S, S1> sourceParentIdMapper;

    /**
     * 从目标中获取目标编号
     */
    private Function<T, T1> targetIdMapper;

    /**
     * 从源中获取目标编号
     */
    private Function<S, T1> sourceTargetIdMapper;

    /**
     * 子节点消费者
     */
    private BiConsumer<T, List<T>> childrenConsumer;

    /**
     * 批量转换器
     */
    private Function<Collection<S>, List<T>> batchConverter;

    /**
     * 根节点过滤器
     */
    private BiFunction<Map<S1, S>, S1, Boolean> rootFilter;

    /**
     * 查找有用的源
     *
     * @param usefulConsumer 有用的源消费者
     * @param parent         父源
     * @param childrenMapper 子节点映射器
     */
    private void findUsefulSource(Consumer<S> usefulConsumer, S parent, Function<S, List<S>> childrenMapper) {
        List<S> sourceChildren = childrenMapper.apply(parent);
        if (CollectionUtils.isEmpty(sourceChildren)) {
            return;
        }
        for (S sourceChild : sourceChildren) {
            usefulConsumer.accept(sourceChild);
            findUsefulSource(usefulConsumer, sourceChild, childrenMapper);
        }
    }

    /**
     * 转换目标
     *
     * @param parent         父节点
     * @param targetMapper   目标映射器
     * @param childrenMapper 子节点映射器
     * @return 目标
     */
    private T parseTarget(S parent, Function<S, T> targetMapper, Function<S, List<S>> childrenMapper) {
        T targetParent = targetMapper.apply(parent);
        List<S> sourceChildren = childrenMapper.apply(parent);
        if (CollectionUtils.isNotEmpty(sourceChildren)) {
            List<T> targetChildren = sourceChildren
                    .stream()
                    .filter(Objects::nonNull)
                    .map(sourceChild -> this.parseTarget(sourceChild, targetMapper, childrenMapper))
                    .filter(Objects::nonNull)
                    .collect(new ListCollector<>(sourceChildren.size()));
            this.childrenConsumer.accept(targetParent, targetChildren);
        } else {
            this.childrenConsumer.accept(targetParent, Collections.emptyList());
        }
        return targetParent;
    }

    /**
     * 收集
     *
     * @param sources 数据源
     * @return 聚集结果
     */
    public List<T> collect(Collection<S> sources) {
        if (CollectionUtils.isEmpty(sources)) {
            return Collections.emptyList();
        }
        int size = sources.size();
        List<S> roots = new ArrayList<>(size);
        Map<S1, S> sourceMap = new LinkedHashMap<>(size);
        Map<S1, S> usefulMap = new LinkedHashMap<>(size);
        Map<S1, List<S>> childrenMap = new LinkedHashMap<>(size);
        Map<S1, S1> childIdMapParentId = new LinkedHashMap<>(size);
        sources
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                        source -> Optional
                                .ofNullable(this.sourceIdMapper.apply(source))
                                .ifPresent(
                                        id -> {
                                            sourceMap.put(id, source);
                                            S1 parentId = this.sourceParentIdMapper.apply(source);
                                            if (parentId != null) {
                                                childrenMap.computeIfAbsent(parentId, key -> new ArrayList<>(size)).add(source);
                                                childIdMapParentId.put(id, parentId);
                                            }
                                        }
                                )
                );
        //确定根节点
        sourceMap
                .entrySet()
                .stream()
                .filter(entry -> {
                    //必须没有父节点编号，或父节点不存在
                    S1 parentId = childIdMapParentId.get(entry.getKey());
                    if (parentId == null) {
                        return true;
                    } else {
                        return !sourceMap.containsKey(parentId);
                    }
                })
                .filter(entry -> {
                    if (this.rootFilter == null) {
                        return true;
                    }
                    return this.rootFilter.apply(sourceMap, entry.getKey());
                })
                .forEach(entry -> {
                    roots.add(entry.getValue());
                    usefulMap.put(entry.getKey(), entry.getValue());
                });
        //确定无断层的节点
        for (S root : roots) {
            this.findUsefulSource(
                    source -> usefulMap.put(this.sourceIdMapper.apply(source), source),
                    root, source -> childrenMap.get(this.sourceIdMapper.apply(source))
            );
        }
        if (CollectionUtils.isEmpty(roots)) {
            return Collections.emptyList();
        }
        if (MapUtils.isEmpty(usefulMap)) {
            return Collections.emptyList();
        }
        List<T> targets = this.batchConverter.apply(usefulMap.values());
        if (CollectionUtils.isEmpty(targets)) {
            return Collections.emptyList();
        }
        Map<T1, T> targetMap = targets
                .stream()
                .filter(Objects::nonNull)
                .collect(new MapCollector<>(targets.size(), target -> targetIdMapper.apply(target), target -> target));
        if (MapUtils.isEmpty(targetMap)) {
            return Collections.emptyList();
        }
        return roots
                .stream()
                .map(root -> this.parseTarget(
                        root,
                        source -> targetMap.get(this.sourceTargetIdMapper.apply(source)),
                        source -> childrenMap.get(this.sourceIdMapper.apply(source))
                ))
                .collect(new ListCollector<>(roots.size()));
    }
}
