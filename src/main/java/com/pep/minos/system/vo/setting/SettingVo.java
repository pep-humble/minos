package com.pep.minos.system.vo.setting;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Properties;

/**
 * 设置项响应视图
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "SettingVo 设置项响应视图", description = "设置项响应视图")
public class SettingVo {

    /**
     * 设置项唯一编码
     */
    @Schema(description = "设置项唯一编码")
    @JSONField(ordinal = 1)
    private String id;

    /**
     * 首次创建者编号
     */
    @Schema(description = "首次创建者编号")
    @JSONField(ordinal = 2)
    private String creator;

    /**
     * 首次创建时间
     */
    @Schema(description = "首次创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 3)
    private Date createTime;

    /**
     * 最后修改者编号
     */
    @Schema(description = "最后修改者编号")
    @JSONField(ordinal = 4)
    private String modifier;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 5)
    private Date modifyTime;

    /**
     * 设置项唯一名称
     */
    @Schema(description = "设置项唯一名称")
    @JSONField(ordinal = 6)
    private String key;

    /**
     * 设置项描述
     */
    @Schema(description = "设置项描述")
    @JSONField(ordinal = 7)
    private String desc;

    /**
     * 是否开启此设置
     */
    @Schema(description = "是否开启此设置")
    @JSONField(ordinal = 8)
    private boolean enabled;

    /**
     * 设置项值
     */
    @Schema(description = "设置项值")
    @JSONField(ordinal = 9)
    private Properties properties;
}
