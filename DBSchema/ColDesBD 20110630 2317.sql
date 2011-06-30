-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.52-community


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
  KEY `ownerIndex` (`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`id`,`roomname`,`roomdescription`,`owner`) VALUES 
 (3,'Diseño PFC','Diseño del proyecto','chemy'),
 (4,'Otra sala','asdfasdf','chemy');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;


--
-- Definition of table `roomuser`
--

DROP TABLE IF EXISTS `roomuser`;
CREATE TABLE `roomuser` (
  `id_room` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `rol` int(10) unsigned NOT NULL,
  `online` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_room`,`username`),
  KEY `RoomId` (`id_room`),
  KEY `UsernameId` (`username`),
  CONSTRAINT `FK_id_room` FOREIGN KEY (`id_room`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roomuser`
--

/*!40000 ALTER TABLE `roomuser` DISABLE KEYS */;
INSERT INTO `roomuser` (`id_room`,`username`,`rol`,`online`) VALUES 
 (3,'chemy',0,0),
 (3,'lucy',2,0),
 (4,'chemy',0,0);
/*!40000 ALTER TABLE `roomuser` ENABLE KEYS */;


--
-- Definition of table `roomuserinvitation`
--

DROP TABLE IF EXISTS `roomuserinvitation`;
CREATE TABLE `roomuserinvitation` (
  `id` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `id_room` int(10) unsigned NOT NULL,
  `guest` tinyint(1) NOT NULL,
  `colaborator` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_Username` (`username`),
  KEY `Index_IdRoom` (`id_room`),
  CONSTRAINT `FK_invusername` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `FK_idRoom` FOREIGN KEY (`id`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roomuserinvitation`
--

/*!40000 ALTER TABLE `roomuserinvitation` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomuserinvitation` ENABLE KEYS */;


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
  `lastoperation` datetime DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`username`,`password`,`name`,`surname1`,`surname2`,`email`,`admin`,`designer`,`active`,`sessionid`,`lastlogin`,`lastoperation`) VALUES 
 ('chemy','36483a997c6c7dca97c7f941675256f646074c63','Jose Miguel','Blanco','Garcia','chemy@coldes.es',1,1,1,NULL,'2011-06-30 23:16:07','2011-06-30 23:16:19'),
 ('lucy','474e97d07b83ea9b34d1ec399840354182f3b6c1','Luz Isabel','Pelaez','Baños','lucy@coldes.es',0,1,1,'ID-ChemySobremesa-53464-1309465467928-8-0','2011-06-30 22:58:01','2011-06-30 22:58:06');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
