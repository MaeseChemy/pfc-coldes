-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.30-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema coldesbd
--

CREATE DATABASE IF NOT EXISTS coldesbd;
USE coldesbd;

--
-- Definition of table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roomname` varchar(45) NOT NULL,
  `roomdescription` text NOT NULL,
  `owner` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_room` (`owner`),
  CONSTRAINT `FK_room` FOREIGN KEY (`owner`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`id`,`roomname`,`roomdescription`,`owner`) VALUES 
 (1,'Nueva Sala','Sala nueva yeah','chemy'),
 (2,'Otra salita','asdfasdfa','admin'),
 (3,'Diseño PFC','Diseño Proyecto','chemy');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;


--
-- Definition of table `roomuser`
--

DROP TABLE IF EXISTS `roomuser`;
CREATE TABLE `roomuser` (
  `id_room` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `rol` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_room`,`username`),
  KEY `FK_roomuser` (`username`),
  CONSTRAINT `FK_roomuser` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roomuser`
--

/*!40000 ALTER TABLE `roomuser` DISABLE KEYS */;
INSERT INTO `roomuser` (`id_room`,`username`,`rol`) VALUES 
 (1,'admin',2),
 (1,'chemy',0),
 (2,'admin',0),
 (2,'chemy',2),
 (3,'chemy',0);
/*!40000 ALTER TABLE `roomuser` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname1` varchar(45) NOT NULL,
  `surname2` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `designer` tinyint(1) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `sessionid` varchar(45) DEFAULT NULL,
  `lastlogin` datetime DEFAULT NULL,
  `lastOperation` datetime DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`username`,`password`,`name`,`surname1`,`surname2`,`email`,`admin`,`designer`,`active`,`sessionid`,`lastlogin`,`lastOperation`) VALUES 
 ('admin','d033e22ae348aeb5660fc2140aec35850c4da997','Administrador','Admin','Admin','admin@coldes.es',1,1,1,'ID-pcjmbg-54204-1309182495335-1-0','2011-06-27 15:49:14','2011-06-27 15:49:20'),
 ('chemy','36483a997c6c7dca97c7f941675256f646074c63','Jose Miguel','Blanco','Garcia','chemy@coldes.es',1,1,1,NULL,'2011-06-27 15:48:15','2011-06-27 15:49:27');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
