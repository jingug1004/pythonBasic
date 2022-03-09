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
-- Table structure for table `app_payment_item`
--

DROP TABLE IF EXISTS `app_payment_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_payment_item` (
  `pi_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_id` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `coupon_discount` int(11) DEFAULT NULL,
  `coupon_id` varchar(255) DEFAULT NULL,
  `delivery_pay` int(11) DEFAULT NULL,
  `delivery_type` int(11) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `order_count` int(11) DEFAULT NULL,
  `payment` int(11) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  `delivery_number` varchar(255) DEFAULT NULL,
  `supply_price` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `prod_name` varchar(255) DEFAULT NULL,
  `delivery_code` varchar(255) DEFAULT NULL,
  `delivery_name` varchar(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `prod_confirm` int(1) DEFAULT '0',
  PRIMARY KEY (`pi_id`),
  KEY `FK1xxe0e4fktn72cqiok5xlnmte` (`item_id`),
  KEY `FK4u8ls33mqv9p3pebmfumfnx19` (`payment_id`),
  KEY `FKq5ytvog37pdqds01kfbb8gj95` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1274 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_payment_item`
--

LOCK TABLES `app_payment_item` WRITE;
/*!40000 ALTER TABLE `app_payment_item` DISABLE KEYS */;
INSERT INTO `app_payment_item` VALUES (1270,'20170523184637418',100,0,NULL,10,0,117,1,0,'2017-05-23 18:46:37','sum',NULL,0,'LIP_BLUE ','TEST LIP','01','우체국택배','ready',0),(1269,'20170523184524131',100,0,NULL,10,0,117,1,0,'2017-05-23 18:45:24','sum',NULL,0,'LIP_BLUE ','TEST LIP','01','우체국택배','ready',0),(1268,'20170523184234582',100,0,NULL,10,0,117,1,0,'2017-05-23 18:42:34','sum',NULL,0,'LIP_BLUE ','TEST LIP','01','우체국택배','ready',0),(1252,'20170518105831337',52000,0,NULL,0,1,12,1,0,'2017-05-18 10:58:31','puregen','933-7966-6870',28600,'E.G.F 인비보 리페어 세럼 30ml','E.G.F 인비보 리페어 세럼','06','로젠택배','paid',1),(1267,'20170523164514181',100,0,NULL,10,0,117,1,0,'2017-05-23 16:45:14','sum',NULL,0,'LIP_BLUE ','TEST LIP','01','우체국택배','paid',0),(1266,'20170523164514181',500,0,NULL,10,0,118,1,0,'2017-05-23 16:08:22','sum',NULL,0,'POWDER_Y','TEST LIP','01','우체국택배','ready',0),(1265,'20170523164514181',300,0,NULL,0,1,116,1,0,'2017-05-23 16:08:22','sum',NULL,0,'LIP_GREEN ','TEST LIP','01','우체국택배','ready',0),(1271,'20170524085912839',200,0,NULL,10,0,115,1,0,'2017-05-24 08:59:12','sum',NULL,10,'LIP_RED 01','TEST LIP','01','우체국택배','ready',0),(1272,'20170524105642272',500,0,NULL,10,0,118,1,0,'2017-05-24 10:56:42','sum',NULL,0,'POWDER_Y','TEST LIP','01','우체국택배','ready',0),(1273,'20170524114354932',12000,0,NULL,2500,0,62,1,0,'2017-05-24 11:43:54','thankyoufarmer',NULL,7500,'듀얼 프리미엄 코튼 패드 5개(400매)','듀얼 프리미엄 코튼 패드 5개(400매)','01','우체국택배','paid',0);
/*!40000 ALTER TABLE `app_payment_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:22
