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
-- Table structure for table `app_coupon_sp`
--

DROP TABLE IF EXISTS `app_coupon_sp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_coupon_sp` (
  `sp_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cp_id` varchar(255) DEFAULT NULL,
  `ct_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sp_id`),
  KEY `FKh8a0n9ia3w357jdri2846jc28` (`cp_id`)
) ENGINE=MyISAM AUTO_INCREMENT=278 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_coupon_sp`
--

LOCK TABLES `app_coupon_sp` WRITE;
/*!40000 ALTER TABLE `app_coupon_sp` DISABLE KEYS */;
INSERT INTO `app_coupon_sp` VALUES (31,'yKOctOwyngjkryGT',37),(32,'yKOctOwyngjkryGT',38),(33,'yKOctOwyngjkryGT',39),(34,'yKOctOwyngjkryGT',40),(35,'yKOctOwyngjkryGT',41),(36,'yKOctOwyngjkryGT',42),(37,'yKOctOwyngjkryGT',43),(38,'yKOctOwyngjkryGT',44),(39,'yKOctOwyngjkryGT',45),(40,'yKOctOwyngjkryGT',46),(41,'yKOctOwyngjkryGT',47),(42,'yKOctOwyngjkryGT',48),(43,'yKOctOwyngjkryGT',49),(44,'yKOctOwyngjkryGT',50),(45,'yKOctOwyngjkryGT',51),(46,'yKOctOwyngjkryGT',52),(47,'yKOctOwyngjkryGT',53),(48,'yKOctOwyngjkryGT',54),(49,'yKOctOwyngjkryGT',55),(50,'yKOctOwyngjkryGT',56),(51,'yKOctOwyngjkryGT',57),(52,'yKOctOwyngjkryGT',58),(53,'uLjnvsLoLEdGXirl',37),(54,'uLjnvsLoLEdGXirl',38),(55,'uLjnvsLoLEdGXirl',39),(56,'uLjnvsLoLEdGXirl',40),(57,'uLjnvsLoLEdGXirl',41),(58,'uLjnvsLoLEdGXirl',42),(59,'uLjnvsLoLEdGXirl',43),(60,'uLjnvsLoLEdGXirl',44),(61,'uLjnvsLoLEdGXirl',45),(62,'uLjnvsLoLEdGXirl',46),(63,'uLjnvsLoLEdGXirl',47),(64,'uLjnvsLoLEdGXirl',48),(65,'uLjnvsLoLEdGXirl',49),(66,'uLjnvsLoLEdGXirl',50),(67,'uLjnvsLoLEdGXirl',51),(68,'uLjnvsLoLEdGXirl',52),(69,'uLjnvsLoLEdGXirl',53),(70,'uLjnvsLoLEdGXirl',55),(71,'uLjnvsLoLEdGXirl',56),(72,'uLjnvsLoLEdGXirl',57),(73,'uLjnvsLoLEdGXirl',58),(74,'ekInSMDXSomxiDHM',37),(75,'ekInSMDXSomxiDHM',38),(76,'ekInSMDXSomxiDHM',39),(77,'ekInSMDXSomxiDHM',40),(78,'ekInSMDXSomxiDHM',41),(79,'ekInSMDXSomxiDHM',42),(80,'ekInSMDXSomxiDHM',43),(81,'ekInSMDXSomxiDHM',44),(82,'ekInSMDXSomxiDHM',45),(83,'ekInSMDXSomxiDHM',46),(84,'ekInSMDXSomxiDHM',47),(85,'ekInSMDXSomxiDHM',48),(86,'ekInSMDXSomxiDHM',49),(87,'ekInSMDXSomxiDHM',50),(88,'ekInSMDXSomxiDHM',51),(89,'ekInSMDXSomxiDHM',52),(90,'ekInSMDXSomxiDHM',53),(91,'ekInSMDXSomxiDHM',55),(92,'ekInSMDXSomxiDHM',56),(93,'ekInSMDXSomxiDHM',57),(94,'ekInSMDXSomxiDHM',58),(95,'nqtmxNoSkrvUIXtz',9),(96,'LPktWcXpzpVsohJV',1),(97,'gMMcuefRFlPFqRoz',37),(98,'gMMcuefRFlPFqRoz',38),(99,'gMMcuefRFlPFqRoz',39),(100,'gMMcuefRFlPFqRoz',40),(101,'gMMcuefRFlPFqRoz',41),(102,'gMMcuefRFlPFqRoz',42),(103,'gMMcuefRFlPFqRoz',43),(104,'gMMcuefRFlPFqRoz',44),(105,'gMMcuefRFlPFqRoz',45),(106,'gMMcuefRFlPFqRoz',46),(107,'gMMcuefRFlPFqRoz',47),(108,'gMMcuefRFlPFqRoz',48),(109,'gMMcuefRFlPFqRoz',49),(110,'gMMcuefRFlPFqRoz',50),(111,'gMMcuefRFlPFqRoz',51),(112,'gMMcuefRFlPFqRoz',52),(113,'gMMcuefRFlPFqRoz',53),(114,'gMMcuefRFlPFqRoz',54),(115,'gMMcuefRFlPFqRoz',55),(116,'gMMcuefRFlPFqRoz',56),(117,'gMMcuefRFlPFqRoz',57),(118,'gMMcuefRFlPFqRoz',58),(119,'hLyiQhinZpQSmXrj',10),(120,'hLyiQhinZpQSmXrj',11),(121,'hLyiQhinZpQSmXrj',12),(122,'hLyiQhinZpQSmXrj',13),(123,'hLyiQhinZpQSmXrj',18),(124,'hLyiQhinZpQSmXrj',17),(125,'hLyiQhinZpQSmXrj',19),(126,'hLyiQhinZpQSmXrj',22),(127,'hLyiQhinZpQSmXrj',23),(128,'hLyiQhinZpQSmXrj',24),(129,'hLyiQhinZpQSmXrj',25),(130,'hLyiQhinZpQSmXrj',26),(131,'hLyiQhinZpQSmXrj',27),(132,'hLyiQhinZpQSmXrj',28),(133,'hLyiQhinZpQSmXrj',29),(134,'hLyiQhinZpQSmXrj',30),(135,'hLyiQhinZpQSmXrj',31),(136,'hLyiQhinZpQSmXrj',32),(137,'hLyiQhinZpQSmXrj',33),(138,'hLyiQhinZpQSmXrj',34),(139,'hLyiQhinZpQSmXrj',35),(140,'hLyiQhinZpQSmXrj',36),(141,'hLyiQhinZpQSmXrj',38),(142,'hLyiQhinZpQSmXrj',41),(143,'hLyiQhinZpQSmXrj',42),(144,'hLyiQhinZpQSmXrj',43),(145,'hLyiQhinZpQSmXrj',44),(146,'hLyiQhinZpQSmXrj',47),(147,'hLyiQhinZpQSmXrj',49),(148,'hLyiQhinZpQSmXrj',50),(149,'hLyiQhinZpQSmXrj',51),(150,'hLyiQhinZpQSmXrj',52),(151,'hLyiQhinZpQSmXrj',54),(152,'hLyiQhinZpQSmXrj',57),(153,'hLyiQhinZpQSmXrj',58),(154,'hLyiQhinZpQSmXrj',59),(155,'hLyiQhinZpQSmXrj',60),(156,'hLyiQhinZpQSmXrj',61),(157,'hLyiQhinZpQSmXrj',62),(158,'hLyiQhinZpQSmXrj',63),(159,'hLyiQhinZpQSmXrj',64),(160,'hLyiQhinZpQSmXrj',65),(161,'hLyiQhinZpQSmXrj',70),(162,'hLyiQhinZpQSmXrj',71),(163,'hLyiQhinZpQSmXrj',72),(164,'hLyiQhinZpQSmXrj',87),(165,'hLyiQhinZpQSmXrj',90),(166,'hLyiQhinZpQSmXrj',91),(167,'hLyiQhinZpQSmXrj',92),(168,'hLyiQhinZpQSmXrj',95),(169,'hLyiQhinZpQSmXrj',97),(170,'hLyiQhinZpQSmXrj',98),(171,'hLyiQhinZpQSmXrj',99),(172,'hLyiQhinZpQSmXrj',100),(173,'hLyiQhinZpQSmXrj',101),(174,'hLyiQhinZpQSmXrj',103),(175,'hLyiQhinZpQSmXrj',105),(176,'hLyiQhinZpQSmXrj',106),(177,'hLyiQhinZpQSmXrj',107),(178,'hLyiQhinZpQSmXrj',108),(179,'hLyiQhinZpQSmXrj',109),(180,'hLyiQhinZpQSmXrj',112),(181,'hLyiQhinZpQSmXrj',113),(182,'hLyiQhinZpQSmXrj',114),(183,'hLyiQhinZpQSmXrj',118),(184,'hLyiQhinZpQSmXrj',119),(185,'hLyiQhinZpQSmXrj',120),(186,'hLyiQhinZpQSmXrj',121),(187,'hLyiQhinZpQSmXrj',122),(188,'hLyiQhinZpQSmXrj',123),(189,'hLyiQhinZpQSmXrj',124),(190,'hLyiQhinZpQSmXrj',125),(191,'hLyiQhinZpQSmXrj',126),(192,'hLyiQhinZpQSmXrj',131),(193,'hLyiQhinZpQSmXrj',132),(194,'hLyiQhinZpQSmXrj',133),(195,'hLyiQhinZpQSmXrj',134),(196,'hLyiQhinZpQSmXrj',135),(197,'hLyiQhinZpQSmXrj',136),(198,'hLyiQhinZpQSmXrj',137),(199,'hLyiQhinZpQSmXrj',138),(200,'hLyiQhinZpQSmXrj',139),(201,'hLyiQhinZpQSmXrj',140),(202,'hLyiQhinZpQSmXrj',141),(203,'hLyiQhinZpQSmXrj',144),(204,'hLyiQhinZpQSmXrj',145),(205,'hLyiQhinZpQSmXrj',148),(206,'hLyiQhinZpQSmXrj',149),(207,'hLyiQhinZpQSmXrj',150),(208,'hLyiQhinZpQSmXrj',151),(209,'hLyiQhinZpQSmXrj',152),(210,'hLyiQhinZpQSmXrj',153),(211,'hLyiQhinZpQSmXrj',154),(212,'hLyiQhinZpQSmXrj',155),(213,'hLyiQhinZpQSmXrj',156),(214,'hLyiQhinZpQSmXrj',157),(215,'hLyiQhinZpQSmXrj',158),(216,'hLyiQhinZpQSmXrj',159),(217,'hLyiQhinZpQSmXrj',160),(218,'hLyiQhinZpQSmXrj',161),(219,'hLyiQhinZpQSmXrj',162),(220,'hLyiQhinZpQSmXrj',163),(221,'hLyiQhinZpQSmXrj',164),(222,'hLyiQhinZpQSmXrj',165),(223,'hLyiQhinZpQSmXrj',166),(224,'hLyiQhinZpQSmXrj',167),(225,'hLyiQhinZpQSmXrj',168),(226,'hLyiQhinZpQSmXrj',169),(227,'hLyiQhinZpQSmXrj',170),(228,'hLyiQhinZpQSmXrj',171),(229,'hLyiQhinZpQSmXrj',172),(230,'hLyiQhinZpQSmXrj',173),(231,'hLyiQhinZpQSmXrj',175),(232,'hLyiQhinZpQSmXrj',176),(233,'hLyiQhinZpQSmXrj',178),(234,'hLyiQhinZpQSmXrj',179),(235,'hLyiQhinZpQSmXrj',180),(236,'hLyiQhinZpQSmXrj',181),(237,'hLyiQhinZpQSmXrj',182),(238,'hLyiQhinZpQSmXrj',183),(239,'hLyiQhinZpQSmXrj',184),(240,'hLyiQhinZpQSmXrj',185),(241,'hLyiQhinZpQSmXrj',186),(242,'hLyiQhinZpQSmXrj',187),(243,'hLyiQhinZpQSmXrj',188),(244,'hLyiQhinZpQSmXrj',191),(245,'hLyiQhinZpQSmXrj',192),(246,'hLyiQhinZpQSmXrj',193),(247,'hLyiQhinZpQSmXrj',194),(248,'hLyiQhinZpQSmXrj',195),(249,'hLyiQhinZpQSmXrj',196),(250,'hLyiQhinZpQSmXrj',197),(251,'hLyiQhinZpQSmXrj',198),(252,'hLyiQhinZpQSmXrj',199),(253,'hLyiQhinZpQSmXrj',200),(254,'hLyiQhinZpQSmXrj',201),(255,'hLyiQhinZpQSmXrj',202),(256,'hLyiQhinZpQSmXrj',203),(257,'hLyiQhinZpQSmXrj',204),(258,'hLyiQhinZpQSmXrj',205),(259,'hLyiQhinZpQSmXrj',206),(260,'hLyiQhinZpQSmXrj',207),(261,'hLyiQhinZpQSmXrj',208),(262,'hLyiQhinZpQSmXrj',209),(264,'hLyiQhinZpQSmXrj',211),(265,'hLyiQhinZpQSmXrj',212),(266,'hLyiQhinZpQSmXrj',213),(267,'hLyiQhinZpQSmXrj',214),(268,'hLyiQhinZpQSmXrj',215),(269,'hLyiQhinZpQSmXrj',216),(270,'hLyiQhinZpQSmXrj',217),(271,'hLyiQhinZpQSmXrj',218),(272,'hLyiQhinZpQSmXrj',219),(273,'hLyiQhinZpQSmXrj',221),(274,'hLyiQhinZpQSmXrj',222),(275,'hLyiQhinZpQSmXrj',224),(276,'hLyiQhinZpQSmXrj',225),(277,'hLyiQhinZpQSmXrj',20);
/*!40000 ALTER TABLE `app_coupon_sp` ENABLE KEYS */;
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
