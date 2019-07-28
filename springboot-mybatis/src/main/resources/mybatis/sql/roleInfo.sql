create table if not exists role_info(
  id INTEGER NOT NULL AUTO_INCREMENT,
  `role_id` varchar(30) not null,
  `role_name` varchar(50),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into role_info (id,`role_id`,`role_name`) values (null,'IT001','技术总监');
