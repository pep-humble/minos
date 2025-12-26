package com.pep.base.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 异常视图对象
 *
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@Schema(name = "ExceptionVo 异常视图对象", description = "异常视图对象")
public class ExceptionVo implements Serializable {

    /**
     * 序列化编号
     */
    @Serial
    private static final long serialVersionUID = 1823536172071572724L;

    /**
     * 是否成功
     */
    @Schema(description = "是否成功")
    private final Boolean success;

    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String msg;

    /**
     * 附加数据
     */
    @Schema(description = "附加数据")
    private Serializable data;

    /**
     * 构造函数
     */
    public ExceptionVo() {
        this.success = Boolean.FALSE;
    }
}
