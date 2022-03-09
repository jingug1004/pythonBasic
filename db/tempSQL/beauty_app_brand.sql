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
-- Table structure for table `app_brand`
--

DROP TABLE IF EXISTS `app_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_brand` (
  `brand_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `banner` varchar(255) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `brand_visible` char(1) DEFAULT 'Y',
  `reg_date` datetime DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=MyISAM AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_brand`
--

LOCK TABLES `app_brand` WRITE;
/*!40000 ALTER TABLE `app_brand` DISABLE KEYS */;
INSERT INTO `app_brand` VALUES (1,'','발그레','N','2017-01-06 13:05:15'),(5,'/beauty/brand/20170206/brand_143051120.png','테스트','N','2017-02-06 14:30:50'),(4,NULL,'위첼','Y','2017-02-02 21:12:32'),(13,'/beauty/brand/20170320/thumb_175656057.jpg','메인코','Y','2017-03-20 17:56:58'),(7,'/beauty/brand/20170207/brand_172924478.png','퓨어젠','Y','2017-02-07 17:29:24'),(8,'/beauty/brand/20170207/brand_173141517.png','프라이빗21','Y','2017-02-07 17:31:41'),(9,'/beauty/brand/20170207/brand_173240921.png','땡큐파머','Y','2017-02-07 17:32:40'),(12,'/beauty/brand/20170317/thumb_162846905.png','루벨리','Y','2017-03-17 16:28:48'),(14,'/beauty/brand/20170321/thumb_095349036.png','드보세르','Y','2017-03-21 09:53:52'),(15,'/beauty/brand/20170322/thumb_151445859.png','포고니아','Y','2017-03-22 15:14:48'),(16,'/beauty/brand/20170324/thumb_091308616.png','한스오가닉','Y','2017-03-24 09:13:12'),(17,'/beauty/brand/20170324/thumb_154443641.jpg','디오넬','Y','2017-03-24 15:44:44'),(18,'/beauty/brand/20170324/thumb_170435717.png','에덴힐스','N','2017-03-24 17:04:37'),(19,'/beauty/brand/20170327/thumb_120759717.png','미애수','Y','2017-03-27 12:08:01'),(20,'/beauty/brand/20170327/thumb_121345458.png','아발론','Y','2017-03-27 12:13:47'),(21,'/beauty/brand/20170327/thumb_122024702.png','캐비올','Y','2017-03-27 12:20:26'),(22,'/beauty/brand/20170327/thumb_175527616.png','멀블리스','Y','2017-03-27 17:55:29'),(23,'/beauty/brand/20170328/thumb_164824655.jpg','JP코스메틱','N','2017-03-28 16:48:26'),(24,'/beauty/brand/20170328/thumb_165849067.jpg','렛헐메이크오버','Y','2017-03-28 16:58:50'),(27,'/beauty/brand/20170329/thumb_124839791.jpg','이너프','Y','2017-03-29 12:48:43'),(26,'/beauty/brand/20170329/thumb_093834500.png','럭시멈','Y','2017-03-29 09:38:40'),(28,'/beauty/brand/20170329/thumb_140213063.jpg','신데렐라JP','Y','2017-03-29 14:02:16'),(29,'/beauty/brand/20170329/thumb_140415163.jpg','프린시크','Y','2017-03-29 14:04:18'),(30,'/beauty/brand/20170330/thumb_174709175.png','트루노트','Y','2017-03-30 17:47:12'),(31,'/beauty/brand/20170331/thumb_184622112.jpg','숲에서온','Y','2017-03-31 18:46:25'),(32,'/beauty/brand/20170404/thumb_090459964.jpg','나우비','Y','2017-04-04 09:05:09'),(33,'/beauty/brand/20170404/thumb_123324886.png','말마','Y','2017-04-04 12:33:26'),(34,'/beauty/brand/20170405/thumb_212607747.JPEG','프레티','Y','2017-04-05 21:26:10'),(35,'/beauty/brand/20170410/thumb_090927071.png','퓨어덤','Y','2017-04-10 09:09:31'),(36,'/beauty/brand/20170410/thumb_091038128.png','셀킨','Y','2017-04-10 09:10:42'),(37,'/beauty/brand/20170410/thumb_204656314.png','몬스토리','Y','2017-04-10 20:46:58'),(38,'/beauty/brand/20170410/thumb_215320642.png','SKINATURE','Y','2017-04-10 21:53:24'),(39,'/beauty/brand/20170413/thumb_001806829.jpg','키엘리','Y','2017-04-13 00:18:09'),(40,'/beauty/brand/20170413/thumb_021003187.png','퐁당','Y','2017-04-13 02:10:05'),(42,'/beauty/brand/20170414/thumb_153846487.JPEG','아라샵','Y','2017-04-14 15:38:50'),(43,'/beauty/brand/20170414/thumb_165710270.JPEG','디바인 보떼','Y','2017-04-14 16:57:12');
/*!40000 ALTER TABLE `app_brand` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:24
