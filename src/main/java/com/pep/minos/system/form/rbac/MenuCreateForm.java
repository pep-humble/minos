package com.pep.minos.system.form.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 菜单创建表单
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "MenuCreateForm 菜单创建表单", description = "菜单创建表单")
public class MenuCreateForm {

    /**
     * 上级节点编号
     */
    @Schema(description = "上级节点编号")
    private String parentId;

    /**
     * 菜单名称
     */
    @NotBlank
    @Schema(description = "菜单名称")
    private String label;

    /**
     * 鼠标悬浮内容展示
     */
    @Schema(description = "鼠标悬浮内容展示")
    private String title;

    /**
     * 菜单类型
     */
    @NotBlank
    @Schema(description = "菜单类型")
    private String menuType;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String routerPath;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String componentPath;

    /**
     * 是否显示
     */
    @Schema(description = "是否显示")
    private Boolean visible;

    /**
     * 菜单状态
     */
    @Schema(description = "菜单状态")
    private Boolean status;

    /**
     * 权限标识符
     */
    @Schema(description = "权限标识符")
    private String perms;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 页面是否缓存
     */
    @Schema(description = "页面是否缓存")
    private Boolean cache;

    /**
     * 外链地址
     */
    @Schema(description = "外链地址")
    private String linkPath;

    /**
     * 链接打开方式
     */
    @Schema(description = "链接打开方式")
    private String linkOpenType;

    /**
     * 多任务栏
     */
    @Schema(description = "多任务栏")
    private Boolean viewTab;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String query;

    /**
     * 排序 用于同一级排序使用
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 附加属性
     */
    @Schema(description = "附加属性")
    private Map<String, Object> addition;
}
