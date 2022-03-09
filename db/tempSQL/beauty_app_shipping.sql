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
-- Table structure for table `app_shipping`
--

DROP TABLE IF EXISTS `app_shipping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_shipping` (
  `ship_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `zipcode` varchar(255) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  `brand_visible` char(1) DEFAULT 'Y',
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ship_id`),
  KEY `FKr708nhoifiy4yx8cr7wpc9i76` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=873 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_shipping`
--

LOCK TABLES `app_shipping` WRITE;
/*!40000 ALTER TABLE `app_shipping` DISABLE KEYS */;
INSERT INTO `app_shipping` VALUES (793,'서울 금천구 가산디지털1로 149 (가산동, 신한이노플렉스)','Test','강민호','01086510628','2017-04-18 15:40:10','08506','01086510628','N',NULL),(794,'서울 금천구 가산디지털1로 149 (가산동, 신한이노플렉스)','Test','강민호','01086510628','2017-04-18 15:40:10','08506','01086510628','N',NULL),(795,'서울 금천구 가산디지털1로 149 (가산동, 신한이노플렉스)','Test','강민호','01086510628','2017-04-18 15:40:10','08506','01086510628','N',NULL),(796,'서울 강남구 남부순환로 2621 (도곡동, LG전자 강남R','hslsy1013@','현서영','01031245254','2017-04-19 21:27:39','06267','01031245254','Y',NULL),(797,'서울 강남구 남부순환로 2621 (도곡동, LG전자 강남R','hslsy1013@','현서영','01031245254','2017-04-19 21:36:48','undefined','01031245254','N',NULL),(798,'서울 강남구 남부순환로 2621 (도곡동, LG전자 강남R','hslsy1013@','현서영','01031245254','2017-04-19 21:46:32','undefined','01031245254','N',NULL),(799,'서울 강남구 남부순환로 2621 (도곡동, LG전자 강남R','hslsy1013@','현서영','01031245254','2017-04-19 21:47:02','undefined','01031245254','N',NULL),(800,'서울 강남구 남부순환로 2621 (도곡동, LG전자 강남R','hslsy1013@','현서영','01031245254','2017-04-19 21:48:28','undefined','01031245254','N',NULL),(844,'경기 동두천시 강변로 18 (송내동)','몰라요','강민호','01086510628','2017-05-02 17:32:14','11355','01086510628','Y',NULL),(846,'서울 강남구 남부순환로 2621 (도곡동, LG전자 강남R','hslsy1013@','현서영','01031245254','2017-05-02 20:06:10','undefined','01031245254','N',NULL),(847,'전남 장성군 장성읍 청운5길 24 (영천리)','전남 장성군 장성읍 청운5길 24','은아','01066866341','2017-05-04 21:11:01','57220','01066866341','Y',NULL),(868,'ㄹㄷㅈㄹㅈㄷ','ㄹㅈㄷㄹ','ㅇㅁㅈㅇㅁㅈㅇ','01012345678','2017-05-23 18:46:37','','01011111111','N',NULL),(867,'ㄹㄷㅈㄹㅈㄷ','ㄹㅈㄷㄹ','ㅇㅁㅈㅇㅁㅈㅇ','01012345678','2017-05-23 18:45:24','','01011111111','N',NULL),(856,'서울 은평구 응암동 587-28','B01호(반지하왼쪽집)','이동원','01066265990','2017-05-17 10:08:16','03484','01066265990','N',NULL),(866,'ㄹㄷㅈㄹ','ㅈㄷㄹ','ㄹㄷㅈㄹㅈㄹ',NULL,'2017-05-23 18:42:34','','01011111111','N',NULL),(858,'서울 은평구 응암동 587-28','B01호(지층왼쪽집)','이동원','01066265990','2017-05-18 10:58:11','03484','01066265990','N',NULL),(859,'서울 은평구 응암동 587-28','B01호(지층왼쪽집)','이동원','01066265990','2017-05-18 10:58:31','03484','01066265990','N',NULL),(869,'ㄹㄷㅈㄹㅈㄷ','ㄹㅈㄷㄹ','ㅇㅁㅈㅇㅁㅈㅇ','01012345678','2017-05-24 08:59:12','','01011111111','N',NULL),(870,'ㄹㄷㅈㄹㅈㄷ','ㄹㅈㄷㄹ','ㅇㅁㅈㅇㅁㅈㅇ','01012345678','2017-05-24 10:56:42','','01011111111','N',NULL),(871,'서울 성동구 성수일로4길 26 (성수동2가, 서울숲힐스테이트)','105-1405','임가희','01095391789','2017-05-24 11:43:55','04780','01095391789','N',NULL),(872,'서울 강남구 개포로 지하 522 (일원동)','','테스트1','01011111111','2017-05-24 13:17:34','06330','01011111111','Y',NULL);
/*!40000 ALTER TABLE `app_shipping` ENABLE KEYS */;
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
