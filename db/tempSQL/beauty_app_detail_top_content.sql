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
-- Table structure for table `app_detail_top_content`
--

DROP TABLE IF EXISTS `app_detail_top_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_detail_top_content` (
  `dtc_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_id` varchar(255) DEFAULT NULL,
  `ct_id` bigint(20) DEFAULT NULL,
  `top_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`dtc_id`),
  KEY `FKlmumcag8pw7wsq9qfk3uf403f` (`dt_id`)
) ENGINE=MyISAM AUTO_INCREMENT=210 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_detail_top_content`
--

LOCK TABLES `app_detail_top_content` WRITE;
/*!40000 ALTER TABLE `app_detail_top_content` DISABLE KEYS */;
INSERT INTO `app_detail_top_content` VALUES (56,'2',33,2),(55,'2',32,2),(54,'2',31,2),(53,'2',30,2),(52,'2',29,2),(51,'2',28,2),(50,'2',27,2),(49,'2',26,2),(48,'2',25,2),(47,'2',24,2),(46,'2',23,2),(45,'2',22,2),(44,'2',19,2),(43,'2',17,2),(42,'2',18,2),(41,'2',13,2),(40,'2',12,2),(39,'2',11,2),(38,'2',10,2),(57,'2',34,2),(58,'2',35,2),(59,'2',36,2),(60,'2',38,2),(61,'2',41,2),(62,'2',42,2),(63,'2',43,2),(64,'2',44,2),(65,'2',47,2),(66,'2',49,2),(67,'2',50,2),(68,'2',51,2),(69,'2',52,2),(70,'2',54,2),(71,'2',57,2),(72,'2',58,2),(73,'2',59,2),(74,'2',60,2),(75,'2',61,2),(76,'2',62,2),(77,'2',63,2),(78,'2',64,2),(79,'2',65,2),(80,'2',70,2),(81,'2',71,2),(82,'2',72,2),(83,'2',87,2),(84,'2',91,2),(85,'2',92,2),(86,'2',95,2),(87,'2',97,2),(88,'2',98,2),(89,'2',99,2),(90,'2',100,2),(91,'2',101,2),(92,'2',103,2),(93,'2',105,2),(94,'2',106,2),(95,'2',107,2),(96,'2',108,2),(97,'2',109,2),(98,'2',112,2),(99,'2',113,2),(100,'2',114,2),(101,'2',118,2),(102,'2',119,2),(103,'2',120,2),(104,'2',121,2),(105,'2',122,2),(106,'2',123,2),(107,'2',124,2),(108,'2',125,2),(109,'2',126,2),(110,'2',131,2),(111,'2',132,2),(112,'2',133,2),(113,'2',134,2),(114,'2',135,2),(115,'2',136,2),(116,'2',137,2),(117,'2',138,2),(118,'2',139,2),(119,'2',140,2),(120,'2',141,2),(121,'2',144,2),(122,'2',145,2),(123,'2',148,2),(124,'2',149,2),(125,'2',150,2),(126,'2',151,2),(127,'2',152,2),(128,'2',153,2),(130,'2',155,2),(131,'2',156,2),(132,'2',157,2),(133,'2',158,2),(134,'2',159,2),(135,'2',160,2),(136,'2',161,2),(137,'2',162,2),(138,'2',163,2),(139,'2',164,2),(140,'2',165,2),(141,'2',166,2),(143,'2',168,2),(144,'2',169,2),(145,'2',170,2),(146,'2',171,2),(147,'2',172,2),(148,'2',173,2),(149,'2',175,2),(150,'2',176,2),(151,'2',178,2),(152,'2',179,2),(153,'2',180,2),(154,'2',181,2),(155,'2',182,2),(156,'2',183,2),(157,'2',184,2),(158,'2',185,2),(159,'2',186,2),(160,'2',187,2),(161,'2',188,2),(162,'2',191,2),(163,'2',192,2),(164,'2',193,2),(165,'2',194,2),(166,'2',195,2),(167,'2',196,2),(168,'2',197,2),(169,'2',198,2),(170,'2',199,2),(171,'2',200,2),(172,'2',201,2),(173,'2',202,2),(174,'2',203,2),(175,'2',204,2),(176,'2',205,2),(177,'2',206,2),(178,'2',207,2),(179,'2',208,2),(180,'2',209,2),(181,'2',210,2),(182,'2',211,2),(183,'2',212,2),(184,'2',213,2),(185,'2',214,2),(186,'2',215,2),(187,'2',216,2),(188,'2',217,2),(189,'2',218,2),(190,'2',219,2),(192,'2',222,2),(193,'2',224,2),(194,'2',225,2),(196,'2',229,2),(197,'2',230,2),(198,'2',231,2),(199,'2',232,2),(200,'2',233,2),(201,'2',234,2),(202,'2',236,2),(203,'2',237,2),(204,'2',238,2),(205,'2',239,2),(206,'2',240,2),(207,'2',241,2),(208,'2',242,2),(209,'2',243,2);
/*!40000 ALTER TABLE `app_detail_top_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:21
