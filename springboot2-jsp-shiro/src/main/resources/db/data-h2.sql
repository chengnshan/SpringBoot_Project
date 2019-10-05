DELETE FROM user_info;

INSERT INTO user_info (id, user_name, password, salt, email) VALUES
(1, 'user1', 'c2b3befe498ece0ace7edd775fa94890', 'abcdefg', 'test1@baomidou.com'),
(2, 'user2', 'c2b3befe498ece0ace7edd775fa94890', 'abcdefg', 'test1@baomidou.com'),
(3, 'user3', '123456', 'abcdefg', 'test1@baomidou.com');