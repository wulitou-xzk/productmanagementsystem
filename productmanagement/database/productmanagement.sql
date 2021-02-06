/*
Navicat MySQL Data Transfer

Source Server         : epoint-mysql
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : productmanagement

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-06-11 15:43:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for depotitem
-- ----------------------------
DROP TABLE IF EXISTS `depotitem`;
CREATE TABLE `depotitem` (
  `itemid` varchar(50) NOT NULL,
  `depotid` varchar(50) NOT NULL,
  `pdid` varchar(50) NOT NULL,
  `depotdate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `depotremain` int(5) NOT NULL DEFAULT '0',
  `adddate` datetime NOT NULL,
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品补给订单，由货物补给员添加，管理员与货物补给员可对其进行增删改查操作';

-- ----------------------------
-- Records of depotitem
-- ----------------------------
INSERT INTO `depotitem` VALUES ('item2020000001', 'depot2020001', 'pd2020000001', '2020-01-16 00:00:00', '50', '2020-01-17 00:00:00');
INSERT INTO `depotitem` VALUES ('item20200216120451cdae591f', 'depot2020001', 'pd2020000006', '2020-02-19 00:00:00', '50', '2020-02-16 00:00:00');
INSERT INTO `depotitem` VALUES ('item202004111406374694', 'admin2020001', 'pd20200110dfsdfs', '2020-04-10 00:00:00', '60', '2020-04-11 00:00:00');
INSERT INTO `depotitem` VALUES ('item202004112015064bdd', 'admin2020001', 'pd2020000006', '2020-04-11 00:00:00', '30', '2020-04-11 00:00:00');
INSERT INTO `depotitem` VALUES ('item202004112158414583', 'depot2020002', 'pd2020000006', '2020-05-10 00:00:00', '60', '2020-04-11 00:00:00');

-- ----------------------------
-- Table structure for logindetail
-- ----------------------------
DROP TABLE IF EXISTS `logindetail`;
CREATE TABLE `logindetail` (
  `detailid` varchar(50) NOT NULL,
  `uid` varchar(50) NOT NULL,
  `currentlogin` datetime NOT NULL COMMENT '登录时间',
  `exitlogin` datetime DEFAULT NULL COMMENT '退出登录时间',
  PRIMARY KEY (`detailid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记录登录详情';

-- ----------------------------
-- Records of logindetail
-- ----------------------------
INSERT INTO `logindetail` VALUES ('detail20200401205455b278', 'depot2020001', '2020-04-01 20:54:55', '2020-04-01 21:59:21');
INSERT INTO `logindetail` VALUES ('detail20200401230457ae75', 'admin2020002', '2020-04-01 23:04:57', '2020-04-01 23:05:21');
INSERT INTO `logindetail` VALUES ('detail2020040313303294a8', 'admin2020002', '2020-04-03 13:30:32', '2020-04-03 13:30:43');
INSERT INTO `logindetail` VALUES ('detail20200403202852aa7f', 'admin2020002', '2020-04-03 20:28:52', '2020-04-03 20:39:56');
INSERT INTO `logindetail` VALUES ('detail202004101939409626', 'admin2020003', '2020-04-10 19:39:40', '2020-04-10 19:43:38');
INSERT INTO `logindetail` VALUES ('detail20200411132802b349', 'depot2020001', '2020-04-11 13:28:02', '2020-04-11 13:28:44');
INSERT INTO `logindetail` VALUES ('detail202004111356088aee', 'depot2020001', '2020-04-11 13:56:08', '2020-04-11 13:59:04');
INSERT INTO `logindetail` VALUES ('detail202004111409218f20', 'depot2020001', '2020-04-11 14:09:21', '2020-04-11 14:09:39');
INSERT INTO `logindetail` VALUES ('detail2020041114174891cf', 'depot2020001', '2020-04-11 14:17:48', '2020-04-11 14:24:17');
INSERT INTO `logindetail` VALUES ('detail20200411201622b58c', 'inventory2020002', '2020-04-11 20:16:34', '2020-04-11 20:16:58');
INSERT INTO `logindetail` VALUES ('detail20200411203521bace', 'admin2020003', '2020-04-11 20:35:21', '2020-04-11 20:49:03');
INSERT INTO `logindetail` VALUES ('detail20200411204938afc1', 'admin2020003', '2020-04-11 20:49:38', '2020-04-11 20:51:36');
INSERT INTO `logindetail` VALUES ('detail20200411215833915a', 'depot2020002', '2020-04-11 21:58:33', '2020-04-11 21:59:17');
INSERT INTO `logindetail` VALUES ('detail20200501212943b8fa', 'admin2020001', '2020-05-01 21:29:43', '2020-05-01 21:32:50');
INSERT INTO `logindetail` VALUES ('detail202005100953199bae', 'admin2020001', '2020-05-10 09:53:20', '2020-05-10 10:02:41');
INSERT INTO `logindetail` VALUES ('detail202005101136039c8c', 'admin2020001', '2020-05-10 11:36:03', '2020-05-10 11:57:15');
INSERT INTO `logindetail` VALUES ('detail202005101147088f20', 'admin2020002', '2020-05-10 11:47:08', '2020-05-10 11:52:07');
INSERT INTO `logindetail` VALUES ('detail2020051011491997b4', 'depot2020001', '2020-05-10 11:49:19', '2020-05-10 11:50:18');
INSERT INTO `logindetail` VALUES ('detail202005101159139ca5', 'depot2020001', '2020-05-10 11:59:13', '2020-05-10 11:59:24');
INSERT INTO `logindetail` VALUES ('detail202005101159308b44', 'admin2020002', '2020-05-10 11:59:30', '2020-05-10 11:59:40');
INSERT INTO `logindetail` VALUES ('detail202005201539088923', 'admin2020001', '2020-05-20 15:39:09', '2020-05-20 15:47:45');
INSERT INTO `logindetail` VALUES ('detail20200520160611beef', 'depot2020001', '2020-05-20 16:06:11', '2020-05-20 16:09:51');
INSERT INTO `logindetail` VALUES ('detail20200520161150b20e', 'admin2020001', '2020-05-20 16:11:50', '2020-05-20 16:20:28');
INSERT INTO `logindetail` VALUES ('detail20200520161154b406', 'admin2020002', '2020-05-20 16:11:54', '2020-05-20 16:14:36');
INSERT INTO `logindetail` VALUES ('detail202005231844589c31', 'admin2020001', '2020-05-23 18:44:59', '2020-05-23 18:46:28');
INSERT INTO `logindetail` VALUES ('detail20200523184634a6bb', 'depot2020001', '2020-05-23 18:46:34', '2020-05-23 18:46:47');
INSERT INTO `logindetail` VALUES ('detail202005231846578c89', 'admin2020002', '2020-05-23 18:47:21', '2020-05-23 18:47:36');
INSERT INTO `logindetail` VALUES ('detail20200523184742bf62', 'inventory2020002', '2020-05-23 18:47:42', '2020-05-23 18:53:13');
INSERT INTO `logindetail` VALUES ('detail20200523185321b484', 'depot2020001', '2020-05-23 18:53:21', '2020-05-23 18:53:36');
INSERT INTO `logindetail` VALUES ('detail202005231853429bdf', 'admin2020001', '2020-05-23 18:53:42', '2020-05-24 00:30:00');
INSERT INTO `logindetail` VALUES ('detail20200524003000bff2', 'admin2020001', '2020-05-24 00:30:00', '2020-05-24 01:32:29');
INSERT INTO `logindetail` VALUES ('detail20200524003220b9bc', 'admin2020002', '2020-05-24 00:32:20', '2020-05-24 01:31:38');
INSERT INTO `logindetail` VALUES ('detail202005272106199c31', 'admin2020001', '2020-05-27 21:06:20', '2020-05-27 21:28:36');
INSERT INTO `logindetail` VALUES ('detail202005272123008f67', 'depot2020001', '2020-05-27 21:23:00', '2020-05-27 21:23:55');
INSERT INTO `logindetail` VALUES ('detail202005272127518465', 'depot2020001', '2020-05-27 21:27:51', '2020-05-27 21:30:08');
INSERT INTO `logindetail` VALUES ('detail20200527212836ad72', 'admin2020001', '2020-05-27 21:28:36', '2020-05-27 21:28:56');
INSERT INTO `logindetail` VALUES ('detail20200527212856afbd', 'admin2020001', '2020-05-27 21:28:56', '2020-05-27 22:05:49');
INSERT INTO `logindetail` VALUES ('detail20200527213008a869', 'depot2020001', '2020-05-27 21:30:08', '2020-05-27 21:30:24');
INSERT INTO `logindetail` VALUES ('detail2020052721311196e7', 'depot2020001', '2020-05-27 21:31:11', '2020-05-27 21:32:44');
INSERT INTO `logindetail` VALUES ('detail202005272131509cf3', 'inventory2020002', '2020-05-27 21:31:50', '2020-05-27 21:34:44');
INSERT INTO `logindetail` VALUES ('detail20200527213244af9c', 'depot2020001', '2020-05-27 21:32:44', '2020-05-27 21:33:17');
INSERT INTO `logindetail` VALUES ('detail20200527213317b654', 'depot2020001', '2020-05-27 21:33:18', '2020-05-27 21:33:31');
INSERT INTO `logindetail` VALUES ('detail20200527213331814c', 'depot2020001', '2020-05-27 21:33:32', '2020-05-27 21:34:48');
INSERT INTO `logindetail` VALUES ('detail20200527213543ab0f', 'depot2020001', '2020-05-27 21:35:43', '2020-05-27 21:35:59');
INSERT INTO `logindetail` VALUES ('detail20200527220601acd2', 'admin2020001', '2020-05-27 22:06:01', '2020-05-27 22:06:06');
INSERT INTO `logindetail` VALUES ('detail202005272206198749', 'admin2020001', '2020-05-27 22:06:19', '2020-05-27 22:06:26');
INSERT INTO `logindetail` VALUES ('detail20200530082010be0d', 'admin2020001', '2020-05-30 08:20:10', '2020-05-30 08:31:52');
INSERT INTO `logindetail` VALUES ('detail202005300955028330', 'depot2020001', '2020-05-30 09:55:02', '2020-05-30 09:55:27');
INSERT INTO `logindetail` VALUES ('detail20200530095534b76f', 'inventory2020002', '2020-05-30 09:55:41', '2020-05-30 09:55:57');
INSERT INTO `logindetail` VALUES ('detail202005301126329130', 'admin2020001', '2020-05-30 11:26:32', null);

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `notifid` varchar(50) NOT NULL,
  `acceptuid` varchar(50) NOT NULL COMMENT '需要完成通知的员工编号',
  `pdid` varchar(50) NOT NULL COMMENT '通知对应的商品编号',
  `operation` varchar(255) NOT NULL COMMENT '通知内容',
  `notifidate` datetime NOT NULL COMMENT '通知的时间',
  `state` int(2) NOT NULL COMMENT '完成状态 0 / 1',
  `finishdate` datetime DEFAULT NULL COMMENT '完成时间',
  `senduid` varchar(50) NOT NULL,
  PRIMARY KEY (`notifid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='放管理员发送的通知，管理员可对其进行增删改查，货物清点员以及货物补给员只可以查看与自己相关的通知；';

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES ('noti20200001', 'depot2020001', 'pd2020000001', '补货s', '2020-01-16 00:00:00', '1', '2020-01-17 00:00:00', 'admin2020001');
INSERT INTO `notification` VALUES ('noti20200401193139482c', 'admin2020002', '', 'dsfdsfdsfdsf', '2020-04-01 00:00:00', '1', '2020-04-03 00:00:00', 'admin2020001');
INSERT INTO `notification` VALUES ('noti2020041121585846c7', 'admin2020001', '', 'ceshibug', '2020-04-11 00:00:00', '1', '2020-04-11 00:00:00', 'depot2020002');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pdid` varchar(50) NOT NULL,
  `pdname` varchar(50) NOT NULL,
  `price` double(4,0) NOT NULL,
  `producer` varchar(50) NOT NULL COMMENT '生产商',
  `producedate` datetime NOT NULL COMMENT '生产日期',
  `expirationdate` datetime NOT NULL COMMENT '过期时间',
  `remain` int(5) NOT NULL COMMENT '剩余数量',
  `remark` varchar(255) DEFAULT NULL,
  `adddate` datetime NOT NULL,
  PRIMARY KEY (`pdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于存储商品信息，管理员可对其进行增删改查操作，货物管理员可对其进行更新';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('pd2020000001', '冰红茶', '3', '不好吃', '2020-01-01 00:00:00', '2020-10-30 00:00:00', '125', '测试商品', '2020-01-17 00:00:00');
INSERT INTO `product` VALUES ('pd2020000002', '可口可乐', '4', '看看看看', '2019-12-20 00:00:00', '2020-12-20 00:00:00', '145', '商品测试', '2020-01-17 00:00:00');
INSERT INTO `product` VALUES ('pd2020000003', '赣南脐橙', '12', '赣南脐橙生产商', '2019-11-11 00:00:00', '2020-02-08 00:00:00', '76', '数量单位：箱；价格：12元/斤', '2020-01-17 00:00:00');
INSERT INTO `product` VALUES ('pd2020000006', '台湾槟榔02', '7', '槟榔生产公司', '2019-10-11 00:00:00', '2020-12-31 00:00:00', '86', '价格：7元/包', '2020-01-18 00:00:00');
INSERT INTO `product` VALUES ('pd2020000007', '钟情抽纸', '5', '成都市阿尔纸业有限公司', '2019-12-25 00:00:00', '2022-12-25 00:00:00', '66', '数量单位：箱；价格：5元/包', '2020-03-27 00:00:00');
INSERT INTO `product` VALUES ('pd20200110dfsdfs', 'test_product', '3', 'test_producter', '2019-12-12 00:00:00', '2020-12-12 00:00:00', '120', 'dfsdfsdfd', '2020-04-10 00:00:00');
INSERT INTO `product` VALUES ('pd20200110dfsrtt', 'test2_product', '5', 'tes2t_producter', '2020-01-23 00:00:00', '2022-12-23 00:00:00', '100', 'sdfdsfds', '2020-04-15 00:00:00');
INSERT INTO `product` VALUES ('pd2020040148cf', '火鸡面', '5', '火鸡面生产公司', '2019-12-09 00:00:00', '2020-04-10 00:00:00', '75', '单位：箱', '2020-04-01 00:00:00');
INSERT INTO `product` VALUES ('pd20200410479e', 'sdfsdf', '3', 'dsfdsfds', '2019-12-13 00:00:00', '2020-12-13 00:00:00', '56', 'sdsa', '2020-04-10 00:00:00');
INSERT INTO `product` VALUES ('pd202004104813', 'dsdsfds', '5', 'sdvf', '2018-12-25 00:00:00', '2021-12-25 00:00:00', '117', 'sddsf', '2020-04-10 00:00:00');
INSERT INTO `product` VALUES ('pd202004294f4d', '可口可乐', '3', '可口可乐生产公司', '2020-04-29 00:00:00', '2020-04-29 00:00:00', '23', '单价：3元/瓶；数量：23箱（说明：本文件中的数据仅用于示范，导入系统前需将本行数据删除，并根据格式添加新的数据，否则将无法添加）', '2020-04-29 00:00:00');
INSERT INTO `product` VALUES ('pd20200530441d', '测试', '3', '的速度', '2020-05-30 00:00:00', '2020-06-06 00:00:00', '40', '小程序', '2020-05-30 00:00:00');

-- ----------------------------
-- Table structure for turnover
-- ----------------------------
DROP TABLE IF EXISTS `turnover`;
CREATE TABLE `turnover` (
  `turnid` varchar(50) NOT NULL,
  `uid` varchar(50) NOT NULL,
  `pdid` varchar(50) NOT NULL COMMENT '售出商品的编号',
  `saledate` datetime NOT NULL COMMENT '商品售出日期',
  `saleremain` int(11) NOT NULL COMMENT '商品售出数量',
  `salemoney` double NOT NULL COMMENT '售出商品的盈利额',
  PRIMARY KEY (`turnid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存放每日的营业额，仅管理员可查看';

-- ----------------------------
-- Records of turnover
-- ----------------------------
INSERT INTO `turnover` VALUES ('turn20200403201137071de1b5', 'admin2020001', 'pd2020040148cf', '2020-04-03 00:00:00', '5', '25');
INSERT INTO `turnover` VALUES ('turn20200403201544e125b9cc', 'admin2020001', 'pd2020000003', '2020-04-03 00:00:00', '8', '96');
INSERT INTO `turnover` VALUES ('turn20200403202002cc5f02d3', '员工已离职', 'pd2020000007', '2020-04-03 00:00:00', '4', '20');
INSERT INTO `turnover` VALUES ('turn20200411201640572b9fd2', 'admin2020001', 'pd202004104813', '2020-04-11 00:00:00', '3', '15');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL DEFAULT '11111',
  `userlevel` int(2) NOT NULL COMMENT '0 / 1 / 2 ：补给员、清点员、管理员',
  `adddate` datetime NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `phone` varchar(20) NOT NULL COMMENT '电话号码',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于存储登录账号';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin2020001', 'admin', '5e6r5N6Z5h6}', '2', '2020-01-17 12:41:12', '管理员', '18279689302');
INSERT INTO `user` VALUES ('admin2020002', 'admin_testfd', '5J5V5^5H5L', '2', '2020-03-27 00:00:00', '测试专用', '18279689635');
INSERT INTO `user` VALUES ('admin2020003', 'execl_test', '5Z575F5[5z', '2', '2020-04-10 00:00:00', 'test', '18279689356');
INSERT INTO `user` VALUES ('admin2020004', 'execl_test', '5h5W5G5;5t', '2', '2020-04-10 00:00:00', 'test', '18279689356');
INSERT INTO `user` VALUES ('admin2020005', 'cd', '5H5S5v5k5T', '2', '2020-01-23 00:00:00', 'etd', '18279689356');
INSERT INTO `user` VALUES ('depot2020001', 'depot修改测试', '5p5e5H5|5S', '0', '2020-01-17 00:00:00', '补给员工测试001', '18877056985');
INSERT INTO `user` VALUES ('depot2020002', 'depot', '5~5Q5s5i5Z', '0', '2020-02-03 00:00:00', 'iuhui', '18279689306');
INSERT INTO `user` VALUES ('depot2020003', 'test_depot', '5x5[555q59', '0', '2020-03-27 00:00:00', '补给测试', '18326536998');
INSERT INTO `user` VALUES ('inventory2020002', 'inven002', '5Y5{5g5r5~', '1', '2020-01-31 00:00:00', '清点员2号', '13133575689');
INSERT INTO `user` VALUES ('inventory2020003', 'test', '5X5q5h5h5T', '1', '2020-03-31 00:00:00', 'sdfsfddssadasdsad', '18265656302');
