-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for t_in_hospital_health  住院健康状况表
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
DROP TABLE IF EXISTS `t_in_hospital_health`;
CREATE TABLE `t_in_hospital_health` (
  `id` VARCHAR(36) NOT NULL,
  `in_hospital_no` varchar(50) null,-- 编号
  `temperature` varchar(50) null,-- 体温
  `weight` FLOAT DEFAULT 0 NOT NULL,-- 体重
  `rheme` text null,-- 临床检查
  `diagnosed` text null,-- 住院情况
  `doctor_advice` text null,-- 医生意见
  `remark` text null,-- 说明
  `create_user_id` varchar(36) not null,
  `create_date` timestamp not null default current_timestamp on update current_timestamp,
  `update_user_id` varchar(36) not null,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(32) NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;