CREATE TABLE classes_info (
	id INTEGER NOT NULL AUTO_INCREMENT,
	classes_id VARCHAR(30) NOT NULL,
	classes_name VARCHAR(50) NOT NULL,
	PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert  into classes_info (id,classes_id,classes_name) values (null,'1001','一年级一班');