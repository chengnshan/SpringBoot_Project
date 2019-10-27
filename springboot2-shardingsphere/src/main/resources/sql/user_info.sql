
DROP TABLE IF EXISTS `user_info_0`;
CREATE TABLE `user_info_0` (
	`id` int(11) NOT NULL,
	`name` varchar(255) DEFAULT NULL,
	`age` int(11) DEFAULT NULL,
	`birthday` date DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- ----------------------------
-- Table structure for user_1 --
-- --------------------------
DROP TABLE IF EXISTS `user_info_1`;
CREATE TABLE `user_info_1` (
	`id` int(11) NOT NULL,
	`name` varchar(255) DEFAULT NULL,
	`age` int(11) DEFAULT NULL,
	`birthday` date DEFAULT NULL,
	PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;