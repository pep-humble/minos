package com.pep.minos.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import com.pep.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 系统菜单
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "System_Menu", autoResultMap = true)
public class SystemMenu extends BaseEntity {

    /**
     * 树形编号
     */
    @TableField(value = "TreeId")
    private String treeId;

    /**
     * 上级节点编号
     */
    @TableField(value = "ParentId")
    private String parentId;

    /**
     * 分组编号
     */
    @TableField(value = "GroupId")
    private String groupId;

    /**
     * 当前层级
     */
    @TableField(value = "Depth")
    private Integer depth;

    /**
     * 是否叶子节点
     */
    @TableField(value = "IsLeaf")
    private Boolean isLeaf;

    /**
     * 排序 用于同一级排序使用
     */
    @TableField(value = "Sort")
    private Integer sort;

    /**
     * 是否删除
     */
    @TableField(value = "Deleted")
    private Boolean deleted;

    /**
     * 附加属性
     */
    @TableField(value = "Addition", typeHandler = Fastjson2TypeHandler.class)
    private Map<String, Object> addition;

    /**
     * 菜单名称
     */
    @TableField(value = "Label")
    private String label;

    /**
     * 鼠标悬浮内容展示
     */
    @TableField(value = "Title")
    private String title;

    /**
     * 菜单类型
     */
    @TableField(value = "MenuType")
    private String menuType;

    /**
     * 路由地址
     */
    @TableField(value = "RouterPath")
    private String routerPath;

    /**
     * 组件路径
     */
    @TableField(value = "ComponentPath")
    private String componentPath;

    /**
     * 是否显示
     */
    @TableField(value = "Visible")
    private Boolean visible;

    /**
     * 菜单状态
     */
    @TableField(value = "Status")
    private Boolean status;

    /**
     * 权限标识符
     */
    @TableField(value = "Perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField(value = "Icon")
    private String icon;

    /**
     * 备注
     */
    @TableField(value = "Remark")
    private String remark;

    /**
     * 页面是否缓存
     */
    @TableField(value = "Cache")
    private Boolean cache;

    /**
     * 外链地址
     */
    @TableField(value = "LinkPath")
    private String linkPath;

    /**
     * 链接打开方式
     */
    @TableField(value = "LinkOpenType")
    private String linkOpenType;

    /**
     * 多任务栏
     */
    @TableField(value = "ViewTab")
    private Boolean viewTab;

    /**
     * 路由参数
     */
    @TableField(value = "Query")
    private String query;

}