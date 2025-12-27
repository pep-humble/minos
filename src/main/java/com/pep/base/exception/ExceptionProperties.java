package com.pep.base.exception;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 异常配置
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class ExceptionProperties {

    /**
     * 消息转义映射表
     * <p>
     * 预防XSS攻击，转义异常消息中的字符串
     * <p>
     * key=被替换的字符串，value=替换后的字符串
     */
    private final Map<String, String> messageEscape;

    /**
     * 响应真实的异常
     */
    private Boolean responseRealExceptions;

    /**
     * 构造函数
     */
    public ExceptionProperties() {
        this.messageEscape = Maps.newConcurrentMap();
        this.messageEscape.put("<", "&lt;");
        this.messageEscape.put(">", "&gt;");
        this.messageEscape.put("(", "&lpar;");
        this.messageEscape.put(")", "&rpar;");
        this.messageEscape.put("&", "&amp;");
        this.messageEscape.put("\"", "&quot;");
        this.messageEscape.put("\\", "&bsol;");
        this.messageEscape.put("'", "&apos;");
        this.messageEscape.put(";", "&semi;");
        this.messageEscape.put(":", "&colon;");
    }
}