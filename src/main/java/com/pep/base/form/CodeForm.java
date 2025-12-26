package com.pep.base.form;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 单个编码表单
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class CodeForm implements Serializable {

    /**
     * 序列化编号
     */
    @Serial
    private static final long serialVersionUID = -3994292215791058258L;

    /**
     * 编码
     */
    private String code;
}