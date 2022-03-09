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
-- Table structure for table `app_plan`
--

DROP TABLE IF EXISTS `app_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_plan` (
  `p_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_date` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `stop_plan` int(1) DEFAULT '0',
  `thumbnail` varchar(255) DEFAULT NULL,
  `p_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`p_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_plan`
--

LOCK TABLES `app_plan` WRITE;
/*!40000 ALTER TABLE `app_plan` DISABLE KEYS */;
INSERT INTO `app_plan` VALUES (1,'2017-02-02 20:10:26',3,1,'/beauty/event/20170202/thumb_201026688.png','오늘의 발견'),(2,'2017-02-07 10:48:11',2,1,'/beauty/event/20170209/thumb_133344413.png','발그레 대박쿠폰'),(3,'2017-02-07 10:48:31',1,1,'/beauty/event/20170209/thumb_133353405.png','출석체크'),(4,'2017-02-07 23:13:12',6,0,'/beauty/event/20170307/thumb_161938650.png','MD초이스'),(5,'2017-02-27 13:36:47',7,1,'/beauty/event/20170307/thumb_153956596.png','응급 홈케어 특가'),(6,'2017-03-07 11:49:53',8,1,'/beauty/event/20170308/thumb_095054487.png','윤기 차르르한 코팅 보습랩'),(7,'2017-03-07 15:30:28',5,0,'/beauty/event/20170307/thumb_153028295.png','올인원 동안피부 위첼'),(8,'2017-03-07 16:30:57',9,0,'/beauty/event/20170308/thumb_095110825.png','야생피부 잠재우는 응급 홈케어'),(9,'2017-03-10 21:50:11',4,0,'/beauty/event/20170310/thumb_215012157.png','구매별 사은품 증정 땡큐파머'),(10,'2017-03-17 17:48:38',10,1,'/beauty/event/20170317/thumb_174838951.png','SNS 구매인증 이벤트');
/*!40000 ALTER TABLE `app_plan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:23
