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
-- Table structure for table `app_plan_group`
--

DROP TABLE IF EXISTS `app_plan_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_plan_group` (
  `pg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sort` int(11) DEFAULT NULL,
  `stop_group` int(1) DEFAULT '0',
  `thumbnail` varchar(255) DEFAULT NULL,
  `p_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`pg_id`),
  KEY `FKkq9as1qhlls9ped3acwux41d2` (`p_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_plan_group`
--

LOCK TABLES `app_plan_group` WRITE;
/*!40000 ALTER TABLE `app_plan_group` DISABLE KEYS */;
INSERT INTO `app_plan_group` VALUES (1,1,0,'/beauty/event/20170202/group_201100954.png',NULL),(2,2,0,'/beauty/event/20170202/group_223116120.png',NULL),(3,3,0,'/beauty/event/20170217/group_192056924.png',NULL),(4,4,0,'/beauty/event/20170307/group_101741683.png',NULL),(5,5,0,'/beauty/event/20170307/group_164034639.png',4),(6,5,0,'/beauty/event/20170307/group_122540191.png',NULL),(7,6,0,'/beauty/event/20170307/group_183938241.png',NULL),(9,8,0,'/beauty/event/20170307/group_164318197.png',NULL),(10,9,0,'/beauty/event/20170307/group_164234211.png',7),(11,10,1,'/beauty/event/20170310/group_215026054.png',NULL),(12,11,0,'/beauty/event/20170310/group_221306428.png',NULL),(13,12,0,'/beauty/event/20170404/group_150249244.png',NULL);
/*!40000 ALTER TABLE `app_plan_group` ENABLE KEYS */;
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
