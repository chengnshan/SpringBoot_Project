create table if not exists user_info(
  id INTEGER NOT NULL AUTO_INCREMENT,
  user_name varchar(50) not null,
  pass_word varchar(30),
  user_sex varchar(2),
  nick_name varchar(50),
  birthday date ,
  jobs varchar(50),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into user_info (id,user_name,pass_word,user_sex,nick_name,birthday,jobs) values
(null,'孟开','123456','男','孟子','2017-09-08','丰修技术总监');
