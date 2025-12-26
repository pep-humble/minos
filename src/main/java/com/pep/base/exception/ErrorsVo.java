package com.pep.base.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 错误集合视图对象
 *
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@Schema(name = "ErrorsVo 错误集合视图对象", description = "错误集合视图对象")
public class ErrorsVo implements Serializable {

    /**
     * 序列化编号
     */
    @Serial
    private static final long serialVersionUID = 4711356575374057899L;

    /**
     * 全局错误
     */
    @Schema(description = "全局错误")
    private List<ObjectErrorVo> globalErrors;

    /**
     * 字段错误
     */
    @Schema(description = "字段错误")
    private List<FieldErrorVo> fieldErrors;
}
