
--创建表空间
create  tablespace  chengxp  datafile  'C:/myfile/tablespace/chengxp.dbf' size  50M  autoextend  on  next  50M ;

--创建学生信息表
create table student_info (
    id_student_info varchar2(32) default sys_guid() primary key,
    stu_name varchar2(50) NOT NULL  ,
	age NUMBER ,
	birthday date ,
	address varchar2(200) default null,
	class_id varchar2(32) not null
) tablespace chengxp;

COMMENT ON TABLE student_info IS '学生信息表';

COMMENT ON COLUMN student_info.id_student_info IS '主键';
COMMENT ON COLUMN student_info.stu_name IS '姓名';
COMMENT ON COLUMN student_info.age IS '年龄';
COMMENT ON COLUMN student_info.birthday IS '生日';
COMMENT ON COLUMN student_info.address IS '住址';
COMMENT ON COLUMN student_info.class_id IS '班级表ID';


insert into student_info (stu_name, age, birthday,address,class_id) values
('李四',20,to_date('2020/6/20 23:27:21','yyyy-MM-dd HH24:mi:ss'),'广东省深圳市','CADB76CA672A426EA87E5E3165470DCD');

insert into student_info (stu_name, age, birthday,address,class_id) values
('张三',23,to_date('2020/6/18 23:27:21','yyyy-MM-dd HH24:mi:ss'),'湖北省武汉市','CFDB76CA672A426EA67E5E3165179DCD');



