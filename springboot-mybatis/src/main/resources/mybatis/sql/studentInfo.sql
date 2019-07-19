
CREATE TABLE student_info (
	id INTEGER NOT NULL AUTO_INCREMENT,
	stu_name VARCHAR(50) NOT NULL,
	age int ,
	birthday DATE,
	address VARCHAR(255),
	classes_id VARCHAR(30),
	PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into student_info (id,stu_name,age,birthday,address,classes_id) values
	(null,'张三丰',22,'2019-07-13','湖北武当山','1001');
