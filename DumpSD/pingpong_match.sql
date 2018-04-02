CREATE DATABASE  IF NOT EXISTS `pingpong` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pingpong`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: pingpong
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
-- Table structure for table `match`
--

DROP TABLE IF EXISTS `match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `match` (
  `idmatch` int(11) NOT NULL AUTO_INCREMENT,
  `mail1` varchar(50) NOT NULL,
  `mail2` varchar(50) NOT NULL,
  `idtournament` int(11) NOT NULL,
  PRIMARY KEY (`idmatch`),
  KEY `mail1_idx` (`mail1`),
  KEY `idtournament_idx` (`idtournament`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match`
--

LOCK TABLES `match` WRITE;
/*!40000 ALTER TABLE `match` DISABLE KEYS */;
INSERT INTO `match` VALUES (1,'anchis@gmail.com','ligia@yahoo.com',1),(2,'cary@gmail.com','luc1@yahoo.com',1),(3,'dana@hotmail.com','flaviu_s@student.utcluj.ro',1),(4,'dandan@yahoo.com','r_acu@gmail.com',1),(5,'anchis@gmail.com','r_acu@gmail.com',1),(6,'cary@gmail.com','flaviu_s@student.utcluj.ro',1),(7,'dana@hotmail.com','ligia@yahoo.com',1),(8,'dandan@yahoo.com','luc1@yahoo.com',1),(9,'anchis@gmail.com','luc1@yahoo.com',3),(10,'cary@gmail.com','ligia@yahoo.com',3),(11,'dana@hotmail.com','r_acu@gmail.com',3),(12,'dandan@yahoo.com','flaviu_s@student.utcluj.ro',3);
/*!40000 ALTER TABLE `match` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-02 22:21:03
