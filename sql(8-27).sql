-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cybertrackerdb
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.13-MariaDB

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
-- Table structure for table `forest_condition_observations`
--

DROP TABLE IF EXISTS `forest_condition_observations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forest_condition_observations` (
  `forest_condition_observation_id` int(11) NOT NULL AUTO_INCREMENT,
  `forest_condition_type` varchar(45) NOT NULL,
  `presence_of_regenerants` varchar(45) NOT NULL,
  `density_of_regenerants` varchar(45) NOT NULL,
  PRIMARY KEY (`forest_condition_observation_id`),
  UNIQUE KEY `forest_condition_observations_id_UNIQUE` (`forest_condition_observation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forest_condition_observations`
--

LOCK TABLES `forest_condition_observations` WRITE;
/*!40000 ALTER TABLE `forest_condition_observations` DISABLE KEYS */;
INSERT INTO `forest_condition_observations` VALUES (1,'OLD_GROWTH_FOREST','1','SPARSE'),(2,'OLD_GROWTH_FOREST','0','SPARSE'),(3,'OLD_GROWTH_FOREST','0','SPARSE'),(4,'OLD_GROWTH_FOREST','0','SPARSE'),(5,'OLD_GROWTH_FOREST','0','SPARSE'),(6,'OLD_GROWTH_FOREST','1','DENSE'),(7,'OLD_GROWTH_FOREST','1','DENSE'),(8,'OLD_GROWTH_FOREST','1','DENSE'),(9,'OLD_GROWTH_FOREST','1','DENSE'),(10,'OLD_GROWTH_FOREST','1','DENSE'),(11,'OLD_GROWTH_FOREST','1','DENSE'),(12,'OLD_GROWTH_FOREST','1','DENSE'),(13,'OLD_GROWTH_FOREST','1','DENSE'),(14,'OLD_GROWTH_FOREST','1','DENSE'),(15,'OLD_GROWTH_FOREST','1','DENSE'),(16,'OLD_GROWTH_FOREST','1','DENSE'),(17,'OLD_GROWTH_FOREST','1','DENSE'),(18,'OLD_GROWTH_FOREST','1','DENSE'),(19,'OLD_GROWTH_FOREST','1','DENSE'),(20,'OLD_GROWTH_FOREST','1','DENSE'),(21,'OLD_GROWTH_FOREST','1','DENSE'),(22,'OLD_GROWTH_FOREST','1','DENSE'),(23,'OLD_GROWTH_FOREST','1','DENSE'),(24,'OLD_GROWTH_FOREST','1','DENSE'),(25,'OLD_GROWTH_FOREST','1','DENSE'),(26,'OLD_GROWTH_FOREST','1','DENSE'),(27,'OLD_GROWTH_FOREST','1','DENSE'),(28,'OLD_GROWTH_FOREST','1','DENSE'),(29,'OLD_GROWTH_FOREST','1','DENSE'),(30,'OLD_GROWTH_FOREST','1','DENSE'),(31,'OLD_GROWTH_FOREST','1','DENSE'),(32,'OLD_GROWTH_FOREST','1','DENSE'),(33,'OLD_GROWTH_FOREST','1','DENSE'),(34,'OLD_GROWTH_FOREST','1','DENSE'),(35,'OLD_GROWTH_FOREST','1','DENSE'),(36,'ADVANCE_SECONDARY_FOREST','1','MODERATE'),(37,'OLD_GROWTH_FOREST','1','DENSE'),(38,'OLD_GROWTH_FOREST','1','DENSE'),(39,'OLD_GROWTH_FOREST','1','DENSE'),(40,'OLD_GROWTH_FOREST','1','DENSE');
/*!40000 ALTER TABLE `forest_condition_observations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lookup_species_type`
--

DROP TABLE IF EXISTS `lookup_species_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lookup_species_type` (
  `idlookup_species_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `species` varchar(45) NOT NULL,
  PRIMARY KEY (`idlookup_species_type_id`),
  UNIQUE KEY `idlookup_species_type_id_UNIQUE` (`idlookup_species_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lookup_species_type`
--

LOCK TABLES `lookup_species_type` WRITE;
/*!40000 ALTER TABLE `lookup_species_type` DISABLE KEYS */;
INSERT INTO `lookup_species_type` VALUES (1,'Labrador','MAMMAL'),(2,'Kangkong','FLORA'),(3,'Bibe','BIRD'),(4,'psyduck','BIRD');
/*!40000 ALTER TABLE `lookup_species_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lookup_threat_type`
--

DROP TABLE IF EXISTS `lookup_threat_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lookup_threat_type` (
  `lookup_threat_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`lookup_threat_type_id`),
  UNIQUE KEY `lookup_threat_type_id_UNIQUE` (`lookup_threat_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lookup_threat_type`
--

LOCK TABLES `lookup_threat_type` WRITE;
/*!40000 ALTER TABLE `lookup_threat_type` DISABLE KEYS */;
INSERT INTO `lookup_threat_type` VALUES (1,'Threat 1'),(2,'Threat 2'),(3,'Threat 3'),(4,'test');
/*!40000 ALTER TABLE `lookup_threat_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_observations`
--

DROP TABLE IF EXISTS `other_observations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_observations` (
  `other_observation_id` int(11) NOT NULL AUTO_INCREMENT,
  `note` varchar(45) NOT NULL,
  PRIMARY KEY (`other_observation_id`),
  UNIQUE KEY `other_observation_id_UNIQUE` (`other_observation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_observations`
--

LOCK TABLES `other_observations` WRITE;
/*!40000 ALTER TABLE `other_observations` DISABLE KEYS */;
INSERT INTO `other_observations` VALUES (1,'Note'),(2,'Note'),(3,'44'),(4,'4'),(5,'test'),(6,'test');
/*!40000 ALTER TABLE `other_observations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patrol_locations`
--

DROP TABLE IF EXISTS `patrol_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patrol_locations` (
  `patrol_location_id` int(32) NOT NULL AUTO_INCREMENT,
  `patrol_id` int(32) NOT NULL,
  `latitude` varchar(20) NOT NULL,
  `longitude` varchar(20) NOT NULL,
  `date` datetime NOT NULL,
  `region` varchar(45) NOT NULL,
  PRIMARY KEY (`patrol_location_id`),
  UNIQUE KEY `id_UNIQUE` (`patrol_location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_locations`
--

LOCK TABLES `patrol_locations` WRITE;
/*!40000 ALTER TABLE `patrol_locations` DISABLE KEYS */;
INSERT INTO `patrol_locations` VALUES (93,1,'14.5510789','121.0270765','2016-08-14 17:25:26','Metro Manila'),(94,1,'14.5511312','121.0265776','2016-08-14 17:27:01','Metro Manila'),(95,1,'14.5530303','121.0263622','2016-08-14 17:28:47','Metro Manila'),(96,1,'14.5530089','121.0260898','2016-08-14 17:31:02','Metro Manila'),(97,1,'14.5543883','121.0256121','2016-08-14 17:33:53','Metro Manila'),(98,1,'14.5546481','121.0246874','2016-08-14 17:36:47','Metro Manila'),(99,1,'14.5547029','121.0243369','2016-08-14 17:38:13','Metro Manila'),(100,1,'14.5576718','121.0194902','2016-08-14 17:39:26','Metro Manila'),(101,1,'14.55951','121.0172622','2016-08-14 17:41:29','Metro Manila'),(102,1,'14.5713117','121.0190398','2016-08-14 17:45:12','Metro Manila'),(103,1,'14.5767843','121.0150608','2016-08-14 17:49:40','Metro Manila'),(104,1,'14.5806205','121.01077','2016-08-14 17:53:10','Metro Manila'),(105,1,'14.5813449','121.0081524','2016-08-14 17:56:28','Metro Manila'),(106,1,'14.5813039','121.0084901','2016-08-14 17:57:56','Metro Manila'),(107,1,'14.5812665','121.0084556','2016-08-14 17:59:50','Metro Manila'),(108,1,'14.5812502','121.0084379','2016-08-14 18:01:56','Metro Manila'),(109,1,'14.5811353','121.0085684','2016-08-14 18:03:48','Metro Manila'),(110,1,'14.581348','121.0086331','2016-08-14 18:05:49','Metro Manila'),(111,1,'14.5814621','121.0085895','2016-08-14 18:07:56','Metro Manila'),(112,1,'14.5814777','121.0085444','2016-08-14 19:11:42','Metro Manila'),(113,1,'14.5812945','121.0112601','2016-08-14 19:13:36','Metro Manila'),(114,1,'14.5814125','121.0121713','2016-08-14 19:15:34',''),(115,1,'14.5874962','121.0201707','2016-08-14 19:19:20','Metro Manila'),(116,1,'14.5869567','121.0136568','2016-08-14 19:22:21','Metro Manila'),(117,1,'14.5875458','121.0116245','2016-08-14 19:25:21','Metro Manila'),(118,1,'14.5865515','121.0116168','2016-08-14 19:26:57','Metro Manila'),(119,1,'14.5858604','121.0115151','2016-08-14 19:29:23','Metro Manila'),(120,1,'14.585591','121.0090849','2016-08-14 19:31:53','Metro Manila'),(121,1,'14.5849674','121.0078147','2016-08-14 19:33:55','Metro Manila'),(122,1,'14.5849422','121.0077252','2016-08-14 19:35:00','Metro Manila'),(123,2,'14.5849266','121.0076959','2016-08-17 23:54:53','NCR'),(124,2,'14.5849486','121.0077442','2016-08-17 23:55:54','NCR'),(125,3,'14.5848893','121.0077375','2016-08-26 12:46:46','Metro Manila'),(126,3,'14.5849106','121.0077315','2016-08-26 12:47:55','Metro Manila'),(127,3,'14.5848704','121.0077368','2016-08-26 12:49:54','Metro Manila'),(128,3,'14.5848931','121.0077818','2016-08-26 12:51:28','Metro Manila'),(129,3,'14.5848658','121.0077652','2016-08-26 12:53:29','Metro Manila'),(130,3,'14.5849123','121.0077325','2016-08-26 12:54:57','Metro Manila'),(131,3,'14.5848735','121.0077588','2016-08-26 12:56:21','Metro Manila'),(132,3,'14.584898','121.0077803','2016-08-26 12:57:56','Metro Manila'),(133,3,'14.5848757','121.00776','2016-08-26 12:59:21',''),(134,3,'14.5848878','121.0077812','2016-08-26 13:00:26',''),(135,3,'14.5851071','121.0107777','2016-08-26 13:03:18',''),(136,3,'14.5834767','121.0115158','2016-08-26 13:05:25',''),(137,3,'14.583322','121.0114728','2016-08-26 13:07:21',''),(138,3,'14.58333','121.0114915','2016-08-26 13:09:22',''),(139,3,'14.5821987','121.0122558','2016-08-26 13:11:53',''),(140,3,'14.5816474','121.012252','2016-08-26 13:14:22',''),(141,3,'14.5801011','121.0075345','2016-08-26 13:16:03',''),(142,3,'14.5801086','121.0073718','2016-08-26 13:18:10',''),(143,3,'14.5800882','121.0073355','2016-08-26 13:20:15',''),(144,3,'14.5793598','121.0085331','2016-08-26 13:22:52',''),(145,3,'14.5760602','121.0115226','2016-08-26 13:24:52',''),(146,3,'14.5727331','121.0137946','2016-08-26 13:27:07','Metro Manila'),(147,3,'14.5711845','121.0145223','2016-08-26 13:29:13','Metro Manila'),(148,3,'14.5675094','121.0213241','2016-08-26 13:31:54','Metro Manila'),(149,3,'14.566759','121.023279','2016-08-26 13:33:33','Metro Manila'),(150,3,'14.5649291','121.0228939','2016-08-26 13:36:56','Metro Manila'),(151,3,'14.5627231','121.0222753','2016-08-26 13:39:53','Metro Manila'),(152,3,'14.5626377','121.0218551','2016-08-26 13:41:26','Metro Manila'),(153,3,'14.5626377','121.0218551','2016-08-26 13:42:31','Metro Manila'),(154,3,'14.5626377','121.0218551','2016-08-26 13:43:36','Metro Manila'),(155,3,'14.5626377','121.0218551','2016-08-26 13:44:41','Metro Manila'),(156,3,'14.5626377','121.0218551','2016-08-26 13:45:46','Metro Manila');
/*!40000 ALTER TABLE `patrol_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patrol_observation_images`
--

DROP TABLE IF EXISTS `patrol_observation_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patrol_observation_images` (
  `patrol_observation_image_id` int(11) NOT NULL AUTO_INCREMENT,
  `observation_id` int(11) NOT NULL,
  `image_location` varchar(250) NOT NULL,
  `longitude` varchar(45) NOT NULL,
  `latitude` varchar(45) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`patrol_observation_image_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_observation_images`
--

LOCK TABLES `patrol_observation_images` WRITE;
/*!40000 ALTER TABLE `patrol_observation_images` DISABLE KEYS */;
INSERT INTO `patrol_observation_images` VALUES (19,2,'C:\\cybertracker-images\\obsimg1470840600252.jpg','121.0077297','14.5848814','2016-08-10 22:48:05'),(20,4,'C:\\cybertracker-images\\obsimg1471176944920.jpg','121.0270765','14.5510789','2016-08-14 12:41:47'),(21,1,'C:\\cybertracker-images\\obsimg1471449364845.jpg','121.0077664','14.5850124','2016-08-17 23:55:30'),(22,52,'C:\\cybertracker-images\\obsimg1472193849141.jpg','121.0218551','14.5626377','2016-08-26 13:44:21'),(23,52,'C:\\cybertracker-images\\obsimg1472193851789.jpg','121.0218551','14.5626377','2016-08-26 13:44:33'),(24,53,'C:\\cybertracker-images\\obsimg1472193853346.jpg','121.0077418','14.5848125','2016-08-26 12:47:15');
/*!40000 ALTER TABLE `patrol_observation_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patrol_observations`
--

DROP TABLE IF EXISTS `patrol_observations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patrol_observations` (
  `patrol_observation_id` int(11) NOT NULL AUTO_INCREMENT,
  `patrol_id` int(11) NOT NULL,
  `observation_id` int(11) NOT NULL,
  `observation_type` varchar(45) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`patrol_observation_id`),
  UNIQUE KEY `patrol_observations_id_UNIQUE` (`patrol_observation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_observations`
--

LOCK TABLES `patrol_observations` WRITE;
/*!40000 ALTER TABLE `patrol_observations` DISABLE KEYS */;
INSERT INTO `patrol_observations` VALUES (1,1,1,'FOREST_CONDITION','2016-08-01 12:01:45','2016-08-01 12:01:45'),(2,1,2,'FOREST_CONDITION','2016-08-01 12:01:45','2016-08-01 12:01:45'),(3,1,3,'FOREST_CONDITION','2016-08-01 12:01:45','2016-08-01 12:01:45'),(4,1,4,'FOREST_CONDITION','2016-08-01 12:01:45','2016-08-01 12:01:45'),(5,1,5,'FOREST_CONDITION','2016-08-01 12:01:45','2016-08-01 12:01:45'),(6,1,1,'WILDLIFE','2016-08-01 12:01:45','2016-08-01 12:01:45'),(7,1,2,'WILDLIFE','2016-08-01 12:01:45','2016-08-01 12:01:45'),(8,1,3,'WILDLIFE','2016-08-01 12:01:45','2016-08-01 12:01:45'),(9,1,1,'THREATS','2016-08-01 12:01:45','2016-08-01 12:01:45'),(10,1,2,'THREATS','2016-08-01 12:01:45','2016-08-01 12:01:45'),(11,1,1,'OTHER_OBSERVATIONS','2016-08-01 12:01:45','2016-08-01 12:01:45'),(12,13,2,'OTHER_OBSERVATIONS','2016-08-01 12:01:45','2016-08-01 12:01:45'),(13,15,6,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(14,16,7,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(15,17,8,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(16,18,9,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(17,19,10,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(18,20,11,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(19,21,12,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(20,22,13,'FOREST_CONDITION','2016-08-01 17:24:41','2016-08-01 17:25:09'),(21,23,14,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(22,24,15,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(23,25,16,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(24,26,17,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(25,27,18,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(26,28,19,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(27,29,20,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(28,30,21,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(29,31,22,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(30,32,23,'FOREST_CONDITION','2016-08-01 21:14:59','2016-08-01 21:15:11'),(31,33,24,'FOREST_CONDITION','2016-08-01 21:14:59','2016-08-01 21:15:11'),(32,34,25,'FOREST_CONDITION','2016-08-01 21:14:59','2016-08-01 21:15:11'),(33,35,26,'FOREST_CONDITION','2016-08-01 21:14:59','2016-08-01 21:15:11'),(34,36,27,'FOREST_CONDITION','2016-08-01 21:14:59','2016-08-01 21:15:11'),(35,37,28,'FOREST_CONDITION','2016-08-01 19:59:40','2016-08-01 19:59:43'),(36,38,3,'OTHER_OBSERVATIONS','2016-08-01 21:56:02','2016-08-01 21:56:33'),(37,38,29,'FOREST_CONDITION','2016-08-01 21:55:32','2016-08-01 21:55:58'),(38,39,30,'FOREST_CONDITION','2016-08-01 22:16:54','2016-08-01 22:17:04'),(39,40,31,'FOREST_CONDITION','2016-08-01 22:18:34','2016-08-01 22:18:36'),(40,41,32,'FOREST_CONDITION','2016-08-01 22:19:41','2016-08-01 22:19:58'),(41,42,33,'FOREST_CONDITION','2016-08-01 22:20:48','2016-08-01 22:21:00'),(42,43,34,'FOREST_CONDITION','2016-08-01 22:21:51','2016-08-01 22:22:03'),(43,43,4,'OTHER_OBSERVATIONS','2016-08-01 22:21:40','2016-08-01 22:21:41'),(44,44,35,'FOREST_CONDITION','2016-08-01 22:34:25','2016-08-01 22:34:46'),(45,48,36,'FOREST_CONDITION','2016-08-10 21:35:05','2016-08-10 21:35:18'),(46,1,3,'THREATS','2016-08-10 21:59:52','2016-08-10 22:00:06'),(47,49,5,'OTHER_OBSERVATIONS','2016-08-10 22:47:51','2016-08-10 22:48:11'),(48,51,6,'OTHER_OBSERVATIONS','2016-08-10 22:47:51','2016-08-10 22:48:11'),(49,52,37,'FOREST_CONDITION','2016-08-14 10:55:07','2016-08-14 10:55:09'),(50,53,38,'FOREST_CONDITION','2016-08-14 12:41:32','2016-08-14 12:41:59'),(51,2,4,'THREATS','2016-08-17 23:55:31','2016-08-17 23:55:51'),(52,3,39,'FOREST_CONDITION','2016-08-26 13:44:09','2016-08-26 13:44:37'),(53,3,40,'FOREST_CONDITION','2016-08-26 12:46:50','2016-08-26 12:47:25');
/*!40000 ALTER TABLE `patrol_observations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patrol_patrollers`
--

DROP TABLE IF EXISTS `patrol_patrollers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patrol_patrollers` (
  `patrol_patroller_id` int(11) NOT NULL AUTO_INCREMENT,
  `patrol_id` int(11) NOT NULL,
  `patroller_id` int(11) NOT NULL,
  PRIMARY KEY (`patrol_patroller_id`),
  UNIQUE KEY `patrol_patroller_id_UNIQUE` (`patrol_patroller_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_patrollers`
--

LOCK TABLES `patrol_patrollers` WRITE;
/*!40000 ALTER TABLE `patrol_patrollers` DISABLE KEYS */;
INSERT INTO `patrol_patrollers` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,1),(6,2,2),(7,2,3),(8,3,1),(9,3,2);
/*!40000 ALTER TABLE `patrol_patrollers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patroller_teams`
--

DROP TABLE IF EXISTS `patroller_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patroller_teams` (
  `patroller_team_id` int(11) NOT NULL AUTO_INCREMENT,
  `patroller_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  PRIMARY KEY (`patroller_team_id`),
  UNIQUE KEY `patroller_team_id_UNIQUE` (`patroller_team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patroller_teams`
--

LOCK TABLES `patroller_teams` WRITE;
/*!40000 ALTER TABLE `patroller_teams` DISABLE KEYS */;
INSERT INTO `patroller_teams` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,1,2),(6,2,2),(7,3,2),(8,2,3),(9,3,4);
/*!40000 ALTER TABLE `patroller_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patrollers`
--

DROP TABLE IF EXISTS `patrollers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patrollers` (
  `patroller_id` int(11) NOT NULL AUTO_INCREMENT,
  `patroller_name` varchar(45) NOT NULL,
  PRIMARY KEY (`patroller_id`),
  UNIQUE KEY `id_UNIQUE` (`patroller_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrollers`
--

LOCK TABLES `patrollers` WRITE;
/*!40000 ALTER TABLE `patrollers` DISABLE KEYS */;
INSERT INTO `patrollers` VALUES (1,'arjay'),(2,'sy'),(3,' kirk'),(4,'franz'),(16,'test');
/*!40000 ALTER TABLE `patrollers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patrols`
--

DROP TABLE IF EXISTS `patrols`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patrols` (
  `patrol_id` int(11) NOT NULL AUTO_INCREMENT,
  `patrol_name` varchar(45) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `patroller_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`patrol_id`),
  UNIQUE KEY `patrol_id_UNIQUE` (`patrol_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrols`
--

LOCK TABLES `patrols` WRITE;
/*!40000 ALTER TABLE `patrols` DISABLE KEYS */;
INSERT INTO `patrols` VALUES (1,'glo patrol','2016-08-14 17:25:25','2016-08-14 19:35:47','sy'),(2,'test','2016-08-17 23:54:53','2016-08-17 23:55:57','arjay'),(3,'arjay-school','2016-08-26 12:46:45','2016-08-26 13:45:52','arjay');
/*!40000 ALTER TABLE `patrols` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams` (
  `team_id` int(11) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(45) NOT NULL,
  PRIMARY KEY (`team_id`),
  UNIQUE KEY `id_UNIQUE` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,'team1'),(2,'team2'),(3,'team3'),(4,'team4'),(5,'team5');
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `threat_observations`
--

DROP TABLE IF EXISTS `threat_observations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `threat_observations` (
  `threat_observation_id` int(11) NOT NULL AUTO_INCREMENT,
  `threat_type` varchar(45) NOT NULL,
  `distance_of_threat_from_waypoint` int(11) NOT NULL,
  `bearing_of_threat_from_waypoint` int(11) NOT NULL,
  `response_to_threat` varchar(45) NOT NULL,
  `note` varchar(45) NOT NULL,
  PRIMARY KEY (`threat_observation_id`),
  UNIQUE KEY `threat_observations_id_UNIQUE` (`threat_observation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `threat_observations`
--

LOCK TABLES `threat_observations` WRITE;
/*!40000 ALTER TABLE `threat_observations` DISABLE KEYS */;
INSERT INTO `threat_observations` VALUES (1,'Threat 1',1,2,'ACTED_UPON','Note'),(2,'Threat 2',2,2,'ACTED_UPON','Note'),(3,'Threat 2',5,5,'ACTED_UPON','abc'),(4,'Threat 1',5,5,'ACTED_UPON','h'),(5,'Threat 3',1,9,'ACTED_UPON','rar'),(6,'Threat 3',45,3,'ACTED_UPON','a'),(7,'Threat 3',2,6,'ACTED_UPON','b'),(8,'Threat 3',9,7,'ACTED_UPON','c'),(9,'Threat 3',8,4,'ACTED_UPON','d'),(10,'Threat 3',5,1,'ACTED_UPON','e'),(11,'test',2,1,'ACTED_UPON','f');
/*!40000 ALTER TABLE `threat_observations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin','Administrator'),(2,'arjay','arjay','Arjay Francisco'),(3,'jeggo','jeggo','Jeggo Fernando'),(4,'kurt','kurt','Kurt Gonzales');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wildlife_observations`
--

DROP TABLE IF EXISTS `wildlife_observations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wildlife_observations` (
  `wildlife_observation_id` int(11) NOT NULL AUTO_INCREMENT,
  `wildlife_observation_type` varchar(45) NOT NULL,
  `species` varchar(45) NOT NULL,
  `species_type` varchar(45) NOT NULL,
  `no_of_male_adults` int(11) DEFAULT NULL,
  `no_of_female_adults` int(11) DEFAULT NULL,
  `no_of_young` int(11) DEFAULT NULL,
  `no_of_undetermined` int(11) DEFAULT NULL,
  `action_taken` varchar(45) DEFAULT NULL,
  `observed_through_hunting` varchar(45) DEFAULT NULL,
  `diameter` int(11) DEFAULT NULL,
  `no_of_trees` int(11) DEFAULT NULL,
  `observed_through_gathering` varchar(45) DEFAULT NULL,
  `other_tree_species_observed` varchar(45) DEFAULT NULL,
  `evidences` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`wildlife_observation_id`),
  UNIQUE KEY `wildlife_observations_id_UNIQUE` (`wildlife_observation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wildlife_observations`
--

LOCK TABLES `wildlife_observations` WRITE;
/*!40000 ALTER TABLE `wildlife_observations` DISABLE KEYS */;
INSERT INTO `wildlife_observations` VALUES (1,'DIRECT','FLORA','Kangkong',NULL,NULL,NULL,NULL,NULL,NULL,4,5,'0','none',NULL),(2,'DIRECT','BIRD','Bibe',1,2,3,4,'OBSERVED','1',NULL,NULL,NULL,NULL,NULL),(3,'INDIRECT','BIRD','psyduck',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'wee');
/*!40000 ALTER TABLE `wildlife_observations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-27 23:18:16
