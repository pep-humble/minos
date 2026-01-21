package com.pep.minos.system.service.impl;

import com.pep.minos.system.exception.TreeNodeIdIllegalException;
import com.pep.minos.system.properties.TreeNodeProperties;
import com.pep.minos.system.service.TreeNodeIdGenerateStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * 树形编号生成策略的默认实现
 *
 * @author liu.gang
 */
public class DefaultTreeNodeIdGenerateStrategy implements TreeNodeIdGenerateStrategy {

    /**
     * 树形结构配置信息
     */
    private final TreeNodeProperties treeProperties;

    /**
     * 进制 例如10进制, 36进制 取决于digits的长度
     */
    private final int radix;

    /**
     * 依赖当前规则获取每一级最小编号, 用于作为起始编号
     */
    private final String levelMinTreeId;

    /**
     * 字符->值 映射关系
     */
    private final Map<Character, Integer> charToValueMapping;

    public DefaultTreeNodeIdGenerateStrategy(TreeNodeProperties treeProperties) {
        this.treeProperties = treeProperties;
        char[] digits = treeProperties.getDigits();
        if (Objects.isNull(digits) || Array.getLength(digits) < 2) {
            throw TreeNodeIdIllegalException.newTreeNodeIdIllegalException("treeProperties digits is illegal, at least 2 elements");
        }
        this.radix = digits.length;
        charToValueMapping = new LinkedHashMap<>(radix);
        IntStream.range(0, radix).forEach(index -> charToValueMapping.put(digits[index], index));
        char[] minTreeChars = new char[treeProperties.getLength()];
        Arrays.fill(minTreeChars, digits[0]);
        this.levelMinTreeId = new String(minTreeChars);
    }

    @Override
    public void ifPostChildTreeNode(String treeId) throws TreeNodeIdIllegalException {
        int depth = inferDepth(treeId);
        // 是否达到最大深度,无法继续新增子级
        if (depth + 1 > treeProperties.getMaximumDepth()) {
            throw TreeNodeIdIllegalException.exceededMaximumDepth();
        }
    }

    /**
     * 校验树形编号长度以及字符合法性
     *
     * @param treeId 树形编号
     * @return 校验结果
     */
    public boolean validateTreeIdFormat(String treeId) {
        return validateTreeIdFormat(treeId, false);
    }

    /**
     * 校验树形编号长度以及字符合法性
     *
     * @param treeId         树形编号
     * @param throwException 是否抛异常
     * @return 校验结果
     */
    @Override
    public boolean validateTreeIdFormat(String treeId, boolean throwException) throws TreeNodeIdIllegalException {
        boolean validateResult = true;
        if (Objects.nonNull(treeId) && !treeId.isEmpty()) {
            for (int i = 0; i < treeId.length(); i++) {
                char c = treeId.charAt(i);
                Integer value = charToValueMapping.get(c);
                if (value == null) {
                    validateResult = false;
                    break;
                }
            }
        } else {
            validateResult = false;
        }
        if (!validateResult && throwException) {
            throw TreeNodeIdIllegalException.newTreeNodeIdIllegalException("treeId is invalid");
        }
        return validateResult;
    }

    /**
     * 树形编号转换成数值类型,便于+1操作
     *
     * @param treeId 树形编号
     * @return 数值
     */
    @Override
    public long treeIdToNumber(String treeId) {
        if (treeId == null || treeId.isEmpty()) {
            throw new IllegalArgumentException("input treeId is empty");
        }
        long result = 0;
        for (int i = 0; i < treeId.length(); i++) {
            char c = treeId.charAt(i);
            Integer value = charToValueMapping.get(c);
            if (value == null) {
                throw new IllegalArgumentException(String.format("char '%c' not in digits '%s'", c, Arrays.toString(treeProperties.getDigits())));
            }
            // 防止极限情况溢出
            if (result > (Long.MAX_VALUE - value) / radix) {
                throw new ArithmeticException("numerical overflow, exceeding the long range");
            }
            result = result * radix + value;
        }
        return result;
    }

    /**
     * 将数值转换为定长字符串
     * 长度不足补充最小位,长度超长则报错
     */
    @Override
    public String numberToTreeId(long number) {
        if (number == 0) {
            return levelMinTreeId;
        }
        StringBuilder sb = new StringBuilder();
        long num = number;
        while (num > 0) {
            int remainder = (int) (num % radix);
            sb.append(treeProperties.getDigits()[remainder]);
            num /= radix;
        }
        String str = sb.reverse().toString();
        if (str.length() > treeProperties.getLength()) {
            throw TreeNodeIdIllegalException.exceededMaximumLength();
        }
        if (str.length() < treeProperties.getLength()) {
            char paddingChar = treeProperties.getDigits()[0];
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < treeProperties.getLength() - str.length(); i++) {
                result.append(paddingChar);
            }
            result.append(str);
            return result.toString();
        }
        return str;
    }

    @Override
    public String nextTreeId(String parentId, String maxChildTreeId) {
        if (Objects.isNull(parentId) || parentId.isEmpty()) {
            parentId = "";
        }
        long nextNumber = 0;
        if (Objects.nonNull(maxChildTreeId) && !maxChildTreeId.isEmpty()) {
            if (maxChildTreeId.startsWith(parentId)) {
                // 判断maxChildTreeId是否是parentId的子级
                String childTreeId = maxChildTreeId.substring(parentId.length());
                List<String> allParentIds = inferAllParentId(childTreeId);
                if (Objects.nonNull(allParentIds) && allParentIds.size() == 1) {
                    String maxTreeId = allParentIds.iterator().next();
                    long number = treeIdToNumber(maxTreeId);
                    nextNumber = number + 1;
                } else {
                    throw TreeNodeIdIllegalException.newTreeNodeIdIllegalException("nextTreeId param is illegal, maxChildTreeId not startsWith parentId");
                }
            } else {
                throw TreeNodeIdIllegalException.newTreeNodeIdIllegalException("nextTreeId param is illegal, maxChildTreeId not startsWith parentId");
            }
        }
        String nextTreeId = numberToTreeId(nextNumber);
        return parentId.concat(nextTreeId);
    }

    @Override
    public int inferDepth(String treeId) {
        validateTreeIdFormat(treeId, false);
        return (treeId.length() + treeProperties.getLength() - 1) / treeProperties.getLength();
    }

    @Override
    public String inferParentId(String treeId) {
        List<String> inferAllParentIds = inferAllParentId(treeId);
        if (Objects.nonNull(inferAllParentIds) && !inferAllParentIds.isEmpty()) {
            inferAllParentIds.remove(inferAllParentIds.size() - 1);
            return String.join("", inferAllParentIds);
        }
        return "";
    }

    @Override
    public List<String> inferAllParentId(String treeId) {
        if (Objects.isNull(treeId) || treeId.isEmpty()) {
            return new ArrayList<>(0);
        }
        // 计算需要分割成多少段
        int size = inferDepth(treeId);
        List<String> allParentIds = new ArrayList<>(size);
        int length = treeId.length();
        for (int i = 0; i < size; i++) {
            int start = Math.max(0, length - (i + 1) * treeProperties.getLength());
            int end = length - i * treeProperties.getLength();
            String parentId = treeId.substring(start, end);
            allParentIds.add(parentId);
        }
        Collections.reverse(allParentIds);
        boolean allMatch = allParentIds.stream()
                // 校验是否合法以及每层父级长度是否合法
                .allMatch(this::validateTreeIdFormat);
        if (allMatch) {
            return allParentIds;
        }
        throw TreeNodeIdIllegalException.newTreeNodeIdIllegalException("some parentIds are invalid");
    }
}