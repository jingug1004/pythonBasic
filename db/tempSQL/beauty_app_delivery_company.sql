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
-- Table structure for table `app_delivery_company`
--

DROP TABLE IF EXISTS `app_delivery_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_delivery_company` (
  `code` varchar(3) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_delivery_company`
--

LOCK TABLES `app_delivery_company` WRITE;
/*!40000 ALTER TABLE `app_delivery_company` DISABLE KEYS */;
INSERT INTO `app_delivery_company` VALUES ('01','우체국택배'),('04','CJ대한통운'),('05','한진택배'),('06','로젠택배'),('08','롯데택배(현대택배)'),('10','KGB택배'),('11','일양로지스'),('12','EMS'),('13','DHL'),('14','UPS'),('15','GTX로지스'),('16','한의사랑택배'),('17','천일택배'),('18','건영택배'),('21','Fedex'),('22','대신택배'),('23','경동택배'),('24','CVSnet 편의점택배'),('25','TNT Express'),('26','USPS'),('28','GSMNtoN'),('29','에어보이익스프레스'),('32','합동택배'),('33','DHL Global Mail'),('34','i-Parcel'),('37','범한판토스'),('38','APEX(ECMS Express)'),('39','KG로지스택배'),('40','굿투럭');
/*!40000 ALTER TABLE `app_delivery_company` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:20
