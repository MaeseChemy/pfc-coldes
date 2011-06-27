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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`id`,`roomname`,`roomdescription`,`owner`) VALUES 
 (1,'Nueva Sala','Sala nueva de prueba\r','chemy'),
 (2,'Otra sala','Otra descripcion\r','chemy'),
 (3,'asdfadfa','asdasadfas\rdfasd\rfasdf\ras\rdf\rasdf\rasdfasddf','chemy');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;


--
-- Definition of table `roomuser`
--

DROP TABLE IF EXISTS `roomuser`;
CREATE TABLE `roomuser` (
  `id_room` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `rol` int(10) unsigned NOT NULL,
  `online` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_room`,`username`),
  KEY `RoomId` (`id_room`),
  KEY `UsernameId` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roomuser`
--

/*!40000 ALTER TABLE `roomuser` DISABLE KEYS */;
INSERT INTO `roomuser` (`id_room`,`username`,`rol`,`online`) VALUES 
 (1,'chemy',0,1),
 (1,'lucy',2,1),
 (2,'chemy',0,0),
 (2,'lucy',2,0),
 (3,'chemy',0,0);
/*!40000 ALTER TABLE `roomuser` ENABLE KEYS */;


--
-- Definition of table `securitylog`
--

DROP TABLE IF EXISTS `securitylog`;
CREATE TABLE `securitylog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `username` varchar(45) NOT NULL,
  `accessType` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_Username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `securitylog`
--

/*!40000 ALTER TABLE `securitylog` DISABLE KEYS */;
INSERT INTO `securitylog` (`id`,`date`,`username`,`accessType`) VALUES 
 (1,'2011-06-25 13:45:27','chemy','ENTRADA'),
 (2,'2011-06-25 13:47:09','chemy','ENTRADA'),
 (3,'2011-06-26 01:46:27','chemy','ENTRADA'),
 (4,'2011-06-26 01:53:23','chemy','ENTRADA'),
 (5,'2011-06-26 01:56:21','chemy','ENTRADA'),
 (6,'2011-06-26 01:56:26','chemy','SALIDA'),
 (7,'2011-06-26 01:56:35','chemy','ENTRADA'),
 (8,'2011-06-26 02:01:11','chemy','ENTRADA'),
 (9,'2011-06-26 02:01:42','chemy','SALIDA'),
 (10,'2011-06-26 02:01:50','chemy','ENTRADA'),
 (11,'2011-06-26 02:01:55','chemy','SALIDA'),
 (12,'2011-06-26 02:01:58','chemy','ENTRADA'),
 (13,'2011-06-26 02:02:19','chemy','SALIDA');
/*!40000 ALTER TABLE `securitylog` ENABLE KEYS */;


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
 ('chemy','36483a997c6c7dca97c7f941675256f646074c63','Jose Miguel','Blanco','Garcia','chemy@coldes.es',1,1,1,'ID-ChemySobremesa-58666-1309213307976-0-0','2011-06-28 00:21:48','2011-06-28 00:21:55'),
 ('lucy','474e97d07b83ea9b34d1ec399840354182f3b6c1','Luz Isabel','Pelaez','Baños','lucy@coldes.es',1,1,1,'ID-ChemySobremesa-58666-1309213307976-0-1','2011-06-28 00:22:48','2011-06-28 00:22:52');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
