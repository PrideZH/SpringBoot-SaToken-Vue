/*
SQLyog Community
MySQL - 8.0.18 : Database - rbac
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rbac` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

/*Table structure for table `article` */

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL COMMENT '文章内容',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1555442194164314114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `article` */

insert  into `article`(`id`,`content`,`create_time`,`update_time`) values (1555442194164314113,'test test test test test test','2022-08-05 14:34:52','2022-08-05 14:34:52');

/*Table structure for table `sys_permission` */

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (1,'用户添加','sys:user:add','2022-08-03 23:08:09',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (2,'用户修改','sys:user:put','2022-08-03 23:08:29',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (3,'用户查询','sys:user:get','2022-08-03 23:08:34',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (4,'用户删除','sys:user:del','2022-08-03 23:08:38',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (5,'角色添加','sys:role:add','2022-08-03 23:08:42',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (6,'角色修改','sys:role:put','2022-08-03 23:08:46',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (7,'角色查询','sys:role:get','2022-08-03 23:08:49',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (8,'角色删除','sys:role:del','2022-08-03 23:08:53',NULL);
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (9,'权限查询','sys:permission:get','2022-08-04 22:15:36','2022-08-04 22:15:38');
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (10,'文章添加','article:add','2022-08-05 12:37:17','2022-08-05 12:37:18');
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (11,'文章修改','article:put','2022-08-05 12:37:27','2022-08-05 12:37:27');
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (12,'文章查询','article:get','2022-08-05 12:37:31','2022-08-05 12:37:31');
insert  into `sys_permission`(`id`,`name`,`code`,`create_time`,`update_time`) values (13,'文章删除','article:del','2022-08-05 12:37:37','2022-08-05 12:37:37');

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`create_time`,`update_time`) values (1555200235071868929,'test','2022-08-04 22:33:25','2022-08-04 22:33:25');

/*Table structure for table `sys_role__permission` */

CREATE TABLE `sys_role__permission` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_role__permission` */

insert  into `sys_role__permission`(`id`,`role_id`,`permission_id`,`create_time`,`update_time`) values (1555200235327721473,1555200235071868929,3,'2022-08-04 22:33:25',NULL);
insert  into `sys_role__permission`(`id`,`role_id`,`permission_id`,`create_time`,`update_time`) values (1555200236619567106,1555200235071868929,7,'2022-08-04 22:33:25',NULL);

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `super_admin` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`nickname`,`super_admin`,`create_time`,`update_time`) values (1,'admin','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','admin',1,'2022-08-03 22:59:45',NULL);
insert  into `sys_user`(`id`,`username`,`password`,`nickname`,`super_admin`,`create_time`,`update_time`) values (1555171511056326658,'test','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','test',0,'2022-08-04 20:39:17','2022-08-05 12:01:19');

/*Table structure for table `sys_user__role` */

CREATE TABLE `sys_user__role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user__role` */

insert  into `sys_user__role`(`id`,`user_id`,`role_id`,`create_time`,`update_time`) values (1555403552502845442,1555171511056326658,1555200235071868929,'2022-08-05 12:01:20',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
