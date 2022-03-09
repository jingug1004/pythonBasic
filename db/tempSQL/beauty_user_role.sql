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
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `app_user_id` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`app_user_id`,`role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES ('','ADVERTISER'),('01011111111','MEMBER'),('01012345678','MEMBER'),('01020395292','MEMBER'),('01021843006','MEMBER'),('01022274374','MEMBER'),('01022278141','MEMBER'),('01022296992','MEMBER'),('01022431730','MEMBER'),('01022475916','MEMBER'),('01022531458','MEMBER'),('01023767377','MEMBER'),('01023794955','MEMBER'),('01023983973','MEMBER'),('01024223987','MEMBER'),('01024300273','MEMBER'),('01025559662','MEMBER'),('01025899737','MEMBER'),('01026237490','MEMBER'),('01026273810','MEMBER'),('01026655187','MEMBER'),('01027737750','MEMBER'),('01027886372','MEMBER'),('01028264090','MEMBER'),('01028337490','MEMBER'),('01028852869','MEMBER'),('01029183587','MEMBER'),('01030850953','MEMBER'),('01031245254','MEMBER'),('01032267680','MEMBER'),('01032686617','MEMBER'),('01033149296','MEMBER'),('01033370390','MEMBER'),('01033527465','MEMBER'),('01033677017','MEMBER'),('01033685941','MEMBER'),('01034459756','MEMBER'),('01036320615','MEMBER'),('01036426797','MEMBER'),('01036576324','MEMBER'),('01036926728','MEMBER'),('01037852153','MEMBER'),('01039438580','MEMBER'),('01040457734','MEMBER'),('01040691775','MEMBER'),('01040908706','MEMBER'),('01041372094','MEMBER'),('01042757799','MEMBER'),('01044146324','MEMBER'),('01044693070','MEMBER'),('01046207661','MEMBER'),('01046212471','MEMBER'),('01046410131','MEMBER'),('01046875321','MEMBER'),('01047320614','MEMBER'),('01047402583','MEMBER'),('01047441507','MEMBER'),('01048643021','MEMBER'),('01048799862','MEMBER'),('01049680612','MEMBER'),('01051148248','MEMBER'),('01051735159','MEMBER'),('01051875292','MEMBER'),('01052437906','MEMBER'),('01052934841','MEMBER'),('01055093584','MEMBER'),('01055226307','MEMBER'),('01055901945','MEMBER'),('01055937998','MEMBER'),('01056186282','MEMBER'),('01056336922','MEMBER'),('01056444815','MEMBER'),('01058830809','MEMBER'),('01059368563','MEMBER'),('01062422089','MEMBER'),('01062436506','MEMBER'),('01062766405','MEMBER'),('01063097105','MEMBER'),('01063144251','MEMBER'),('01064989909','MEMBER'),('01066265990','MEMBER'),('01066443570','MEMBER'),('01066866341','MEMBER'),('01067760415','MEMBER'),('01067824313','MEMBER'),('01068090606','MEMBER'),('01068663653','MEMBER'),('01072226984','MEMBER'),('01073540904','MEMBER'),('01074137417','MEMBER'),('01074712332','MEMBER'),('01075835866','MEMBER'),('01076491280','MEMBER'),('01076620710','MEMBER'),('01076696011','MEMBER'),('01077236960','MEMBER'),('01077743860','MEMBER'),('01082116055','MEMBER'),('01083976812','MEMBER'),('01084850236','MEMBER'),('01086451475','MEMBER'),('01086510628','MEMBER'),('01087257872','MEMBER'),('01087339298','MEMBER'),('01087454068','MEMBER'),('01089258429','MEMBER'),('01089498626','MEMBER'),('01089661758','MEMBER'),('01090543448','MEMBER'),('01090612338','MEMBER'),('01090666882','MEMBER'),('01090823361','MEMBER'),('01091271487','MEMBER'),('01091549861','MEMBER'),('01091659508','MEMBER'),('01093845614','MEMBER'),('01094043780','MEMBER'),('01094669411','MEMBER'),('01094957022','MEMBER'),('01095391789','MEMBER'),('01095857119','MEMBER'),('01097180075','MEMBER'),('01097610658','MEMBER'),('01098223533','MEMBER'),('admin','ADMIN'),('admin','ADVERTISER'),('chitocross','ADVERTISER'),('cinderellajp','ADVERTISER'),('dionel','ADVERTISER'),('edenhills','ADVERTISER'),('emcorp','ADVERTISER'),('enough','ADVERTISER'),('farandwide','ADVERTISER'),('hans','ADVERTISER'),('jungsy','ADVERTISER'),('l2lglobal','ADVERTISER'),('limecc','ADVERTISER'),('lyc1324','ADVERTISER'),('mainko','ADVERTISER'),('merbliss','ADVERTISER'),('monstory','ADVERTISER'),('peoples','ADVERTISER'),('pogonia','ADVERTISER'),('puregen','ADVERTISER'),('rubelly','ADVERTISER'),('saeba','ADVERTISER'),('saebyeok','ADVERTISER'),('sum','ADVERTISER'),('sunmax','ADVERTISER'),('test','ADVERTISER'),('thankyoufarmer','ADVERTISER'),('trunote','ADVERTISER'),('vb','ADVERTISER'),('viplus','ADVERTISER'),('wichal','ADVERTISER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
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
