/*
SQLyog Enterprise - MySQL GUI v8.14 
MySQL - 5.1.62-1-log : Database - activitystore
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `block` */

insert  into `block`(`id`,`name`,`parent`,`creation_date`,`update_date`) values (0,'root',0,'2012-07-05 12:31:16',NULL);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `card` */

/*Table structure for table `card_cell` */

DROP TABLE IF EXISTS `card_cell`;

CREATE TABLE `card_cell` (
  `card_id` int(10) unsigned NOT NULL,
  `cell_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`card_id`,`cell_id`),
  KEY `FK_card_cell_to_cell` (`cell_id`),
  CONSTRAINT `FK_card_cell_to_card` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`),
  CONSTRAINT `FK_card_cell_to_cell` FOREIGN KEY (`cell_id`) REFERENCES `cell` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `card_cell` */

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cell` */

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `template` */

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
