package com.pep.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 数据库基础字段
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
public class BaseEntity {

    /**
     * 自增主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 唯一键
     */
    @TableField(value = "UUID")
    private String uuid;

    /**
     * 首次创建者编号
     */
    @TableField(value = "Creator", fill = FieldFill.INSERT)
    private String creator;

    /**
     * 首次创建时间
     */
    @TableField(value = "CreateTime", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后修改者编号
     */
    @TableField(value = "Modifier", fill = FieldFill.INSERT_UPDATE)
    private String modifier;

    /**
     * 最后修改时间
     */
    @TableField(value = "ModifyTime", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}
