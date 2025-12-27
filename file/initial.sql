-- auto-generated definition
create table System_Setting
(
    ID         bigint auto_increment comment '自增主键'
        primary key,
    UUID       varchar(36)          not null comment '唯一键',
    Creator    varchar(36)          not null comment '首次创建者编号',
    CreateTime datetime             not null comment '首次创建时间',
    Modifier   varchar(36)          not null comment '最后修改者编号',
    ModifyTime datetime             not null comment '最后修改时间',
    `Key`      varchar(36)          not null comment '配置项唯一名称',
    `Desc`     varchar(100)         not null comment '配置项描述',
    Enabled    tinyint(1) default 0 not null comment '此配置项是否开启',
    Properties json                 null comment '详细配置信息',
    constraint UUID
        unique (UUID)
)
    comment '系统配置项表';

