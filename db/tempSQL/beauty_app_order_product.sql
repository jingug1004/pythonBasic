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
-- Table structure for table `app_order_product`
--

DROP TABLE IF EXISTS `app_order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_order_product` (
  `ord_prod` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `prod_name` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `order_id` varchar(100) DEFAULT NULL,
  `prod_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ord_prod`),
  KEY `FKg6jeqdv3b43liyeja7kvlsb8k` (`order_id`)
) ENGINE=MyISAM AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_order_product`
--

LOCK TABLES `app_order_product` WRITE;
/*!40000 ALTER TABLE `app_order_product` DISABLE KEYS */;
INSERT INTO `app_order_product` VALUES (10,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','2eUjGCBn946F88Y',104),(11,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','1ewrnQ5rsLJQzN0',104),(12,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','fhlH1jrPD4TE2xp',104),(13,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','2uct4WCBFqmjsDi',104),(14,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','D0gJCG3I38EQUEn',104),(15,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','OcORwuqD7DlAIKN',104),(16,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','R7HQGAbWBPaiiEf',104),(17,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','7iDs260NWmTKtXc',104),(18,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','mmMMlSSteXLushW',104),(19,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','S4acmwqpJEcPfoO',104),(20,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','X0Lzl48np6xVffr',104),(21,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','s6MsQMy6SrF9qqH',104),(22,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','34IGiWljnu7UDsc',104),(23,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','JfVMMLgTgR7qLPE',104),(24,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','sUCDCxQbY4nB8ji',104),(25,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','mpBSSoDxR07xXcR',104),(26,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','GkXYE6qm4YraijC',104),(27,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','SVBeDIirSHdoki6',104),(28,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','SaCm2LQT6ahk7g1',104),(29,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','wcznADDyjKmFxYG',104),(30,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','lxgdj2edKRZxzzM',104),(31,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','wQue6zYqI2dUBiy',104),(32,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','RlciHTNGFAcI2Sx',104),(33,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','ZMlIgAroYNX3S7W',104),(34,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','wWgXyrZPQTq82di',104),(35,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','X6LWe4ghqUYbkuN',104),(36,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','9BAZkKkrItEQutB',104),(37,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','y1F4iMq33G5zng4',124),(38,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','a3ZCrQuSMTHHtNd',124),(39,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','4ItwFGB0usbZsd0',124),(40,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','9yinYsnvs807Yyf',124),(41,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','6NhHvvHjejjjD9K',124),(42,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','0u4AdHkbI62aKjh',124),(43,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','D9eHa8tZpe8cKpK',124),(44,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','41mYdrXhSmwUttT',124),(45,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','tC8QUgcMapjqvhs',124),(46,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','bdeW97xYp2G15TS',124),(47,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','VQThy0vHJtg14HB',124),(48,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','gveviLBfganfDeg',124),(49,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','C3j6cSZfGwbAQcf',124),(50,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','byD6la3zlOksaNl',124),(51,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','GwxfgxToBEGxVv9',124),(52,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','LKDCFljlI7QWRqd',124),(53,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','2b37ljmYoNYjcKR',124),(54,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','tVK30MZgC4LwtkN',124),(55,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','sscQo2W7FRzvKWB',124),(56,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','JbvWWNIwtOBhR0Z',124),(57,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','SgQyNq96ZU1pT32',124),(58,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','xBAy2ZDQzIPYNDd',124),(59,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','gGmWG7sjck6H6E6',124),(60,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','ROceKxChiPzQ0bv',124),(61,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','1d80D7DHXIowiuL',124),(62,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','V142sTEpt9Nskbt',124),(63,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','oKianp9NrJZj9Xq',124),(64,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','GAIUZehViKjfSnp',124),(65,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','K995uqw9QYdwvSG',124),(66,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','wSLLEikeEZNOM0h',124),(67,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','CdtEsHLpyyVUFYA',124),(68,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','hXO9faGlPT13bnG',124),(69,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','VRKv1bJ0vog8S6j',124),(70,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','xDCdyp13MAGxJGT',124),(71,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','BLqvO9OXxJBJEa6',124),(72,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','nNUmIJKB70RDbbC',124),(73,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','ZNqtMAQ6L1InSbd',124),(74,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','mDndY1HfXURaWbx',124),(75,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','XkykuaPPMkpa2RS',124),(76,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','6yOWTMblZ1qR7eH',124),(77,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','QymaehW00ImtoYI',104),(78,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','pt06rnWVDXMfbId',104),(79,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','RITLQS37z02T1CC',104),(80,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','fAaYDVydTciDdY5',124),(81,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','yZQpGM76Lmbwqzk',104),(82,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','ulZnyQmepI4E3G5',124),(83,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','NzVVE1xhfyN2Qej',124),(84,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','xjt5nBqLRSq90Fi',104),(85,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','u1teI8Zb32KFUwi',104),(86,'미애수','아로마틱 씨드 클렌징 밤','/beauty/product/20170324/thumb_235831942.png','4H8wVjHyIxVySJz',124),(87,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','KHntGZW3smW1vrJ',104),(88,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','QNLJrx3YyjLS3Jz',104),(89,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','Q36CgPKZUrrX1Dp',104),(90,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','pCQ8CIVXaC0z1KD',104),(91,'발그레','TEST LIP','/beauty/product/20170323/thumb_092223580.png','PJyl0t3N2hTZCgn',104);
/*!40000 ALTER TABLE `app_order_product` ENABLE KEYS */;
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
