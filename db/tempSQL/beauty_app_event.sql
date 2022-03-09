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
-- Table structure for table `app_event`
--

DROP TABLE IF EXISTS `app_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_event` (
  `e_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `e_type` int(11) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `stop_event` int(1) DEFAULT '0',
  `thumbnail` varchar(255) DEFAULT NULL,
  `t_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`e_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_event`
--

LOCK TABLES `app_event` WRITE;
/*!40000 ALTER TABLE `app_event` DISABLE KEYS */;
INSERT INTO `app_event` VALUES (30,'\n			              						\n			              						<p><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170426/event_083333094.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170426/event_003315137.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"coupon hLyiQhinZpQSmXrj\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170426/event_004404044.png\" style=\"width: 100%;\"></p>\n			              					\n			              					',5,'2017-04-26 00:37:14',14,0,'/beauty/event/20170426/thumb_003710195.png',0),(28,'<p><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170324/event_015426418.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170324/event_015433855.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"coupon mlFVFnLHnXZvEfhT\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170324/event_015528919.png\" style=\"width: 100%;\"><br></p>',5,'2017-03-13 20:25:24',3,1,'/beauty/event/20170324/thumb_015356817.png',0),(16,'\n			              						<p><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170303/event_122434319.png\" style=\"width: 100%;\"><a href=\"http://jamestomato.cafe24.com/calender_check.php\" target=\"_self\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170303/event_122100632.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"\" role=\"\"></a><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170303/event_122104402.png\" style=\"width: 100%;\"><br></p>\n			              					',5,'2017-02-23 14:36:17',9,1,'/beauty/event/20170303/thumb_122503265.png',0),(17,'\n			              						\n			              						\n			              						\n			              						\n			              						\n			              						\n			              						<p><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170223/event_155212327.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170223/event_155215722.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"mycoupon\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170223/event_155219805.png\" style=\"width: 100%;\"><br></p>\n			              					\n			              					\n			              					\n			              					\n			              					\n			              					\n			              					',5,'2017-02-23 14:37:51',1,0,'/beauty/event/20170414/thumb_214629463.png',0),(18,'\n			              						\n			              						\n			              						\n			              						\n			              						<p><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170502/event_164032831.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170502/event_164041811.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"mycoupon\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170502/event_164044464.png\" style=\"width: 100%;\"><br></p>\n			              					\n			              					\n			              					\n			              					\n			              					',5,'2017-02-23 14:41:01',2,0,'/beauty/event/20170502/thumb_164611043.png',0),(22,'\n			              						<p><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_093931165.png\" style=\"width: 100%;\"><a href=\"http://jamestomato.cafe24.com/calender_check.php\" target=\"_self\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_093934541.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"\" role=\"\"></a><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_093937636.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_093952924.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"coupon mlFVFnLHnXZvEfhT\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_093943304.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_094023236.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"coupon RoNvhfzYnhpTkjHC\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_094028947.png\" style=\"width: 100%;\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_094033537.png\" style=\"width: 100%;\" alt=\"\" title=\"\" class=\"coupon gMMcuefRFlPFqRoz\" role=\"\"><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170316/event_094039613.png\" style=\"width: 100%;\"><br></p>\n			              					',5,'2017-02-27 09:47:54',11,1,'/beauty/event/20170316/thumb_093919620.png',0),(29,'<p><img src=\"http://sumcrimage.cafe24.com/beauty/event/20170317/event_175011296.png\" style=\"width: 100%;\">\n			              					</p>',5,'2017-03-17 17:50:13',4,1,'/beauty/event/20170317/thumb_174949552.png',0);
/*!40000 ALTER TABLE `app_event` ENABLE KEYS */;
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
