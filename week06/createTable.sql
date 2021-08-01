-- 2、（必做）：基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的
-- SQL文件到Github（后面2周的作业依然要是用到这个表结构）。


--## 创建数据库
create database `shops` default character set utf8mb4 collate utf8mb4_general_ci;

drop table `users`

--##用户表
create table if not exists `users`(
`id` int(11) not null auto_increment,
`name` varchar(255) not null comment '用户名',
`password` varchar(255) not null comment '密码',
`nick_name` varchar(255) not null comment '昵称',
`identify_card` varchar(18) not null comment '身份证',
PRIMARY KEY(`id`)
)engine=innodb AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


--##供应商 拆解商品表
create table if not exists `supplier`(
`id` int(11) not null auto_increment,
`name` varchar(255) not null comment '名称',
`registration_number` varchar(255) not null comment '注册号',
`contactPerson` varchar(255) not null comment '联系人',
PRIMARY KEY(`id`)
)engine=innodb AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



--##商品
create table if not exists `goods`(
`id` int(11) not null auto_increment,
`name` varchar(255) not null comment '名称',
`classify` varchar(255) not null comment '分类',
`weight` double not null comment '重量',
`supplier_id` int(11) not null comment '供应商编码',
PRIMARY KEY(`id`),
 foreign key (supplier_id) references supplier(id)
)engine=innodb AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



--##订单
create table if not exists `orders`(
`id` int(11) not null auto_increment,
`user_id` int(11) not null comment '用户ID',
`money` double not null comment '金额',
`state` int(11) not null comment '状态 存储枚举',
`goods_id` int(11) not null comment '商品编码',
PRIMARY KEY(`id`),
foreign key (user_id) references users(id),
foreign key (goods_id) references goods(id)
)engine=innodb auto_increment=1 DEFAULT charset=utf8mb4;

