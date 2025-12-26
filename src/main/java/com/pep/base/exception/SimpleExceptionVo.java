package com.pep.base.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 简单异常视图对象
 *
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@Schema(name = "SimpleExceptionVo 异常视图对象", description = "异常视图对象")
public class SimpleExceptionVo implements Serializable {

    /**
     * 序列化编号
     */
    @Serial
    private static final long serialVersionUID = 907173487658849854L;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private Class<?> type;

    /**
     * 原因类型
     */
    @Schema(description = "原因类型")
    private Class<?> causeType;

    /**
     * 异常消息
     */
    @Schema(description = "异常消息")
    private String message;
}
