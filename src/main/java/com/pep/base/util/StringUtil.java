package com.pep.base.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 字符串工具
 *
 * @author gang.liu
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unused")
public class StringUtil {

    /**
     * 0
     */
    public static final char ZERO = '0';

    /**
     * 负数前缀
     */
    public static final char NEGATIVE_PREFIX = '-';

    /**
     * 正数前缀
     */
    public static final char POSITIVE_PREFIX = '+';

    /**
     * 斜杠
     */
    public static final String SLASH = "/";

    /**
     * 空格的正则表达式
     */
    public static final String SPACE_REGEX = "\\s";

    /**
     * 分号
     */
    public static final String SEMICOLON = ";";

    /**
     * 空值字符串
     */
    public static final String NULL = "null";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * 空的字符串数组
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 转换唯一值结果为空时返回null
     */
    public static final Supplier<String[]> UNIQUE_EMPTY_NULL_SUPPLIER = () -> null;

    /**
     * 分段
     *
     * @param text          原始文本
     * @param splitters     分割器列表
     * @param splitterIndex 分割器索引
     * @return 分段结果
     */
    public static List<String> paragraph(String text, List<Pattern> splitters, int splitterIndex) {
        if (splitters.size() <= splitterIndex) {
            return Collections.emptyList();
        }
        Pattern pattern = splitters.get(splitterIndex);
        String[] items = pattern.split(text);
        List<String> paragraphs = new ArrayList<>();
        for (String paragraph : items) {
            paragraphs.add(paragraph);
            List<String> subParagraphs = paragraph(paragraph, splitters, splitterIndex + 1);
            if (CollectionUtils.isNotEmpty(subParagraphs)) {
                paragraphs.addAll(subParagraphs);
            }
        }
        return paragraphs;
    }

    /**
     * 匹配后缀
     *
     * @param string           原字符串
     * @param suffix           后缀
     * @param interceptHandler 截取处理器
     */
    public static String matchSuffix(String string, String suffix, UnaryOperator<String> interceptHandler) {
        if (Objects.isNull(string) || Objects.isNull(suffix)) {
            return interceptHandler.apply(string);
        }
        if (Objects.equals(string, suffix)) {
            return interceptHandler.apply(string);
        }
        if (suffix.length() < string.length()) {
            int index = string.lastIndexOf(suffix);
            if (index == string.length() - suffix.length()) {
                return interceptHandler.apply(string.substring(0, index));
            }
        }
        return string;
    }

    /**
     * 匹配后缀
     *
     * @param string           原字符串
     * @param suffix           后缀
     * @param interceptHandler 截取处理器
     */
    public static void matchSuffix(String string, String suffix, Consumer<String> interceptHandler) {
        matchSuffix(string, suffix, value -> {
            interceptHandler.accept(value);
            return null;
        });
    }

    /**
     * 去除后缀
     *
     * @param string 原字符串
     * @param suffix 后缀
     * @return 新字符串
     */
    public static String removeSuffix(String string, String suffix) {
        return matchSuffix(string, suffix, value -> value);
    }

    /**
     * 匹配前缀
     *
     * @param string           原字符串
     * @param prefix           前缀
     * @param interceptHandler 截取处理器
     */
    public static String matchPrefix(String string, String prefix, UnaryOperator<String> interceptHandler) {
        if (Objects.isNull(string) || Objects.isNull(prefix)) {
            return interceptHandler.apply(string);
        }
        if (Objects.equals(string, prefix)) {
            return interceptHandler.apply(string);
        }
        if (prefix.length() < string.length()) {
            int index = string.indexOf(prefix);
            if (index == 0) {
                return interceptHandler.apply(string.substring(prefix.length()));
            }
        }
        return string;
    }

    /**
     * 去除前缀
     *
     * @param string 原字符串
     * @param prefix 前缀
     * @return 新字符串
     */
    public static String removePrefix(String string, String prefix) {
        return matchPrefix(string, prefix, value -> value);
    }

