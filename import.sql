/*
SQLyog Enterprise - MySQL GUI v8.14 
MySQL - 5.5.24-9-log : Database - activitystore
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`activitystore` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `activitystore`;

/*Table structure for table `block` */

DROP TABLE IF EXISTS `block`;

CREATE TABLE `block` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `parent` int(10) unsigned NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;

/*Data for the table `block` */

insert  into `block`(`id`,`name`,`parent`,`creation_date`,`update_date`) values (0,'root',0,'2012-07-05 12:31:16',NULL),(167,'год 2012',0,'2012-07-30 12:10:33',NULL),(168,'январь',167,'2012-07-30 12:10:41',NULL),(169,'1 день',168,'2012-07-30 12:10:53',NULL),(170,'2 день',168,'2012-07-30 12:19:08',NULL);

/*Table structure for table `card` */

DROP TABLE IF EXISTS `card`;

CREATE TABLE `card` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `block_id` int(10) unsigned DEFAULT NULL,
  `template_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(512) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `block_id` (`block_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1677 DEFAULT CHARSET=utf8;

/*Data for the table `card` */

insert  into `card`(`id`,`block_id`,`template_id`,`name`,`creation_date`,`update_date`) values (1668,169,NULL,'новый год','2012-09-04 14:24:37','2012-09-04 14:24:37'),(1669,NULL,NULL,'','2012-07-30 12:19:11',NULL),(1670,170,NULL,'123','2012-08-21 18:10:16','2012-08-21 18:10:16'),(1671,NULL,NULL,'','2012-08-21 18:10:09',NULL),(1672,NULL,NULL,'','2012-09-24 16:57:43',NULL),(1673,NULL,NULL,'','2012-09-24 18:06:27',NULL),(1674,NULL,NULL,'','2012-09-24 18:06:34',NULL),(1675,NULL,NULL,'','2012-09-24 18:07:35',NULL),(1676,NULL,NULL,'','2012-09-24 18:07:46',NULL);

/*Table structure for table `card_cell` */

DROP TABLE IF EXISTS `card_cell`;

CREATE TABLE `card_cell` (
  `card_id` int(10) unsigned NOT NULL,
  `cell_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`card_id`,`cell_id`),
  KEY `cell_id` (`cell_id`),
  CONSTRAINT `card_cell_ibfk_1` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `card_cell_ibfk_2` FOREIGN KEY (`cell_id`) REFERENCES `cell` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `card_cell` */

insert  into `card_cell`(`card_id`,`cell_id`) values (1668,12),(1670,12),(1674,12),(1675,12),(1668,13),(1670,13);

/*Table structure for table `card_cell_val` */

DROP TABLE IF EXISTS `card_cell_val`;

CREATE TABLE `card_cell_val` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cell_id` int(11) unsigned NOT NULL,
  `card_id` int(11) unsigned NOT NULL,
  `val` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `card_cell_val` */

insert  into `card_cell_val`(`id`,`cell_id`,`card_id`,`val`) values (1,12,1668,'1'),(2,13,1668,'2'),(3,12,1670,'3'),(4,13,1670,'4');

/*Table structure for table `cell` */

DROP TABLE IF EXISTS `cell`;

CREATE TABLE `cell` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(10) unsigned NOT NULL,
  `list_id` int(10) unsigned DEFAULT NULL,
  `val` varchar(512) DEFAULT NULL,
  `comment` varchar(200) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cell_to_list_fk` (`list_id`),
  CONSTRAINT `cell_to_list_fk` FOREIGN KEY (`list_id`) REFERENCES `vlist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `cell` */

insert  into `cell`(`id`,`type`,`list_id`,`val`,`comment`,`creation_date`,`update_date`) values (12,0,NULL,NULL,'кол-во народу','2012-07-30 12:11:11',NULL),(13,0,NULL,NULL,'пришло всего','2012-07-30 12:18:14',NULL);

/*Table structure for table `list_val` */

DROP TABLE IF EXISTS `list_val`;

CREATE TABLE `list_val` (
  `val_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `list_id` int(10) unsigned NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`val_id`),
  KEY `list_id` (`list_id`),
  CONSTRAINT `list_val_ibfk_1` FOREIGN KEY (`list_id`) REFERENCES `vlist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `list_val` */

/*Table structure for table `template` */

DROP TABLE IF EXISTS `template`;

CREATE TABLE `template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL,
  `card` int(10) unsigned NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `card` (`card`),
  CONSTRAINT `template_ibfk_1` FOREIGN KEY (`card`) REFERENCES `card` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `template` */

insert  into `template`(`id`,`name`,`card`,`creation_date`,`update_date`) values (1,'awd',1675,'2012-09-24 18:07:40',NULL);

/*Table structure for table `vlist` */

DROP TABLE IF EXISTS `vlist`;

CREATE TABLE `vlist` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vlist` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
