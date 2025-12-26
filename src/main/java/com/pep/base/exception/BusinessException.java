package com.pep.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 业务异常
 * @author liu.gang
 */
@Data
@Slf4j
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    /**
     * 响应码
     */
    private final int responseCode;

    /**
     * 消息参数
     */
    private final transient Object[] messageArgs;

    /**
     * 业务数据
     */
    private final Serializable data;

    /**
     * 构造函数
     *
     * @param message      异常消息
     * @param responseCode 响应码
     * @param messageArgs  异常消息
     * @param data         业务数据
     */
    public BusinessException(final String message, final int responseCode, final Object[] messageArgs, final Serializable data) {
        super(message);
        this.responseCode = responseCode;
        this.messageArgs = messageArgs;
        this.data = data;
    }

    /**
     * 构造函数
     *
     * @param message      异常消息
     * @param cause        原因
     * @param responseCode 响应码
     * @param messageArgs  异常消息
     * @param data         业务数据
     */
    public BusinessException(final String message, final Throwable cause, final int responseCode, final Object[] messageArgs, final Serializable data) {
        super(message, cause);
        this.responseCode = responseCode;
        this.messageArgs = messageArgs;
        this.data = data;
    }

    /**
     * 构造函数
     *
     * @param message      异常消息
     * @param responseCode 响应码
     * @param messageArgs  异常消息
     */
    public BusinessException(final String message, final int responseCode, final Object[] messageArgs) {
        this(message, responseCode, messageArgs, null);
    }

    /**
     * 构造函数
     *
     * @param message      异常消息
     * @param cause        原因
     * @param responseCode 响应码
     * @param messageArgs  异常消息
     */
    public BusinessException(final String message, final Throwable cause, final int responseCode, final Object[] messageArgs) {
        this(message, cause, responseCode, messageArgs, null);
    }

    /**
     * 构造函数
     *
     * @param message     异常消息
     * @param cause       原因
     * @param messageArgs 异常消息
     */
    public BusinessException(final String message, final Throwable cause, final Object... messageArgs) {
        this(message, cause, HttpStatus.INTERNAL_SERVER_ERROR.value(), messageArgs);
    }

    /**
     * 构造函数
     *
     * @param cause 原因
     */
    public BusinessException(final Throwable cause) {
        this(cause.getMessage(), cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * 构造函数
     *
     * @param message     异常消息
     * @param messageArgs 异常消息
     */
    public BusinessException(final String message, final Object... messageArgs) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), messageArgs, null);
    }

    /**
     * 构造函数
     *
     * @param message     异常消息
     * @param status      http状态
     * @param messageArgs 异常消息
     */
    public BusinessException(final String message, final HttpStatus status, final Object... messageArgs) {
        this(message, status.value(), messageArgs);
    }

    /**
     * 创建异常
     *
     * @param message      异常消息
     * @param responseCode 响应码
     * @param messageArgs  消息参数
     * @return 异常信息
     */
    public static BusinessException newException(final String message, final int responseCode, final Object[] messageArgs) {
        log.error("message:{}, responseCode:{}, messageArgs:{}", message, responseCode, messageArgs);
        return new BusinessException(message, responseCode, messageArgs);
    }
}
