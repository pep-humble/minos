package com.pep.base.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 对象错误视图对象
 *
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@Schema(name = "ObjectErrorVo 对象错误视图对象", description = "对象错误视图对象")
public class ObjectErrorVo implements Serializable {

    /**
     * 序列化编号
     */
    @Serial
    private static final long serialVersionUID = 4735216145543665014L;

    /**
     * 对象名称
     */
    @Schema(description = "对象名称")
    private String objectName;

    /**
     * 异常消息
     */
    @Schema(description = "异常消息")
    private String message;
}
