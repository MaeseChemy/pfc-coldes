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
  `private` tinyint(1) NOT NULL,
  `participationtype` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ownerIndex` (`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`id`,`roomname`,`roomdescription`,`owner`,`private`,`participationtype`) VALUES 
 (1,'Sala Publica','Sala Publica','jmbg',0,0),
 (2,'Mi Sala Privada','','jmbg',1,0),
 (3,'Sala publica anarquica','Todos a la vez :D','jmbg',0,1);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;


--
-- Definition of table `roomdesign`
--

DROP TABLE IF EXISTS `roomdesign`;
CREATE TABLE `roomdesign` (
  `id_room` int(10) unsigned NOT NULL,
  `designcontent` text NOT NULL,
  PRIMARY KEY (`id_room`),
  CONSTRAINT `FK_roomdesignIdRoom` FOREIGN KEY (`id_room`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roomdesign`
--

/*!40000 ALTER TABLE `roomdesign` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomdesign` ENABLE KEYS */;


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
 (1,'jmbg',0,0),
 (2,'jmbg',0,0),
 (3,'jmbg',0,0);
/*!40000 ALTER TABLE `roomuser` ENABLE KEYS */;


--
-- Definition of table `roomuserinvitation`
--

DROP TABLE IF EXISTS `roomuserinvitation`;
CREATE TABLE `roomuserinvitation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `id_room` int(10) unsigned NOT NULL,
  `rol` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_Username` (`username`),
  KEY `Index_IdRoom` (`id_room`),
  CONSTRAINT `FK_invroom` FOREIGN KEY (`id_room`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_invusername` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roomuserinvitation`
--

/*!40000 ALTER TABLE `roomuserinvitation` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomuserinvitation` ENABLE KEYS */;


--
-- Definition of table `roomuserpencil`
--

DROP TABLE IF EXISTS `roomuserpencil`;
CREATE TABLE `roomuserpencil` (
  `id_room` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `userrol` int(10) unsigned NOT NULL,
  `requesttime` datetime NOT NULL,
  `pencilowner` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_room`,`username`),
  KEY `FK_pencilUsername` (`username`),
  CONSTRAINT `FK_pencilIdRoom` FOREIGN KEY (`id_room`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_pencilUsername` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roomuserpencil`
--

/*!40000 ALTER TABLE `roomuserpencil` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomuserpencil` ENABLE KEYS */;


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
 ('ircc','3ec8e2b221402e786dfa6f0884a0e7f7be11c32a','Ivan','Redondo','Chisvert','ircc@coldes.es',0,1,1,NULL,NULL,NULL),
 ('jmbg','ef22313290bd9a2658eeb8de7b92043f2c3a8e82','Jose Miguel','Blanco','Garcia','jmbg@coldes.es',1,1,1,NULL,'2011-07-03 14:09:27','2011-07-03 14:11:47'),
 ('lipb','61d7c5a7d3231ee0ba3c73a239fd8d45ae6895ac','Luz Isabel','Pelaez','Baños','lipb@coldes.es',0,1,1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `userdesign`
--

DROP TABLE IF EXISTS `userdesign`;
CREATE TABLE `userdesign` (
  `username` varchar(45) NOT NULL,
  `designname` varchar(45) NOT NULL,
  `designcontent` text NOT NULL,
  PRIMARY KEY (`username`,`designname`) USING BTREE,
  CONSTRAINT `FK_userdesignUsername` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userdesign`
--

/*!40000 ALTER TABLE `userdesign` DISABLE KEYS */;
/*!40000 ALTER TABLE `userdesign` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
