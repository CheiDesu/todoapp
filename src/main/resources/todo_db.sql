/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : todo_db

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2022-05-25 11:04:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `normal_todo_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

-- ----------------------------
-- Records of events
-- ----------------------------
INSERT INTO `events` VALUES ('1', 'test_events', '1', '2022-05-24 21:16:56', '2022-05-24 21:16:30');
INSERT INTO `events` VALUES ('2', 'test_events', '2', '2022-05-24 21:17:18', '2022-05-24 21:17:21');

-- ----------------------------
-- Table structure for normal_todo
-- ----------------------------
DROP TABLE IF EXISTS `normal_todo`;
CREATE TABLE `normal_todo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `important` int(11) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

-- ----------------------------
-- Records of normal_todo
-- ----------------------------
INSERT INTO `normal_todo` VALUES ('1', '你好使劲儿', '2022-05-24 21:18:43', '2022-05-24 20:25:14', '2', null);
INSERT INTO `normal_todo` VALUES ('2', '测试数据3', null, '2022-05-24 20:26:21', '3', null);
INSERT INTO `normal_todo` VALUES ('3', '测试数据2', null, '2022-05-24 20:26:26', '2', null);
INSERT INTO `normal_todo` VALUES ('4', '测试数据4', null, '2022-05-24 20:26:30', '4', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `password` varchar(45) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `permissions` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '3', null, null);
INSERT INTO `user` VALUES ('2', 'ddmanager', '123', '2', null, null);
INSERT INTO `user` VALUES ('3', 'guest', '123', '1', null, null);

-- ----------------------------
-- Table structure for workspace
-- ----------------------------
DROP TABLE IF EXISTS `workspace`;
CREATE TABLE `workspace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

-- ----------------------------
-- Records of workspace
-- ----------------------------
INSERT INTO `workspace` VALUES ('1', 'test_workspace', '1', '2', null, null);
INSERT INTO `workspace` VALUES ('2', 'test_workspace', '1', '1', null, null);
