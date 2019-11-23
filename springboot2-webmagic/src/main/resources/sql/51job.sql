create table if not exists `job_info`(
    id bigint(20) not null auto_increment comment '主键id',
    `company_name` varchar(100) default null comment '公司名称',
    `company_addr` varchar(200) default null comment '公司联系方式',
    `company_info` text comment '公司信息',
    `job_name` varchar(100) default null comment '职位名称',
    `job_addr` varchar(50) default null comment '工作地点',
    `job_info` text default null comment '职位信息',
    `salary_min` int(10) default null comment '薪资范围,最小',
    `salary_max` int(10) default null comment '薪资范围,最大',
    `salary` varchar(100) default null comment '薪资范围',
    `url` varchar(150) default null comment '招聘信息详情页',
    `time` varchar(30) default null comment '职位发布时间',
    primary key (`id`)
)engine=InnoDB default charset='utf8' comment='招聘信息' ;


