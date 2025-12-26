package com.pep.base.form;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 单个编号
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class IdForm implements Serializable {

    /**
     * 序列化编号
     */
    @Serial
    private static final long serialVersionUID = -3952785410874093800L;

    /**
     * 编号
     */
    private String id;
}