package com.pep.minos.system.configuration;

import com.pep.minos.system.properties.TreeNodeProperties;
import com.pep.minos.system.service.TreeNodeIdGenerateStrategy;
import com.pep.minos.system.service.impl.DefaultTreeNodeIdGenerateStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 树形结构配置
 *
 * @author liu.gang
 */
@Configuration
public class TreeConfiguration {

    /**
     * 树形编号生成策略的默认实现
     *
     * @param treeNodeProperties 树形结构配置信息
     * @return 树形编号生成策略的默认实现
     */
    @Bean
    public TreeNodeIdGenerateStrategy treeNodeIdGenerateStrategy(TreeNodeProperties treeNodeProperties) {
        return new DefaultTreeNodeIdGenerateStrategy(treeNodeProperties);
    }

    /**
     * 树形结构配置信息
     *
     * @return 树形结构配置信息
     */
    @Bean
    @ConfigurationProperties(prefix = "business.tree.menu")
    public TreeNodeProperties treeNodeProperties() {
        return new TreeNodeProperties();
    }

}
