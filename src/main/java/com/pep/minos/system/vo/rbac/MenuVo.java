package com.pep.minos.system.vo.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 菜单响应视图
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@Schema(name = "MenuVo 菜单响应视图", description = "菜单响应视图")
public class MenuVo {

    /**
     * 节点编号
     */
    @Schema(description = "节点编号")
    private String id;

    /**
     * 树形节点编号
     */
    @Schema(description = "树形节点编号")
    private String treeId;

    /**
     * 上级节点编号
     */
    @Schema(description = "上级节点编号")
    private String parentId;

    /**
     * 菜单名称
     */
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
    private boolean visible;

    /**
     * 菜单状态
     */
    @Schema(description = "菜单状态")
    private boolean status;

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
    private boolean cache;

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
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 多任务栏
     */
    @Schema(description = "多任务栏")
    private boolean viewTab;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String query;

    /**
     * 附加属性
     */
    @Schema(description = "附加属性")
    private Map<String, Object> addition;
}
