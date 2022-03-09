-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 192.168.0.40    Database: beauty
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.9-MariaDB

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
-- Table structure for table `app_first_banner`
--

DROP TABLE IF EXISTS `app_first_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_first_banner` (
  `fb_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_date` datetime DEFAULT NULL,
  `show_yn` char(1) DEFAULT 'N',
  `sort` int(3) DEFAULT '0',
  `thumbnail` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `show_type` int(1) DEFAULT '1',
  `sb_type` int(11) DEFAULT NULL,
  `t_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fb_id`),
  KEY `FKj1vt79206sj0rf80myf802tsk` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_first_banner`
--

LOCK TABLES `app_first_banner` WRITE;
/*!40000 ALTER TABLE `app_first_banner` DISABLE KEYS */;
INSERT INTO `app_first_banner` VALUES (17,'2017-02-08 17:49:40','Y',1,'/beauty/main/first/20170208/thumb_174940562.png',36,3,2,36),(16,'2017-02-08 17:47:19','Y',3,'/beauty/main/first/20170208/thumb_174719431.png',50,2,2,50),(21,'2017-03-24 01:22:38','Y',1,'/beauty/event/20170426_004718619.png',NULL,1,4,30);
/*!40000 ALTER TABLE `app_first_banner` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:25
