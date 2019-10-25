drop table if exists `t_order_1`;
create table t_order_1 (
    `order_id` bigint(20) not null comment '订单id',
    `price` decimal(10,2) not null comment '订单价格',
    `user_id` bigint(20) not null comment '下单用户id',
    status varchar(50),
    primary key (`order_id`) using btree
)engine = InnoDB ;

drop table if exists `t_order_2`;
create table t_order_2 (
    `order_id` bigint(20) not null comment '订单id',
    `price` decimal(10,2) not null comment '订单价格',
    `user_id` bigint(20) not null comment '下单用户id',
    status varchar(50),
    primary key (`order_id`) using btree
)engine = InnoDB;


