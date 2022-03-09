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
-- Table structure for table `app_category`
--

DROP TABLE IF EXISTS `app_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_category` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) DEFAULT NULL,
  `menu_parent` bigint(20) DEFAULT NULL,
  `menu_sort` int(11) DEFAULT NULL,
  `menu_visible` int(11) DEFAULT NULL,
  `menu_depth` int(11) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=MyISAM AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_category`
--

LOCK TABLES `app_category` WRITE;
/*!40000 ALTER TABLE `app_category` DISABLE KEYS */;
INSERT INTO `app_category` VALUES (1,'상품',-1,1,1,1),(2,'MAKE UP',1,1,1,2),(3,'SKIN',1,2,1,2),(4,'HAIR',1,3,1,2),(5,'BODY',1,4,1,2),(6,'MEN',1,5,1,2),(7,'FERFUME',1,6,1,2),(21,'아이브로우',9,3,1,4),(20,'아이라이너',9,2,1,4),(19,'아이섀도우',9,1,1,4),(8,'페이스',2,1,1,3),(18,'픽서',8,6,1,4),(17,'하이라이터·블러셔·쉐딩',8,5,1,4),(16,'팩트·파우더',8,4,1,4),(15,'파운데이션·쿠션',8,3,1,4),(14,'BB·CC',8,2,1,4),(13,'베이스·프라이머',8,1,1,4),(12,'메이크업도구',2,5,1,3),(11,'네일',2,4,1,3),(10,'립',2,3,1,3),(9,'아이',2,2,1,3),(22,'마스카라',9,4,1,4),(23,'립스틱',10,1,1,4),(24,'틴트',10,2,1,4),(25,'립밤',10,3,1,4),(26,'립글로즈',10,4,1,4),(27,'브러쉬·퍼프',12,1,1,4),(28,'기타',12,2,1,4),(29,'스킨·미스트',3,1,1,3),(30,'로션·오일',3,2,1,3),(31,'에센스·세럼',3,3,1,3),(32,'크림·아이크림',3,4,1,3),(33,'기능성',3,5,1,3),(34,'클렌징',3,6,1,3),(35,'팩·패치',3,7,1,3),(36,'스킨케어도구',3,8,1,3),(37,'스킨',29,1,1,4),(38,'미스트',29,2,1,4),(39,'로션',30,3,1,4),(40,'페이셜오일',30,4,1,4),(41,'에센스',31,1,1,4),(42,'세럼',31,2,1,4),(43,'크림',32,1,1,4),(44,'아이크림',32,2,1,4),(45,'선케어',33,1,1,4),(46,'트러블케어',33,2,1,4),(47,'클렌징 폼',34,1,1,4),(48,'클렌징 워터',34,2,1,4),(49,'클렌징 오일',34,3,1,4),(50,'클렌징 크림',34,4,1,4),(51,'클렌징 티슈',34,5,1,4),(52,'필링·스크럽',34,6,1,4),(53,'립·아이리무버',34,7,1,4),(54,'클렌징도구',34,8,1,4),(55,'시트팩',35,1,1,4),(56,'워시오프팩',35,2,1,4),(57,'패치˙코팩',35,3,1,4),(58,'슬리핑팩',35,4,1,4),(59,'샴푸·린스·트리트먼트',4,1,1,3),(60,'에센스·앰플·미스트',4,2,1,3),(61,'스타일링',4,3,1,3),(62,'헤어도구',4,4,1,3),(63,'샴푸',59,1,1,4),(64,'린스',59,2,1,4),(65,'트리트먼트',59,3,1,4),(66,'염색',61,1,1,4),(67,'왁스·스프레이',61,2,1,4),(68,'기타',61,3,1,4),(69,'바디클렌징',5,1,1,3),(70,'바디스킨케어',5,2,1,3),(71,'핸드·풋케어',5,3,1,3),(72,'제모·데오드란트·슬리밍',5,4,1,3),(73,'바디도구',5,5,1,3),(74,'바디클렌저',69,1,1,4),(75,'스크럽',69,2,1,4),(76,'입욕제',69,3,1,4),(77,'여성청결제',69,4,1,4),(78,'바디로션',70,1,1,4),(79,'바디크림',70,2,1,4),(80,'바디오일',70,3,1,4),(81,'바디미스트',70,4,1,4),(82,'핸드케어',71,1,1,4),(83,'풋케어',71,2,1,4),(84,'제모',72,1,1,4),(85,'데오드란트',72,2,1,4),(86,'슬리밍',72,3,1,4),(87,'스킨',6,1,1,3),(88,'메이크업',6,2,1,3),(89,'클렌징',6,3,1,3),(90,'헤어',6,4,1,3),(91,'쉐이빙',89,1,1,4),(92,'클렌저',89,2,1,4),(93,'여성향수',7,1,1,3),(94,'남성향수',7,2,1,3),(95,'아로마',7,3,1,3),(96,'디퓨저',95,1,1,4),(97,'캔들',95,2,1,4),(98,'기타',95,3,1,4),(99,'test',7,1,0,0),(100,'클렌징 바',34,1,1,0),(101,'모델링팩',35,1,1,0),(102,'톤업크림',32,1,1,0);
/*!40000 ALTER TABLE `app_category` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-26 11:51:17
