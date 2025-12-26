package com.pep.base.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字段错误视图对象
 *
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@Schema(name = "FieldErrorVo 字段错误视图对象", description = "字段错误视图对象")
public class FieldErrorVo implements Serializable {

    /**
     * 序列化编号
     */
    @Serial
    private static final long serialVersionUID = 1358143300808613790L;

    /**
     * 对象名称
     */
    @Schema(description = "对象名称")
    private String objectName;

    /**
     * 字段名
     */
    @Schema(description = "字段名")
    private String fieldName;

    /**
     * 异常消息
     */
    @Schema(description = "异常消息")
    private String message;
}
