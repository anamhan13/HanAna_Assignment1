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
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
  `idplayer` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `address` varchar(60) DEFAULT NULL,
  `isadmin` bit(1) NOT NULL,
  PRIMARY KEY (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'Ana','anahan@gmail.com','Abc12345?','Teius',''),(2,'Anca','anchis@gmail.com','aBc12345%','Timisoara','\0'),(3,'Carina','cary@gmail.com','abCd12345!','Teius','\0'),(4,'Dana','dana@hotmail.com','aBC12345-','Aiud','\0'),(5,'Dan','dandan@yahoo.com','abcD12345&','Cluj-Napoca','\0'),(6,'Flaviu','flaviu_s@student.utcluj.ro','ABc12345*','Oradea','\0'),(10,'hfjakl','haj@shrgh.com','avbdnG62$','zahwhkjl','\0'),(11,'jhhk','hjwek@hjk.com','hgHH64@fg','hj','\0'),(12,'rjaenklk','hwej@hj.com','ewhhHH76$','fhgj','\0'),(14,'bvnbma','jhsj@hj.com','hjds56JJ@','hjkhb','\0'),(7,'Ligia','ligia@yahoo.com','abC12345=','Alba iulia','\0'),(8,'Lucian','luc1@yahoo.com','AbC12345!','Sebes','\0'),(9,'Raul','r_acu@gmail.com','Abcd12345*','Bistrita','\0'),(15,'ejhbj','shj@hjkd.com','12hdjKreh^','hfgj','\0');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-02 22:21:02
