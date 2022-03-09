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
-- Table structure for table `app_payment`
--

DROP TABLE IF EXISTS `app_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_payment` (
  `payment_uid` varchar(255) NOT NULL,
  `imp_uid` varchar(255) DEFAULT NULL,
  `buyer_addr` varchar(255) DEFAULT NULL,
  `buyer_email` varchar(255) DEFAULT NULL,
  `buyer_name` varchar(255) DEFAULT NULL,
  `buyer_postcode` varchar(255) DEFAULT NULL,
  `buyer_tel` varchar(255) DEFAULT NULL,
  `coupon_discount` int(11) DEFAULT NULL,
  `coupon_id` varchar(255) DEFAULT NULL,
  `merchant_uid` varchar(255) DEFAULT NULL,
  `point_discount` int(11) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `total_payment` int(11) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `apply_num` varchar(255) DEFAULT NULL,
  `cancel_amount` decimal(19,2) DEFAULT NULL,
  `cancel_reason` varchar(255) DEFAULT NULL,
  `cancelled_at` datetime DEFAULT NULL,
  `card_name` varchar(255) DEFAULT NULL,
  `card_quota` int(11) NOT NULL,
  `escrow` bit(1) NOT NULL,
  `fail_reason` varchar(255) DEFAULT NULL,
  `failed_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `paid_at` datetime DEFAULT NULL,
  `pay_method` varchar(255) DEFAULT NULL,
  `pg_provider` varchar(255) DEFAULT NULL,
  `pg_tid` varchar(255) DEFAULT NULL,
  `receipt_url` varchar(255) DEFAULT NULL,
  `vbank_date` datetime DEFAULT NULL,
  `vbank_holder` varchar(255) DEFAULT NULL,
  `vbank_name` varchar(255) DEFAULT NULL,
  `vbank_num` varchar(255) DEFAULT NULL,
  `note` longtext,
  `point_yn` int(1) DEFAULT '0',
  `refund_account` varchar(255) DEFAULT NULL,
  `refund_bank` varchar(255) DEFAULT NULL,
  `refund_holder` varchar(255) DEFAULT NULL,
  `paid_proc` int(1) DEFAULT '0',
  `normal_coupon_discount` int(11) DEFAULT NULL,
  `normal_coupon_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`payment_uid`),
  KEY `FKqtfeqa5vaiprqngam1heyb1u8` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_payment`
--

LOCK TABLES `app_payment` WRITE;
/*!40000 ALTER TABLE `app_payment` DISABLE KEYS */;
INSERT INTO `app_payment` VALUES ('20170523164514181','imp_225515710142','TEST1 TEST2','text@test.com','테스트','12345','01012345678',0,NULL,'bgr_20170523164514181',0,'2017-05-23 16:45:14',900,'01011111111','paid',900.00,'02280031',0.00,NULL,'1970-01-01 09:00:00','신한카드',0,'\0',NULL,'1970-01-01 09:00:00','TEST LIP','2017-05-23 16:47:00','card','html5_inicis','StdpayCARDMOIsumcrea20170523164654588639','https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=StdpayCARDMOIsumcrea20170523164654588639&noMethod=1','1970-01-01 09:00:00',NULL,NULL,NULL,'TEST',0,NULL,NULL,'테스트1',1,0,NULL),('20170523160822072',NULL,'TEST1 TEST2','text@test.com','테스트','12345','01012345678',0,NULL,'bgr_20170523160822073',0,'2017-05-23 16:08:22',800,'01011111111','ready',NULL,NULL,NULL,NULL,NULL,NULL,0,'\0',NULL,NULL,'TEST LIP외 1건',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'TEST',0,NULL,NULL,'테스트1',0,0,NULL),('20170518105831337','imp_495857602087','서울 은평구 응암동 587-28 B01호(지층왼쪽집)','mariah1@naver.com','이동원','03484','01066265990',0,NULL,'bgr_20170518105831337',0,'2017-05-18 10:58:31',42000,'01066265990','paid',42000.00,'08446295',0.00,NULL,'1970-01-01 09:00:00','외환카드',0,'\0',NULL,'1970-01-01 09:00:00','[퓨어젠]E.G.F 인비보 리페어 세럼','2017-05-18 11:01:22','card','html5_inicis','INIMX_CARDMOIsumcrea20170518110118281165','https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=INIMX_CARDMOIsumcrea20170518110118281165&noMethod=1','1970-01-01 09:00:00',NULL,NULL,NULL,'',0,NULL,NULL,NULL,1,-10000,'KWvgLLABVGBiAxiU'),('20170523184234582',NULL,'ㄹㄷㅈㄹ ㅈㄷㄹ','text@test.com','ㄹㄷㅈㄹㅈㄹ','',NULL,0,NULL,'bgr_20170523184234582',0,'2017-05-23 18:42:34',110,'01011111111','ready',NULL,NULL,NULL,NULL,NULL,NULL,0,'\0',NULL,NULL,'TEST LIP',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'부재시 경비실에 맡겨주세요',0,NULL,NULL,'테스트1',0,0,NULL),('20170523184524131',NULL,'ㄹㄷㅈㄹㅈㄷ ㄹㅈㄷㄹ','text@test.com','ㅇㅁㅈㅇㅁㅈㅇ','','01012345678',0,NULL,'bgr_20170523184524131',0,'2017-05-23 18:45:24',110,'01011111111','ready',NULL,NULL,NULL,NULL,NULL,NULL,0,'\0',NULL,NULL,'TEST LIP',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'',0,NULL,NULL,'테스트1',0,0,NULL),('20170523184637418',NULL,'ㄹㄷㅈㄹㅈㄷ ㄹㅈㄷㄹ','text@test.com','ㅇㅁㅈㅇㅁㅈㅇ','','01012345678',0,NULL,'bgr_20170523184637418',0,'2017-05-23 18:46:37',110,'01011111111','ready',NULL,NULL,NULL,NULL,NULL,NULL,0,'\0',NULL,NULL,'TEST LIP',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'',0,NULL,NULL,'테스트1',0,0,NULL),('20170524085912839',NULL,'ㄹㄷㅈㄹㅈㄷ ㄹㅈㄷㄹ','text@test.com','ㅇㅁㅈㅇㅁㅈㅇ','','01012345678',0,NULL,'bgr_20170524085912840',0,'2017-05-24 08:59:12',210,'01011111111','ready',NULL,NULL,NULL,NULL,NULL,NULL,0,'\0',NULL,NULL,'TEST LIP',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'방문전 연락주세요',0,NULL,NULL,'테스트1',0,0,NULL),('20170524105642272',NULL,'ㄹㄷㅈㄹㅈㄷ ㄹㅈㄷㄹ','text@test.com','ㅇㅁㅈㅇㅁㅈㅇ','','01012345678',0,NULL,'bgr_20170524105642272',0,'2017-05-24 10:56:42',510,'01011111111','ready',NULL,NULL,NULL,NULL,NULL,NULL,0,'\0',NULL,NULL,'TEST LIP',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'',0,NULL,NULL,'테스트1',0,0,NULL),('20170524114354932','imp_612944193546','서울 성동구 성수일로4길 26 (성수동2가, 서울숲힐스테이트) 105-1405','redo486v@nate.com','임가희','04780','01095391789',0,NULL,'bgr_20170524114354934',1000,'2017-05-24 11:43:54',13500,'01095391789','paid',13500.00,'08175283',0.00,NULL,'1970-01-01 09:00:00','신한카드',0,'\0',NULL,'1970-01-01 09:00:00','[땡큐파머]듀얼 프리미엄 코튼 패드 5개(400매)','2017-05-24 11:46:26','card','html5_inicis','INIMX_CARDMOIsumcrea20170524114620231556','https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=INIMX_CARDMOIsumcrea20170524114620231556&noMethod=1','1970-01-01 09:00:00',NULL,NULL,NULL,'',0,NULL,NULL,NULL,1,0,'');
/*!40000 ALTER TABLE `app_payment` ENABLE KEYS */;
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
