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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forest_condition_observations`
--

LOCK TABLES `forest_condition_observations` WRITE;
/*!40000 ALTER TABLE `forest_condition_observations` DISABLE KEYS */;
INSERT INTO `forest_condition_observations` VALUES (1,'OLD_GROWTH_FOREST','1','DENSE'),(2,'EARLY_SECONDARY_FOREST','1','SPARSE');
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
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idlookup_species_type_id`),
  UNIQUE KEY `idlookup_species_type_id_UNIQUE` (`idlookup_species_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lookup_species_type`
--

LOCK TABLES `lookup_species_type` WRITE;
/*!40000 ALTER TABLE `lookup_species_type` DISABLE KEYS */;
INSERT INTO `lookup_species_type` VALUES (1,'Philippine Eagle-Owl','BIRD',1),(2,'Camiguin Forest Rat','MAMMAL',1),(3,' Philippine Dwarf Kingfisher','BIRD',1),(4,'Palawan Bearded Pig','MAMMAL',1),(5,'Philippine Freshwater Crocodile','REPTILES',1),(6,'Philippine Tarsier','MAMMAL',1),(7,'Mayapis','FLORA',1),(8,'Loggerhead Sea Turtle','REPTILES',1),(9,'Philippine Teak','FLORA',1),(10,'Tamaraw ','MAMMAL',1);
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
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`lookup_threat_type_id`),
  UNIQUE KEY `lookup_threat_type_id_UNIQUE` (`lookup_threat_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lookup_threat_type`
--

LOCK TABLES `lookup_threat_type` WRITE;
/*!40000 ALTER TABLE `lookup_threat_type` DISABLE KEYS */;
INSERT INTO `lookup_threat_type` VALUES (1,'Illegal Logging',1),(2,'Climate Change',1),(3,'Biodiversity Loss',1),(4,'Forest Fire',1),(5,'Drought',1),(6,'Threat 6a',1);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_observations`
--

LOCK TABLES `other_observations` WRITE;
/*!40000 ALTER TABLE `other_observations` DISABLE KEYS */;
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
  `city` varchar(45) NOT NULL,
  `street` varchar(45) NOT NULL,
  PRIMARY KEY (`patrol_location_id`),
  UNIQUE KEY `id_UNIQUE` (`patrol_location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_locations`
--

LOCK TABLES `patrol_locations` WRITE;
/*!40000 ALTER TABLE `patrol_locations` DISABLE KEYS */;
INSERT INTO `patrol_locations` VALUES (1,12,'9.207964','124.720166','2016-09-04 20:23:50','Metro Manila','Manila','Sta. Ana'),(2,12,'9.208176','124.721410','2016-09-04 20:24:52','Metro Manila','Manila','Sta. Ana'),(3,12,'9.207074','124.723084','2016-09-04 20:20:30','Metro Manila','Manila','Sta. Ana'),(4,12,'9.206185','124.723427','2016-09-04 20:21:34','Metro Manila','Manila','Sta. Ana'),(5,14,'9.205634','124.718964','2016-09-04 20:21:34','Metro Manila','Manila','Street Boys'),(6,14,'9.202203','124.719350','2016-09-04 20:21:34','Metro Manila','Manila','Street Boys'),(7,14,'9.201525','124.719908','2016-09-04 20:21:34','Metro Manila','Manila','Street Boys'),(8,14,'9.201737','124.721324','2016-09-04 20:21:34','Metro Manila','Manila','Street Boys'),(9,15,'14.581490','121.008243','2016-09-04 20:21:34','Bicol','Albay','Back Street'),(10,15,'14.580047','121.007498','2016-09-04 20:21:34','Bicol','Albay','Back Street'),(11,16,'14.580047','121.007498','2016-09-04 20:21:34','Metro Manila','Makati','Street Wise');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_observation_images`
--

LOCK TABLES `patrol_observation_images` WRITE;
/*!40000 ALTER TABLE `patrol_observation_images` DISABLE KEYS */;
INSERT INTO `patrol_observation_images` VALUES (1,2,'C:\\cybertracker-images\\obsimg1472997068323.jpg','121.0086951','14.5817335','2016-09-04 20:24:35'),(2,3,'C:\\cybertracker-images\\obsimg1472997072830.jpg','121.0086956','14.5817336','2016-09-04 20:24:09'),(3,5,'C:\\cybertracker-images\\obsimg1472997080550.jpg','121.0086985','14.5817327','2016-09-04 20:21:22');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_observations`
--

LOCK TABLES `patrol_observations` WRITE;
/*!40000 ALTER TABLE `patrol_observations` DISABLE KEYS */;
INSERT INTO `patrol_observations` VALUES (1,12,1,'THREATS','2016-09-04 20:24:42','2016-09-04 20:25:21'),(2,12,1,'WILDLIFE','2016-09-04 20:24:15','2016-09-04 20:24:38'),(3,12,1,'FOREST_CONDITION','2016-09-04 20:23:53','2016-09-04 20:24:13'),(4,13,2,'THREATS','2016-09-04 20:21:36','2016-09-04 20:22:01'),(5,13,2,'WILDLIFE','2016-09-04 20:20:49','2016-09-04 20:21:27'),(6,13,2,'FOREST_CONDITION','2016-09-04 20:20:34','2016-09-04 20:20:42');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrol_patrollers`
--

LOCK TABLES `patrol_patrollers` WRITE;
/*!40000 ALTER TABLE `patrol_patrollers` DISABLE KEYS */;
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
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`patroller_id`),
  UNIQUE KEY `id_UNIQUE` (`patroller_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrollers`
--

LOCK TABLES `patrollers` WRITE;
/*!40000 ALTER TABLE `patrollers` DISABLE KEYS */;
INSERT INTO `patrollers` VALUES (1,'Juan Dela Cruz',1),(2,'John Doe',1),(3,'Korina Sanchez',1),(4,'Noli De Castro',1),(5,'Julius Babao',1),(6,'Ted Failon',1),(7,'Bernadette Sembrano',1),(8,'Kim Atienza',1),(9,'Karen Davilla',1),(10,'Gretchen Fullido',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrols`
--

LOCK TABLES `patrols` WRITE;
/*!40000 ALTER TABLE `patrols` DISABLE KEYS */;
INSERT INTO `patrols` VALUES (12,'Subic Forest Patrol','2016-09-04 20:23:49','2016-09-04 20:25:24','Juan Dela Cruz'),(14,'Test Patrol','2016-09-04 20:23:49','2016-09-04 20:25:24','John Doe'),(15,'Test Patrol 2','2016-09-04 20:23:49','2016-09-04 20:25:24','Ted Failon');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `threat_observations`
--

LOCK TABLES `threat_observations` WRITE;
/*!40000 ALTER TABLE `threat_observations` DISABLE KEYS */;
INSERT INTO `threat_observations` VALUES (1,'Forest Fire',600,10,'RESOLVED','just a small fire from a cigarette'),(2,'Climate Change',300,75,'RECORDED','N/A');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wildlife_observations`
--

LOCK TABLES `wildlife_observations` WRITE;
/*!40000 ALTER TABLE `wildlife_observations` DISABLE KEYS */;
INSERT INTO `wildlife_observations` VALUES (1,'INDIRECT','BIRD',' Philippine Dwarf Kingfisher',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Sound,Feeding,Tracks'),(2,'DIRECT','BIRD',' Philippine Dwarf Kingfisher',35,19,13,8,'COLLECTED','1',NULL,NULL,NULL,NULL,NULL);
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

-- Dump completed on 2016-09-14 23:53:03
