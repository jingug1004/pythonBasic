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
-- Table structure for table `app_order_coupon`
--

DROP TABLE IF EXISTS `app_order_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_order_coupon` (
  `order_cp_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `max_dis` bigint(20) DEFAULT NULL,
  `ord_prod` bigint(20) DEFAULT NULL,
  `coupon_id` varchar(255) DEFAULT NULL,
  `order_id` varchar(100) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`order_cp_id`),
  KEY `FK17bggfl6x20kacacdvt023lb5` (`coupon_id`),
  KEY `FKclot6jpts77cl6w7frilj3gg3` (`order_id`),
  KEY `FK4v9ou2h3m0w07ijt2y503lr44` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_order_coupon`
--

LOCK TABLES `app_order_coupon` WRITE;
/*!40000 ALTER TABLE `app_order_coupon` DISABLE KEYS */;
INSERT INTO `app_order_coupon` VALUES (1,3000,0,'SWrEMBspZYjAJZVi','XkykuaPPMkpa2RS','01011111111'),(2,3000,0,'SWrEMBspZYjAJZVi','6yOWTMblZ1qR7eH','01011111111'),(3,3000,0,'SWrEMBspZYjAJZVi','fAaYDVydTciDdY5','01011111111'),(4,3000,0,'SWrEMBspZYjAJZVi','ulZnyQmepI4E3G5','01011111111'),(5,3000,0,'SWrEMBspZYjAJZVi','NzVVE1xhfyN2Qej','01011111111'),(6,3000,0,'SWrEMBspZYjAJZVi','4H8wVjHyIxVySJz','01011111111');
/*!40000 ALTER TABLE `app_order_coupon` ENABLE KEYS */;
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
