create table if not exists user_role_info(
  id INTEGER NOT NULL AUTO_INCREMENT,
  role_id varchar(30) not null,
  user_name varchar(50),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into user_role_info (id,role_id,user_name) values (null,'IT001','孟开');
