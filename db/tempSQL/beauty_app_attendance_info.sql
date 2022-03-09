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
-- Table structure for table `app_attendance_info`
--

DROP TABLE IF EXISTS `app_attendance_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_attendance_info` (
  `info_id` bigint(20) NOT NULL,
  `detail_url` text,
  `reg_date` datetime DEFAULT NULL,
  `thumb_url` varchar(255) DEFAULT NULL,
  `color_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`info_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_attendance_info`
--

LOCK TABLES `app_attendance_info` WRITE;
/*!40000 ALTER TABLE `app_attendance_info` DISABLE KEYS */;
INSERT INTO `app_attendance_info` VALUES (201704,'<p><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_1.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_2.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 1\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_3.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_4.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 2\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_5.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_6.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 3\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_7.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_8.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 4\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_9.png\" style=\"width: 100%;\"><br></p>','2017-04-18 11:11:11','/beauty/attendance/201704/201704_0.png','#b8deff'),(201705,'<p><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_1.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_2.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 1\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_3.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_4.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 2\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_5.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_6.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 3\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_7.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_8.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"stamp 4\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/attendance/201704/201704_9.png\" style=\"width: 100%;\"><br></p>','2017-04-18 11:11:11','/beauty/attendance/201704/201704_0.png','#b8deff');
/*!40000 ALTER TABLE `app_attendance_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:18
