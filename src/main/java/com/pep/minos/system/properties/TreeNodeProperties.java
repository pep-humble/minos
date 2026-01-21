package com.pep.minos.system.properties;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 树形结构配置信息
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class TreeNodeProperties {

    /**
     * 每级编号的长度, 例如0000, 00010002
     */
    private int length = 4;

    /**
     * 每位组成单元, 例如0123456789为10进制, 0123456789abcdefghijklmnopqrstuvwxyz为36进制
     * 默认10进制下, 每一级最大个数为9999. 如果业务不满足,调整此配置,比如评论系统,可调整为高进制以及每级编号长度
     */
    private char[] digits;

    /**
     * 每级最大深度, 允许存在子级的层数
     */
    private int maximumDepth = 6;

}
