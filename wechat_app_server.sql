/*
Navicat MySQL Data Transfer

Source Server         : 49.235.135.130
Source Server Version : 50728
Source Host           : 49.235.135.130:3306
Source Database       : wechat_app_server

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-07-27 13:34:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识',
  `area_code` varchar(255) DEFAULT NULL COMMENT '机构代码',
  `name` varchar(32) DEFAULT NULL COMMENT '组织区域全称',
  `remarks` varchar(255) DEFAULT NULL COMMENT '区域描述信息',
  `level` int(11) DEFAULT NULL COMMENT '区域等级 1-省级 2-市级 3-区县',
  `index_parent` varchar(255) DEFAULT NULL COMMENT '所有父级节点，以字符串形式保存 如 ‘1’，‘2’，‘11’',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级组织id',
  `create_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织区域信息表\r\n';

-- ----------------------------
-- Table structure for article_comments
-- ----------------------------
DROP TABLE IF EXISTS `article_comments`;
CREATE TABLE `article_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` int(11) DEFAULT NULL COMMENT '文章id',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `from_uid` int(11) DEFAULT NULL COMMENT '评论用户id',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COMMENT='文章评论';

-- ----------------------------
-- Table structure for article_info
-- ----------------------------
DROP TABLE IF EXISTS `article_info`;
CREATE TABLE `article_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `cover_image_url` varchar(255) DEFAULT NULL COMMENT '封面url',
  `audit_status` int(2) DEFAULT NULL COMMENT '文章审核状态',
  `parse_type` int(2) DEFAULT NULL COMMENT '解析类型  1.markdown 2.html ',
  `read_count` int(11) DEFAULT NULL COMMENT '浏览量，redis同步',
  `praise_count` int(11) DEFAULT NULL COMMENT ' 文章点赞数量，redis同步',
  `belongs_type` int(2) DEFAULT NULL COMMENT '所属类型 1.置顶 2.普通',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='文章信息';

-- ----------------------------
-- Table structure for base_user_info
-- ----------------------------
DROP TABLE IF EXISTS `base_user_info`;
CREATE TABLE `base_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nick_name` varchar(55) DEFAULT NULL COMMENT '昵称',
  `user_name` varchar(55) DEFAULT NULL COMMENT '用户名',
  `email` varchar(55) DEFAULT NULL COMMENT '邮箱',
  `pwd` varchar(55) DEFAULT NULL COMMENT '密码',
  `open_id` varchar(200) DEFAULT NULL COMMENT '小程序id',
  `gender` tinyint(2) DEFAULT NULL COMMENT '性别',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `country` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `province` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `city` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息';

-- ----------------------------
-- Table structure for comments_reply
-- ----------------------------
DROP TABLE IF EXISTS `comments_reply`;
CREATE TABLE `comments_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comments_id` int(11) DEFAULT NULL COMMENT '评论id',
  `reply_id` int(11) DEFAULT NULL COMMENT '回复目标id  对评论的回复 comments_id',
  `reply_type` varchar(50) DEFAULT NULL COMMENT '类型 comment 对评论的回复  reply对回复的回复 ',
  `content` varchar(255) DEFAULT NULL COMMENT '回复内容',
  `from_uid` int(11) DEFAULT NULL COMMENT '回复用户id',
  `to_uid` int(11) DEFAULT NULL COMMENT '目标用户id',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='评论回复';

-- ----------------------------
-- Table structure for community_feed_back
-- ----------------------------
DROP TABLE IF EXISTS `community_feed_back`;
CREATE TABLE `community_feed_back` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `details` varchar(255) DEFAULT NULL COMMENT '反馈详情',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `replay_content` varchar(255) DEFAULT NULL COMMENT '回复反馈详情',
  `replay` int(4) DEFAULT NULL COMMENT '是否恢复 0未回复 1已回复',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='社区反馈信息';

-- ----------------------------
-- Table structure for community_notice
-- ----------------------------
DROP TABLE IF EXISTS `community_notice`;
CREATE TABLE `community_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `details` varchar(255) DEFAULT NULL COMMENT '通告详情',
  `type` int(4) DEFAULT NULL COMMENT '通告类型 1.首页通告 2.社区通告 3.其他的通告',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='社区通告';

-- ----------------------------
-- Table structure for date_virtual
-- ----------------------------
DROP TABLE IF EXISTS `date_virtual`;
CREATE TABLE `date_virtual` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='虚拟时间表  为按天统计提供  查询7天则至少要有7条数据';

-- ----------------------------
-- Table structure for ext_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ext_user_info`;
CREATE TABLE `ext_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_score` int(8) DEFAULT NULL COMMENT '用户积分',
  `user_level` int(4) DEFAULT NULL COMMENT '用户等级',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户额外信息';

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id  管理员id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `admin_email` varchar(200) DEFAULT NULL COMMENT '管理员邮箱',
  `admin_wx_token` varchar(255) DEFAULT NULL COMMENT '微信官方api token 定时器定时生成,2小时过期',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统admin参数';

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件url',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `type` int(3) DEFAULT NULL COMMENT '0-文章图片 1-封面图片 2-其他',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='上传文件信息';
