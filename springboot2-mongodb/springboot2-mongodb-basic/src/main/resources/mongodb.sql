--创建数据库
use chengxp;
--创建集合
db.createCollection("book");

--插入数据
db.book.insertMany([
    {"price": 23.8, "name":"红楼梦", "info":"曹雪芹", "publish":"工业出版社", "createTime":"2020-10-25 10:12:12", "updateTime":"2020-10-25 10:12:12"},
    {"price": 27.8, "name":"西游记", "info":"吴承恩", "publish":"工业出版社", "createTime":"2020-10-25 10:12:12", "updateTime":"2020-10-25 10:12:12"}
]);