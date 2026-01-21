-- auto-generated definition
create table System_Setting
(
    ID         bigint auto_increment comment '自增主键'
        primary key,
    UUID       varchar(36)                     not null comment '唯一键',
    Creator    varchar(36)                     not null comment '首次创建者编号',
    CreateTime datetime                        not null comment '首次创建时间',
    Modifier   varchar(36)                     not null comment '最后修改者编号',
    ModifyTime datetime                        not null comment '最后修改时间',
    `Key`      varchar(36) collate utf8mb4_bin not null comment '配置项唯一名称',
    `Desc`     varchar(100)                    not null comment '配置项描述',
    Enabled    tinyint(1) default 0            not null comment '此配置项是否开启',
    Properties json                            null comment '详细配置信息',
    constraint UUID
        unique (UUID)
)
    comment '系统配置项表';


-- auto-generated definition
create table System_Menu
(
    ID            bigint auto_increment comment '自增主键'
        primary key,
    UUID          varchar(36)          not null comment '唯一键',
    Creator       varchar(36)          not null comment '首次创建者编号',
    CreateTime    datetime             not null comment '首次创建时间',
    Modifier      varchar(36)          not null comment '最后修改者编号',
    ModifyTime    datetime             not null comment '最后修改时间',

    TreeId        varchar(24)          not null comment '树形编号',
    ParentId      varchar(24)          not null comment '上级节点编号',
    Label         varchar(10)          not null comment '菜单名称',
    Title         varchar(20)          null comment '鼠标悬浮内容展示',
    MenuType      varchar(10)          not null comment '菜单类型',
    RouterPath    varchar(50)          null comment '路由地址',
    ComponentPath varchar(50)          null comment '组件路径',
    Visible       tinyint(1) default 0 not null comment '是否显示',
    Status        tinyint(1) default 0 not null comment '菜单状态',
    cache         tinyint(1) default 0 not null comment '页面是否缓存',
    ViewTab       tinyint(1) default 0 not null comment '多任务栏',
    Perms         varchar(20)          null comment '权限标识符',
    Icon          varchar(20)          null comment '菜单图标',
    Remark        varchar(20)          null comment '备注',
    LinkPath      varchar(100)         null comment '外链地址',
    LinkOpenType  varchar(10)          null comment '链接打开方式',
    Sort          int                  not null comment '排序',
    Query         varchar(100)         null comment '路由参数',
    constraint UUID
        unique (UUID)
)
    comment '系统菜单表';


