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
-- Table structure for table `app_coupon`
--

DROP TABLE IF EXISTS `app_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_coupon` (
  `cp_id` varchar(255) NOT NULL,
  `cp_count` int(11) DEFAULT NULL,
  `cp_name` varchar(255) DEFAULT NULL,
  `cp_range` int(1) DEFAULT '0',
  `cp_type` int(1) DEFAULT '0',
  `delete_date` int(3) DEFAULT '0',
  `delete_type` int(1) DEFAULT '0',
  `maximum` bigint(20) DEFAULT NULL,
  `minimum` bigint(20) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `sale_type` int(1) DEFAULT '0',
  `end_date` datetime DEFAULT NULL,
  `end_type` int(1) DEFAULT '0',
  `end_after` int(3) DEFAULT '0',
  `download` int(1) DEFAULT '0',
  `auto` int(1) DEFAULT '0',
  PRIMARY KEY (`cp_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_coupon`
--

LOCK TABLES `app_coupon` WRITE;
/*!40000 ALTER TABLE `app_coupon` DISABLE KEYS */;
INSERT INTO `app_coupon` VALUES ('mlFVFnLHnXZvEfhT',-49,'[기획쿠폰] 20% 쿠폰',0,0,0,0,0,0,20,'2017-03-13 20:25:00',0,NULL,0,7,0,0),('gMMcuefRFlPFqRoz',-21,'[3월기획쿠폰] 스킨케어 3,000원 쿠폰',0,3,0,0,3000,5000,3000,'2017-03-10 17:15:17',1,'2017-03-31 00:00:00',1,0,0,0),('LPktWcXpzpVsohJV',10,'[TEST쿠폰] 특별 브랜드용',0,2,0,0,0,0,10,'2017-02-28 15:03:41',0,NULL,0,5,0,0),('nqtmxNoSkrvUIXtz',10,'[TEST쿠폰]기간만료확인용 10분쿠폰',0,2,0,0,0,0,10,'2017-02-28 14:24:54',0,'2017-03-31 23:59:59',1,0,0,0),('zQdFmvxFrlGOfVis',-101,'[감사쿠폰] 신규회원 10% 무제한 쿠폰',0,0,0,0,0,0,10,'2017-02-16 22:42:56',0,NULL,0,14,0,0),('OOlQaQJqEemRvTQK',-8,'[감사쿠폰] 신규회원 3,000원 할인쿠폰',0,0,0,0,3000,0,3000,'2017-02-16 22:44:15',1,NULL,0,14,0,0),('KWvgLLABVGBiAxiU',-209,'[감사쿠폰] 신규회원 10,000원 할인쿠폰',0,0,0,0,10000,50000,10000,'2017-02-16 22:45:36',1,NULL,0,14,1,1),('ZxEAdEjtIcLzJVvM',-71,'[첫구매쿠폰] 첫 구매 감사 중복 3% 할인 쿠폰',0,1,0,0,0,0,3,'2017-02-16 22:46:52',0,NULL,0,14,1,2),('bzQWbcELsKCRkBvL',0,'[3월출석체크] 출석체크 중복 3%쿠폰',0,1,0,0,0,0,3,'2017-02-17 20:13:58',0,NULL,0,20,0,0),('uLjnvsLoLEdGXirl',-1,'[TEST쿠폰] 특정상품 카테고리 10%',0,3,0,0,0,0,10,'2017-02-24 15:02:58',0,NULL,0,7,0,0),('ekInSMDXSomxiDHM',-13,'[3월기획쿠폰] 스킨케어 3,000원 쿠폰',0,3,0,0,3000,0,3000,'2017-02-24 15:04:26',1,'2017-03-31 23:59:59',1,0,0,0),('SWrEMBspZYjAJZVi',-167,'[감사쿠폰] 신규회원 3,000원 할인쿠폰',0,0,0,0,3000,20000,3000,'2017-02-21 11:01:41',1,NULL,0,14,0,0),('ETeYXOBPvUXgbYSz',0,'[3월기획쿠폰] 전제품 20%할인 쿠폰',0,0,0,0,0,0,20,'2017-02-17 20:12:15',0,'2017-03-31 23:59:59',1,0,0,0),('RoNvhfzYnhpTkjHC',-37,'[3월기획쿠폰] 전제품 10%할인 쿠폰',0,0,0,0,0,0,10,'2017-02-21 11:02:50',0,'2017-03-31 23:59:59',1,0,0,0),('hLyiQhinZpQSmXrj',-16,'[기획쿠폰] 20% 쿠폰',0,4,0,0,0,0,20,'2017-04-25 15:50:54',0,NULL,0,7,1,0),('yKOctOwyngjkryGT',0,'[3월기획쿠폰] 스킨케어 3,000원 쿠폰',0,3,0,0,3000,0,3000,'2017-02-17 20:16:21',1,'2017-03-31 23:59:59',1,0,0,0),('OtnTlwGWGmaSptpZ',10,'[3월기획쿠폰] 10% 할인쿠폰 (최대 8,000원)',0,0,0,0,8000,10000,10,'2017-02-20 17:49:32',0,NULL,0,1,0,0),('CKKsJPCmYMVzkgVc',-1,'[감사쿠폰] 신규회원 3,000원 할인쿠폰',0,0,0,0,2000,10000,2000,'2017-05-02 16:51:39',1,NULL,0,7,0,0),('KYtiRIYAABBXBkzK',-36,'[감사쿠폰] 신규회원 2,000원 할인쿠폰',0,0,0,0,2000,10000,2000,'2017-05-02 16:53:30',1,NULL,0,7,1,1);
/*!40000 ALTER TABLE `app_coupon` ENABLE KEYS */;
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
