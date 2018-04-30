CREATE DATABASE  IF NOT EXISTS `assignment2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `assignment2`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: assignment2
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tournaments`
--

DROP TABLE IF EXISTS `tournaments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournaments` (
  `idTournament` bigint(20) NOT NULL AUTO_INCREMENT,
  `dateFinish` datetime DEFAULT NULL,
  `dateStart` datetime DEFAULT NULL,
  `entryFee` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `paid` bit(1) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `prize` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `winner` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTournament`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tournaments`
--

LOCK TABLES `tournaments` WRITE;
/*!40000 ALTER TABLE `tournaments` DISABLE KEYS */;
INSERT INTO `tournaments` VALUES (1,'2018-05-22 18:42:44','2018-04-22 18:42:44',0,'RomCup','\0','Bucharest',0,'Upcoming',-1),(2,'2018-03-10 00:00:00','2018-04-11 00:00:00',10,'WorldCup','','London',10,'Ongoing',-1),(3,'2018-06-12 00:00:00','2018-07-13 00:00:00',20,'AussieCup','','Sydney',40,'Upcoming',-1),(5,'2018-10-08 00:00:00','2018-09-11 00:00:00',15,'UBBCup','','Cluj',15,'Upcoming',-1);
/*!40000 ALTER TABLE `tournaments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-30 13:33:08