    /**
     * 获取友好的字符串操作对象
     *
     * @param value 字符串
     * @return 友好的字符串操作对象
     */
    @NonNull
    public static Optional<String> ifFriendly(@Nullable String value) {
        if (Objects.isNull(value)) {
            return Optional.empty();
        }
        value = value.trim();
        if (value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    /**
     * 获取友好的字符串操作对象
     *
     * @param value 字符串
     * @return 友好的字符串操作对象
     */
    @NonNull
    public static Optional<String> ifFriendly(@Nullable Object value) {
        return Optional
                .ofNullable(value)
                .map(item -> {
                    if (item instanceof String) {
                        return (String) item;
                    }
                    return item.toString();
                })
                .flatMap(StringUtil::ifFriendly);
    }

    /**
     * 判断是不是友好字符串
     *
     * @param value 字符串
     * @return 是不是友好字符串
     */
    public static boolean isFriendly(@Nullable String value) {
        return ifFriendly(value).isPresent();
    }

    /**
     * 判断是不是友好字符串
     *
     * @param value 字符串
     * @return 是不是友好字符串
     */
    public static boolean isFriendly(@Nullable Object value) {
        return ifFriendly(value).isPresent();
    }

    /**
     * 判断是不是友好字符串
     *
     * @param value 字符串
     * @return 是不是友好字符串
     */
    public static boolean isFriendly(@Nullable CharSequence value) {
        if (Objects.isNull(value)) {
            return false;
        }
        if (value instanceof String) {
            return isFriendly((String) value);
        }
        return isFriendly(value.toString());
    }

    /**
     * 判断是不是非友好字符串
     *
     * @param value 字符串
     * @return 是不是非友好字符串
     */
    public static boolean isUnfriendly(@Nullable String value) {
        return !isFriendly(value);
    }

    /**
     * 判断是不是非友好字符串
     *
     * @param value 字符串
     * @return 是不是非友好字符串
     */
    public static boolean isUnfriendly(@Nullable CharSequence value) {
        return !isFriendly(value);
    }

    /**
     * 判断是不是非友好字符串
     *
     * @param value 字符串
     * @return 是不是非友好字符串
     */
    public static boolean isUnfriendly(@Nullable Object value) {
        return !isFriendly(value);
    }

    /**
     * 获取友好的字符串
     *
     * @param value 字符串
     * @return 友好的字符串
     */
    @Nullable
    public static String friendly(@Nullable String value) {
        return ifFriendly(value).orElse(null);
    }

    /**
     * 获取友好的字符串
     *
     * @param value 字符串
     * @return 友好的字符串
     */
    @NonNull
    public static String[] friendly(@Nullable String[] value) {
        return Optional
                .ofNullable(value)
                .map(
                        item -> Stream
                                .of(item)
                                .map(StringUtil::friendly)
                                .filter(Objects::nonNull)
                                .toArray(String[]::new)
                )
                .orElse(StringUtil.EMPTY_STRING_ARRAY);
    }

    /**
     * 获取友好的字符串
     *
     * @param value 字符串
     * @return 友好的字符串
     */
    @Nullable
    public static String friendly(@Nullable Object value) {
        return ifFriendly(value).orElse(null);
    }

    /**
     * 获取友好的字符串操作对象
     *
     * @param value 字符串
     * @return 友好的字符串操作对象
     */
    @NonNull
    public static Optional<String> getFriendlyStringOptional(@Nullable String value) {
        return ifFriendly(value);
    }

    /**
     * 获取友好的字符串
     *
     * @param value 字符串
     * @return 友好的字符串
     */
    @Nullable
    public static String getFriendlyString(@Nullable String value) {
        return friendly(value);
    }

    /**
     * 如果是长整型
     *
     * @param value 长整型字符串
     * @return 操作器
     */
    @NonNull
    public static Optional<Long> ifLong(@Nullable String value) {
        return ifFriendly(value)
                .filter(StringUtil::isLong)
                .map(Long::parseLong);
    }

    /**
     * 如果是长整型
     *
     * @param value 长整型内容
     * @return 操作器
     */
    @NonNull
    public static Optional<Long> ifLong(@Nullable Object value) {
        if (Objects.isNull(value)) {
            return Optional.empty();
        }
        if (value instanceof Number) {
            return Optional.of(((Number) value).longValue());
        }
        return ifFriendly(value)
                .filter(StringUtil::isLong)
                .map(Long::parseLong);
    }

    /**
     * 如果是整型
     *
     * @param value 整型字符串
     * @return 操作器
     */
    @NonNull
    public static Optional<Integer> ifInteger(@Nullable String value) {
        return ifFriendly(value)
                .filter(StringUtil::isInteger)
                .map(Integer::parseInt);
    }

    /**
     * 如果是整型
     *
     * @param value 整型内容
     * @return 操作器
     */
    @NonNull
    public static Optional<Integer> ifInteger(@Nullable Object value) {
        if (Objects.isNull(value)) {
            return Optional.empty();
        }
        if (value instanceof Number) {
            return Optional.of(((Number) value).intValue());
        }
        return ifFriendly(value)
                .filter(StringUtil::isInteger)
                .map(Integer::parseInt);
    }

    /**
     * 转换成长整型
     *
     * @param value 长整型字符串
     * @return 长整型
     */
    @Nullable
    public static Long parseLong(@Nullable String value) {
        return ifLong(value).orElse(null);
    }

    /**
     * 转换成长整型
     *
     * @param value 长整型内容
     * @return 长整型
     */
    @Nullable
    public static Long parseLong(@Nullable Object value) {
        return ifLong(value).orElse(null);
    }

    /**
     * 转换成整型
     *
     * @param value 整型字符串
     * @return 整型
     */
    @Nullable
    public static Integer parseInteger(@Nullable String value) {
        return ifInteger(value).orElse(null);
    }

    /**
     * 转换成整型
     *
     * @param value 整型内容
     * @return 整型
     */
    @Nullable
    public static Integer parseInteger(@Nullable Object value) {
        return ifInteger(value).orElse(null);
    }

    /**
     * 转换成唯一值数组
     *
     * @param collection    集合
     * @param emptySupplier 空值生产者
     * @return 数组
     */
    public static String[] unique(Collection<String> collection, Supplier<String[]> emptySupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            return emptySupplier.get();
        }
        String[] array = collection
                .stream()
                .map(StringUtil::friendly)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(String[]::new);
        if (ArrayUtils.isEmpty(array)) {
            return emptySupplier.get();
        } else {
            return array;
        }
    }

    /**
     * 转换成唯一值数组
     *
     * @param collection 集合
     * @return 数组
     */
    public static String[] unique(Collection<String> collection) {
        return unique(collection, UNIQUE_EMPTY_NULL_SUPPLIER);
    }

    /**
     * 转换成驼峰命名
     *
     * @param originalName 原名称
     * @return 驼峰命名
     */
    public static String toCamelCase(String originalName) {
        String[] nameArray = StringUtil
                .ifFriendly(originalName)
                .map(item -> item.toLowerCase().split("[\\W_]"))
                .filter(ArrayUtils::isNotEmpty)
                .orElse(null);
        if (Objects.isNull(nameArray)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String name : nameArray) {
            if (StringUtils.isEmpty(name)) {
                continue;
            }
            if (builder.isEmpty()) {
                builder.append(name);
            } else {
                builder.append(name.substring(0, 1).toUpperCase());
                builder.append(name.substring(1));
            }
        }
        return builder.toString();
    }

    /**
     * 是否是长整型字符串
     * <p>
     * 这里借鉴了JDK内部Long的转换逻辑，所以不对Sonar检查做修复（squid:S3776）
     * <p>
     * {@link Long#parseLong(String, int)}
     *
     * @param value 字符串
     * @param radix 基数
     * @return 是否是长整型字符串
     */
    @SuppressWarnings("squid:S3776")
    public static boolean isLong(String value, int radix) {
        if (value == null) {
            return false;
        }
        if (radix < Character.MIN_RADIX) {
            return false;
        }
        if (radix > Character.MAX_RADIX) {
            return false;
        }
        long result = 0;
        int i = 0;
        int len = value.length();
        long limit = -Long.MAX_VALUE;
        long multiMin;
        int digit;
        if (len > 0) {
            char firstChar = value.charAt(0);
            if (firstChar < ZERO) {
                // Possible leading "+" or "-"
                if (firstChar == NEGATIVE_PREFIX) {
                    limit = Long.MIN_VALUE;
                } else if (firstChar != POSITIVE_PREFIX) {
                    return false;
                }
                if (len == 1) {
                    // Cannot have lone "+" or "-"
                    return false;
                }
                i++;
            }
            multiMin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(value.charAt(i++), radix);
                if (digit < 0) {
                    return false;
                }
                if (result < multiMin) {
                    return false;
                }
                result *= radix;
                if (result < limit + digit) {
                    return false;
                }
                result -= digit;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 是否是整型字符串
     * <p>
     * 这里借鉴了JDK内部Integer的转换逻辑，所以不对Sonar检查做修复（squid:S3776、S1659）
     * <p>
     * {@link Integer#parseInt(String, int)}
     *
     * @param value 字符串
     * @param radix 基数
     * @return 是否是整型字符串
     */
    @SuppressWarnings({"squid:S3776", "squid:S1659"})
    public static boolean isInteger(String value, int radix) {
        if (value == null) {
            return false;
        }
        if (radix < Character.MIN_RADIX) {
            return false;
        }
        if (radix > Character.MAX_RADIX) {
            return false;
        }
        int result = 0;
        int i = 0, len = value.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;
        if (len > 0) {
            char firstChar = value.charAt(0);
            if (firstChar < '0') {
                // Possible leading "+" or "-"
                if (firstChar == '-') {
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+') {
                    return false;
                }
                if (len == 1) {
                    // Cannot have lone "+" or "-"
                    return false;
                }
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                if (result < multmin) {
                    return false;
                }
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(value.charAt(i++), radix);
                if (digit < 0) {
                    return false;
                }
                result *= radix;
                if (result < limit + digit) {
                    return false;
                }
                result -= digit;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 是否是长整型字符串
     *
     * @param value 字符串
     * @return 是否是长整型字符串
     */
    public static boolean isLong(String value) {
        return isLong(value, 10);
    }

    /**
     * 是否是整型字符串
     *
     * @param value 字符串
     * @return 是否是整型字符串
     */
    public static boolean isInteger(String value) {
        return isInteger(value, 10);
    }

    /**
     * 是否不是长整型字符串
     *
     * @param value 字符串
     * @param radix 基数
     * @return 是否不是长整型字符串
     */
    public static boolean isNotLong(String value, int radix) {
        return !isLong(value, radix);
    }

    /**
     * 是否不是整型字符串
     *
     * @param value 字符串
     * @param radix 基数
     * @return 是否不是整型字符串
     */
    public static boolean isNotInteger(String value, int radix) {
        return !isInteger(value, radix);
    }

    /**
     * 是否不是长整型字符串
     *
     * @param value 字符串
     * @return 是否不是长整型字符串
     */
    public static boolean isNotLong(String value) {
        return isNotLong(value, 10);
    }

    /**
     * 是否不是整型字符串
     *
     * @param value 字符串
     * @return 是否不是整型字符串
     */
    public static boolean isNotInteger(String value) {
        return isNotInteger(value, 10);
    }

    /**
     * 按字符范围获取字符串
     *
     * @param begin 起始字符
     * @param count 字符个数
     * @return 字符串
     */
    public static String range(char begin, int count) {
        char[] chars = new char[count];
        for (int i = 0; i < count; i++) {
            chars[i] = (char) (begin + i);
        }
        return new String(chars);
    }

    /**
     * 去空格
     *
     * @param value 值
     * @return 去空格之后的值
     */
    public static String trim(String value) {
        return Optional
                .ofNullable(value)
                .map(String::trim)
                .orElse(null);
    }

    /**
     * 去掉左右两侧的斜杠
     *
     * @param value 字符串
     * @return 处理结果
     */
    public static String trimSlash(String value) {
        String result = value.trim();
        while (result.startsWith(SLASH)) {
            result = result.substring(1).trim();
        }
        while (result.endsWith(SLASH)) {
            result = result.substring(0, result.length() - 1).trim();
        }
        return result;
    }

    /**
     * 拆分字符串并去掉每个元素左右两侧的空格
     *
     * @param value     字符串
     * @param delimiter 分隔符
     * @return 拆分结果
     */
    public static String[] trimSplit(String value, String delimiter) {
        return Optional
                .ofNullable(value)
                .map(item -> item.split(delimiter))
                .filter(ArrayUtils::isNotEmpty)
                .map(
                        array -> Stream
                                .of(array)
                                .map(StringUtil::friendly)
                                .filter(Objects::nonNull)
                                .toArray(String[]::new)
                )
                .filter(ArrayUtils::isNotEmpty)
                .orElse(EMPTY_STRING_ARRAY);
    }

    /**
     * 转换成字符串
     *
     * @param map 映射表
     * @return 字符串
     */
    public static String toString(Map<String, String[]> map) {
        if (Objects.isNull(map)) {
            return NULL;
        }
        StringBuilder builder = new StringBuilder();
        map.forEach((key, values) -> {
            if (!builder.isEmpty()) {
                builder.append(COMMA);
            }
            builder.append(key);
            builder.append("=");
            if (Objects.isNull(values)) {
                builder.append(NULL);
                return;
            }
            builder.append("[");
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    builder.append(COMMA);
                }
                builder.append(values[i]);
            }
            builder.append("]");
        });
        return builder
                .insert(0, "{")
                .append("}")
                .toString();
    }

    /**
     * 忽略大小写时不一致
     *
     * @param string        字符串
     * @param anotherString 另一个字符串
     * @return 忽略大小写时不一致
     */
    public static boolean notEqualsIgnoreCase(String string, String anotherString) {
        return !Strings.CI.equals(string, anotherString);
    }

    /**
     * 扁平化
     *
     * @param value 值
     * @return 扁平化结果
     */
    public static String flatten(Object value) {
        return Optional
                .ofNullable(value)
                .map(item -> {
                    if (item instanceof String) {
                        return (String) item;
                    }
                    return item.toString();
                })
                .map(item -> item.replaceAll("\\R", StringUtils.SPACE))
                .orElse(null);
    }

}
