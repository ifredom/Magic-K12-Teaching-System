create table sys_user (
    id int primary key auto_increment comment '主键用户ID',
    username varchar(30) not null comment '用户名',
    password varchar(100) not null comment '密码',
    salt varchar(50) not null comment '盐加密',
    nickname varchar(30) comment '昵称',
    mobile varchar(11) comment '手机号',
    avatar varchar(512) comment '头像地址',
    sex tinyint comment '性别',
    age tinyint comment '年龄',
    email varchar(50) comment '邮箱',
    status tinyint comment '状态(0:正常,1:禁用)',
    remark varchar(500) comment '备注',

    dept_id bigint comment '部门ID',
    post_id varchar(255) comment '岗位编号数组',

    login_ip varchar(50) comment '登录IP',
    login_date datetime comment '最后登录时间',
    deleted bit NOT NULL DEFAULT 0 comment '是否删除',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '更新时间',
    creator varchar(64) comment '创建者',
    updater varchar(64) comment '更新者',

    tenant_id bigint comment '租户Id'

)