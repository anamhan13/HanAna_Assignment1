CREATE DATABASE  IF NOT EXISTS `assignment3` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `assignment3`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: assignment3
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
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articles` (
  `idarticle` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL,
  `author` varchar(45) NOT NULL,
  `articleabstract` varchar(300) NOT NULL,
  `location` varchar(200) NOT NULL,
  PRIMARY KEY (`idarticle`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (4,'assadf','han@gmail.com','szrdtuu','D:\\univer\\Anul 3\\semestrul 2\\Software Design\\HanAna_Assignment3\\Assignment3\\src\\main\\resources\\articles\\assadf.json'),(7,'asdfgh','han@gmail.com','etrtuyu','D:\\univer\\Anul 3\\semestrul 2\\Software Design\\HanAna_Assignment3\\Assignment3\\src\\main\\resources\\articles\\asdfgh.json'),(9,'dsasfghj','novacean@gmail.com','sardu','D:\\univer\\Anul 3\\semestrul 2\\Software Design\\HanAna_Assignment3\\Assignment3\\src\\main\\resources\\articles\\dsasfghj.json'),(10,'dsgtdhf','novacean@gmail.com','srtyv','D:\\univer\\Anul 3\\semestrul 2\\Software Design\\HanAna_Assignment3\\Assignment3\\src\\main\\resources\\articles\\dsgtdhf.json'),(11,'labSD','han@gmail.com','stdyui','D:\\univer\\Anul 3\\semestrul 2\\Software Design\\HanAna_Assignment3\\Assignment3\\src\\main\\resources\\articles\\labSD.json');
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-14 22:57:00
