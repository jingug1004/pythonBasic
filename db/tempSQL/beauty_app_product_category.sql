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
-- Table structure for table `app_product_category`
--

DROP TABLE IF EXISTS `app_product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_date` datetime DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKghlqdu4ttp8uqjejcglb67qb4` (`menu_id`),
  KEY `FK67wq1uy56xhnxvw6bkorvqtw4` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1617 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_product_category`
--

LOCK TABLES `app_product_category` WRITE;
/*!40000 ALTER TABLE `app_product_category` DISABLE KEYS */;
INSERT INTO `app_product_category` VALUES (1355,'2017-04-24 13:14:16',42,10),(1354,'2017-04-24 13:12:23',43,11),(1356,'2017-04-24 13:17:17',55,12),(1358,'2017-04-24 13:22:51',43,13),(1367,'2017-04-24 14:36:17',42,17),(1369,'2017-04-24 14:45:47',42,18),(1370,'2017-04-24 14:50:29',42,19),(1373,'2017-04-24 15:11:26',42,22),(370,'2017-03-03 14:53:07',17,81),(1378,'2017-04-24 15:22:50',42,24),(1379,'2017-04-24 15:26:10',42,25),(1470,'2017-05-09 15:51:06',42,26),(1368,'2017-04-24 14:40:38',42,27),(371,'2017-03-03 14:53:07',15,81),(1505,'2017-05-10 15:32:12',37,29),(1508,'2017-05-10 15:32:44',39,30),(1507,'2017-05-10 15:32:37',41,31),(1506,'2017-05-10 15:32:28',43,32),(1364,'2017-04-24 14:12:07',37,33),(1362,'2017-04-24 14:06:37',39,34),(1365,'2017-04-24 14:16:04',41,35),(1471,'2017-05-09 15:51:34',43,36),(833,'2017-03-31 19:55:24',40,37),(1518,'2017-05-10 20:52:12',47,38),(1519,'2017-05-10 21:06:59',43,39),(395,'2017-03-07 18:51:12',42,40),(1166,'2017-04-18 19:00:22',37,41),(1169,'2017-04-18 19:13:45',39,42),(1170,'2017-04-18 19:17:22',42,43),(1213,'2017-04-19 19:20:16',43,44),(1191,'2017-04-18 22:51:14',42,45),(1192,'2017-04-18 22:53:02',43,46),(1211,'2017-04-19 19:16:53',43,47),(1196,'2017-04-18 23:36:00',55,48),(1159,'2017-04-18 16:54:16',37,49),(1161,'2017-04-18 16:59:13',42,50),(1255,'2017-04-20 22:57:30',43,51),(1162,'2017-04-18 17:01:24',44,52),(1197,'2017-04-18 23:36:15',55,53),(96,'2017-02-08 18:05:44',48,55),(1201,'2017-04-18 23:55:42',47,56),(1175,'2017-04-18 21:22:51',52,57),(1511,'2017-05-10 20:22:47',56,58),(1171,'2017-04-18 19:24:41',47,59),(1184,'2017-04-18 21:55:26',41,60),(1188,'2017-04-18 21:56:58',87,61),(1185,'2017-04-18 21:55:57',41,62),(1210,'2017-04-19 19:15:45',14,63),(1208,'2017-04-19 19:03:54',13,64),(1209,'2017-04-19 19:12:53',14,65),(224,'2017-02-21 17:23:54',23,66),(1514,'2017-05-10 20:39:31',41,67),(1515,'2017-05-10 20:41:27',36,68),(407,'2017-03-14 18:23:55',36,69),(1517,'2017-05-10 20:45:09',28,70),(1216,'2017-04-19 21:19:58',41,71),(1215,'2017-04-19 21:19:58',39,71),(1230,'2017-04-20 09:09:04',43,72),(1229,'2017-04-20 09:09:04',42,72),(1228,'2017-04-20 09:09:04',41,72),(400,'2017-03-08 18:20:04',48,73),(279,'2017-02-22 12:19:16',23,75),(325,'2017-02-24 14:46:58',17,77),(1173,'2017-04-18 20:59:53',48,54),(373,'2017-03-06 09:16:51',17,74),(359,'2017-02-27 15:49:56',17,78),(1034,'2017-04-14 20:17:37',41,79),(483,'2017-03-22 17:48:29',42,21),(482,'2017-03-22 17:48:29',41,21),(1376,'2017-04-24 15:20:10',42,28),(1371,'2017-04-24 15:04:37',42,23),(372,'2017-03-03 14:53:07',14,81),(374,'2017-03-06 13:14:50',17,82),(394,'2017-03-07 17:27:36',15,83),(398,'2017-03-08 11:25:14',17,84),(404,'2017-03-10 13:38:39',98,85),(406,'2017-03-10 15:21:46',18,86),(1545,'2017-05-10 21:51:13',55,87),(1550,'2017-05-11 10:02:28',14,88),(1551,'2017-05-11 10:06:22',13,89),(987,'2017-04-12 23:55:51',45,90),(986,'2017-04-12 23:55:51',14,90),(1547,'2017-05-10 21:54:29',43,91),(1555,'2017-05-11 10:11:04',78,92),(751,'2017-03-29 14:44:00',63,93),(752,'2017-03-29 14:44:31',65,94),(1557,'2017-05-11 10:11:24',79,95),(1556,'2017-05-11 10:11:24',78,95),(1296,'2017-04-21 05:29:14',45,96),(1266,'2017-04-21 00:08:51',43,97),(1265,'2017-04-21 00:06:04',43,98),(1264,'2017-04-21 00:03:35',43,99),(1459,'2017-04-25 16:05:00',42,121),(1221,'2017-04-20 08:58:25',43,101),(1220,'2017-04-20 08:58:25',42,101),(1219,'2017-04-20 08:58:25',39,101),(1218,'2017-04-20 08:58:25',37,101),(1559,'2017-05-11 10:36:37',55,102),(1580,'2017-05-11 20:48:19',60,103),(488,'2017-03-23 09:23:49',25,104),(1579,'2017-05-11 20:41:00',65,105),(1578,'2017-05-11 20:41:00',64,105),(1577,'2017-05-11 20:41:00',63,105),(1584,'2017-05-11 21:20:04',15,106),(1434,'2017-04-25 11:14:34',66,107),(1590,'2017-05-11 21:26:05',42,108),(1589,'2017-05-11 21:26:05',41,108),(1582,'2017-05-11 21:15:31',60,109),(1581,'2017-05-11 21:15:31',42,109),(503,'2017-03-23 16:06:43',60,110),(1442,'2017-04-25 13:28:52',77,111),(1444,'2017-04-25 14:33:36',55,112),(1549,'2017-05-11 09:50:46',65,100),(1548,'2017-05-11 09:50:46',63,100),(1593,'2017-05-11 21:43:00',63,144),(1594,'2017-05-11 21:49:10',63,113),(1592,'2017-05-11 21:43:00',100,144),(1614,'2017-05-11 22:03:25',100,114),(1566,'2017-05-11 13:06:39',93,142),(1616,'2017-05-11 22:13:28',100,115),(1564,'2017-05-11 13:05:41',93,116),(1561,'2017-05-11 12:59:03',93,117),(626,'2017-03-27 12:08:38',37,118),(629,'2017-03-27 12:09:43',39,119),(627,'2017-03-27 12:09:03',41,120),(1458,'2017-04-25 16:05:00',41,121),(1257,'2017-04-20 23:00:25',43,122),(631,'2017-03-27 12:10:24',40,123),(858,'2017-04-04 14:00:22',50,124),(1353,'2017-04-21 22:08:28',37,125),(1352,'2017-04-21 22:05:48',39,126),(1349,'2017-04-21 21:55:38',42,132),(1347,'2017-04-21 21:53:59',14,133),(1456,'2017-04-25 16:02:19',14,134),(1345,'2017-04-21 21:46:22',87,135),(1344,'2017-04-21 21:43:09',43,136),(1343,'2017-04-21 21:38:04',43,137),(1342,'2017-04-21 21:33:28',49,138),(1340,'2017-04-21 21:29:30',47,139),(1339,'2017-04-21 21:23:44',55,140),(1591,'2017-05-11 21:39:06',100,141),(685,'2017-03-28 10:04:57',77,143),(1350,'2017-04-21 22:00:36',42,131),(1348,'2017-04-21 21:55:38',41,132),(1611,'2017-05-11 21:59:50',83,145),(1610,'2017-05-11 21:59:50',75,145),(978,'2017-04-12 15:52:49',45,146),(1575,'2017-05-11 13:36:44',81,147),(1569,'2017-05-11 13:25:14',22,148),(1301,'2017-04-21 05:54:35',55,149),(1297,'2017-04-21 05:40:05',56,150),(977,'2017-04-12 15:52:49',39,146),(976,'2017-04-12 15:52:49',37,146),(1317,'2017-04-21 16:24:22',47,151),(1281,'2017-04-21 03:19:59',55,152),(1306,'2017-04-21 13:51:50',57,153),(1304,'2017-04-21 13:40:48',15,154),(1282,'2017-04-21 03:20:28',55,155),(1283,'2017-04-21 03:20:36',55,156),(1286,'2017-04-21 03:22:26',43,157),(1285,'2017-04-21 03:22:26',39,157),(1284,'2017-04-21 03:22:26',37,157),(1305,'2017-04-21 13:51:50',55,153),(1275,'2017-04-21 01:32:41',55,158),(1274,'2017-04-21 01:32:29',55,159),(1270,'2017-04-21 01:08:08',15,160),(1271,'2017-04-21 01:10:00',15,161),(1262,'2017-04-20 23:18:42',43,162),(1261,'2017-04-20 23:10:40',43,163),(1269,'2017-04-21 01:06:41',55,164),(802,'2017-03-30 09:46:08',45,165),(1268,'2017-04-21 00:58:31',14,166),(1319,'2017-04-21 16:35:00',52,167),(1318,'2017-04-21 16:35:00',47,167),(1250,'2017-04-20 22:46:45',43,168),(807,'2017-03-30 17:23:46',18,20),(1321,'2017-04-21 16:43:30',82,221),(1303,'2017-04-21 13:34:11',55,222),(1232,'2017-04-20 14:39:07',55,171),(1573,'2017-05-11 13:33:48',77,172),(1150,'2017-04-17 22:26:45',55,173),(846,'2017-04-04 10:20:07',44,174),(1149,'2017-04-17 21:06:14',44,175),(1251,'2017-04-20 22:48:33',43,176),(877,'2017-04-05 21:39:39',48,177),(876,'2017-04-05 21:39:39',43,177),(1237,'2017-04-20 16:49:16',101,170),(1469,'2017-05-09 14:29:25',101,169),(1474,'2017-05-09 15:54:56',55,178),(1146,'2017-04-17 20:00:23',83,179),(1145,'2017-04-17 20:00:23',82,179),(1144,'2017-04-17 20:00:23',55,179),(1143,'2017-04-17 18:17:34',83,180),(1142,'2017-04-17 18:17:34',57,180),(1141,'2017-04-17 18:13:20',57,181),(1140,'2017-04-17 18:13:20',55,181),(1477,'2017-05-09 16:06:36',57,182),(1476,'2017-05-09 16:06:36',55,182),(1475,'2017-05-09 16:06:36',48,182),(1136,'2017-04-17 17:47:02',55,183),(1135,'2017-04-17 17:41:50',55,184),(1134,'2017-04-17 17:33:49',58,185),(1133,'2017-04-17 17:33:49',57,185),(1132,'2017-04-17 17:33:49',55,185),(1114,'2017-04-17 15:56:40',58,186),(1113,'2017-04-17 15:56:40',43,186),(1115,'2017-04-17 15:58:24',56,187),(1116,'2017-04-17 16:00:08',56,188),(1131,'2017-04-17 16:23:19',58,191),(1130,'2017-04-17 16:23:19',56,191),(1129,'2017-04-17 16:23:19',43,191),(1112,'2017-04-17 15:47:44',55,192),(1111,'2017-04-17 15:45:50',55,193),(1472,'2017-05-09 15:53:39',55,194),(1109,'2017-04-17 15:37:59',39,195),(1473,'2017-05-09 15:54:00',43,196),(1105,'2017-04-17 15:29:09',42,197),(1108,'2017-04-17 15:35:30',43,198),(1107,'2017-04-17 15:35:30',42,198),(1522,'2017-05-10 21:11:48',42,199),(1520,'2017-05-10 21:10:04',37,200),(1530,'2017-05-10 21:20:58',42,201),(1529,'2017-05-10 21:20:37',42,202),(1528,'2017-05-10 21:20:37',41,202),(1535,'2017-05-10 21:22:53',42,203),(1534,'2017-05-10 21:22:53',41,203),(1533,'2017-05-10 21:22:34',46,204),(1532,'2017-05-10 21:22:34',42,204),(1521,'2017-05-10 21:11:48',41,199),(1527,'2017-05-10 21:16:16',43,205),(1526,'2017-05-10 21:16:16',42,205),(1525,'2017-05-10 21:16:16',41,205),(1524,'2017-05-10 21:16:16',37,205),(1315,'2017-04-21 16:18:33',38,206),(1077,'2017-04-17 11:48:10',43,207),(1076,'2017-04-17 11:48:10',42,207),(1075,'2017-04-17 11:46:57',43,208),(1074,'2017-04-17 11:46:57',42,208),(1073,'2017-04-17 11:46:10',43,209),(1072,'2017-04-17 11:46:10',42,209),(1523,'2017-05-10 21:13:13',43,218),(1307,'2017-04-21 14:24:47',100,211),(1308,'2017-04-21 14:25:03',65,212),(1033,'2017-04-14 19:43:16',58,213),(1032,'2017-04-14 19:43:16',43,213),(1489,'2017-05-09 20:06:47',48,214),(1488,'2017-05-09 20:06:47',47,214),(1609,'2017-05-11 21:59:50',74,145),(1465,'2017-05-01 20:50:53',44,215),(1044,'2017-04-14 21:05:51',43,216),(1494,'2017-05-09 20:33:58',44,234),(1504,'2017-05-10 14:44:51',78,217),(1503,'2017-05-10 14:44:51',102,217),(1531,'2017-05-10 21:22:34',41,204),(1608,'2017-05-11 21:59:50',100,145),(1613,'2017-05-11 22:03:25',47,114),(1612,'2017-05-11 22:03:25',46,114),(1158,'2017-04-18 16:54:01',39,219),(1187,'2017-04-18 21:56:58',45,61),(1186,'2017-04-18 21:55:57',45,62),(1513,'2017-05-10 20:23:18',48,220),(1512,'2017-05-10 20:23:18',47,220),(1576,'2017-05-11 20:36:33',63,223),(1384,'2017-04-24 16:42:22',41,224),(1385,'2017-04-24 16:42:22',43,224),(1386,'2017-04-24 17:12:19',37,225),(1387,'2017-04-24 17:12:19',41,225),(1388,'2017-04-24 17:12:19',43,225),(1461,'2017-05-01 16:16:22',41,226),(1462,'2017-05-01 16:16:22',42,226),(1463,'2017-05-01 16:16:22',43,226),(1464,'2017-05-01 16:16:22',55,226),(1478,'2017-05-09 18:00:58',83,229),(1479,'2017-05-09 19:08:46',83,230),(1480,'2017-05-09 19:38:00',47,231),(1481,'2017-05-09 19:38:00',48,231),(1482,'2017-05-09 19:38:00',51,231),(1483,'2017-05-09 19:38:00',54,231),(1484,'2017-05-09 19:38:00',92,231),(1485,'2017-05-09 19:59:47',47,232),(1495,'2017-05-09 20:40:12',44,236),(1496,'2017-05-09 20:55:19',43,237),(1497,'2017-05-09 21:02:14',43,238),(1498,'2017-05-09 21:10:16',43,239),(1536,'2017-05-10 21:30:44',47,240),(1541,'2017-05-10 21:43:48',43,241),(1538,'2017-05-10 21:34:16',52,242),(1540,'2017-05-10 21:42:08',43,243),(1552,'2017-05-11 10:06:22',45,89);
/*!40000 ALTER TABLE `app_product_category` ENABLE KEYS */;
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
