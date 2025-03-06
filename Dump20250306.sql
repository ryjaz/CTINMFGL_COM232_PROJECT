-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: steamdb
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `AdminID` int NOT NULL AUTO_INCREMENT,
  `AdminUname` varchar(50) NOT NULL,
  `AdminPword` varchar(255) NOT NULL,
  PRIMARY KEY (`AdminID`),
  UNIQUE KEY `AdminUname` (`AdminUname`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','admin123');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `GameID` int NOT NULL AUTO_INCREMENT,
  `GameName` varchar(50) NOT NULL,
  `OriginalPrice` int DEFAULT NULL,
  `DiscountPercent` int DEFAULT NULL,
  `FinalAmount` int DEFAULT NULL,
  PRIMARY KEY (`GameID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,'Example Game',NULL,NULL,NULL),(2,'Hello Kitty Island Adventure',3900,50,1950),(3,'Hello Kitty Island Adventure',3900,25,2925),(4,'Hello Kitty Island Adventure',3900,10,3510),(5,'Crossfire',899,69,279),(6,'Crossfire',899,20,719),(7,'Crossfire',899,5,854),(8,'Sims',199,20,159),(9,'Sims',199,10,179),(10,'Sims',199,2,195),(11,'Minecraft',1500,10,1350),(12,'Minecraft',1500,50,750),(13,'Minecraft',1500,100,0);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `library` (
  `GameID` int NOT NULL,
  `GameName` varchar(255) DEFAULT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Purchase` enum('Yes','No') NOT NULL,
  `UserID` int NOT NULL,
  PRIMARY KEY (`GameID`),
  CONSTRAINT `library_ibfk_1` FOREIGN KEY (`GameID`) REFERENCES `game` (`GameID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
INSERT INTO `library` VALUES (1,'Example Game','2025-03-05 22:04:32','Yes',0);
/*!40000 ALTER TABLE `library` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactiontable`
--

DROP TABLE IF EXISTS `transactiontable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactiontable` (
  `TransactionID` int NOT NULL AUTO_INCREMENT,
  `UserID` int DEFAULT NULL,
  `GameID` int DEFAULT NULL,
  `FinalAmount` decimal(10,2) DEFAULT NULL,
  `PaymentMethod` varchar(50) NOT NULL,
  PRIMARY KEY (`TransactionID`),
  KEY `fk_user` (`UserID`),
  KEY `fk_game` (`GameID`),
  CONSTRAINT `fk_game` FOREIGN KEY (`GameID`) REFERENCES `game` (`GameID`) ON DELETE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE,
  CONSTRAINT `transactiontable_ibfk_1` FOREIGN KEY (`GameID`) REFERENCES `game` (`GameID`) ON DELETE CASCADE,
  CONSTRAINT `transactiontable_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactiontable`
--

LOCK TABLES `transactiontable` WRITE;
/*!40000 ALTER TABLE `transactiontable` DISABLE KEYS */;
INSERT INTO `transactiontable` VALUES (2,1,6,899.00,'Credit Card'),(3,1,5,279.00,'Gcash'),(4,1,5,279.00,'Gcash'),(5,1,5,279.00,'Gcash'),(6,1,5,279.00,'Gcash'),(7,1,5,279.00,'Gcash'),(8,1,5,279.00,'Gcash'),(9,1,5,279.00,'Gcash'),(10,1,5,279.00,'Gcash'),(11,1,5,279.00,'Gcash'),(12,1,2,499.99,'Gcash'),(13,1,5,279.00,'Gcash'),(14,1,5,279.00,'Gcash'),(15,1,5,279.00,'Gcash'),(16,1,5,279.00,'Gcash'),(17,1,5,279.00,'Gcash'),(18,1,5,279.00,'Gcash'),(19,1,5,279.00,'Gcash'),(20,1,2,49.99,'Credit Card'),(21,1,5,279.00,'Gcash'),(22,1,5,279.00,'Gcash'),(23,1,5,279.00,'Gcash'),(24,1,5,279.00,'Gcash'),(25,1,5,279.00,'Gcash'),(26,1,5,279.00,'Gcash'),(27,1,5,279.00,'Gcash'),(28,1,5,279.00,'Gcash'),(29,1,5,279.00,'Gcash'),(30,1,11,1350.00,'Gcash'),(31,1,5,279.00,'Gcash'),(32,1,5,279.00,'Gcash');
/*!40000 ALTER TABLE `transactiontable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'danielpadilla','summer'),(2,'kathrynb','winter');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-06 17:31:00
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: userdb
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adminuserdb`
--

DROP TABLE IF EXISTS `adminuserdb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adminuserdb` (
  `adminuname` varchar(100) NOT NULL,
  `adminpword` varchar(255) NOT NULL,
  UNIQUE KEY `adminuname` (`adminuname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adminuserdb`
--

LOCK TABLES `adminuserdb` WRITE;
/*!40000 ALTER TABLE `adminuserdb` DISABLE KEYS */;
INSERT INTO `adminuserdb` VALUES ('admin','admin123');
/*!40000 ALTER TABLE `adminuserdb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minecraftstore`
--

DROP TABLE IF EXISTS `minecraftstore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `minecraftstore` (
  `id` int NOT NULL AUTO_INCREMENT,
  `original_price` decimal(10,2) NOT NULL DEFAULT '1499.00',
  `discount_percentage` int NOT NULL DEFAULT '100',
  `final_price` decimal(10,2) NOT NULL DEFAULT '1499.00',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minecraftstore`
--

LOCK TABLES `minecraftstore` WRITE;
/*!40000 ALTER TABLE `minecraftstore` DISABLE KEYS */;
INSERT INTO `minecraftstore` VALUES (1,1499.00,100,1499.00,'2025-02-28 20:19:51'),(2,1499.00,50,749.50,'2025-02-28 20:19:51'),(3,1499.00,25,1124.25,'2025-02-28 20:19:51');
/*!40000 ALTER TABLE `minecraftstore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useracc`
--

DROP TABLE IF EXISTS `useracc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `useracc` (
  `username` varchar(20) NOT NULL,
  `password` varchar(40) DEFAULT NULL,
  `AccountCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `AccountStatus` tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useracc`
--

LOCK TABLES `useracc` WRITE;
/*!40000 ALTER TABLE `useracc` DISABLE KEYS */;
INSERT INTO `useracc` VALUES ('danielpadilla','summer','2020-01-20 02:29:12',1),('kathrynb','winter','2025-02-17 18:58:16',1);
/*!40000 ALTER TABLE `useracc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Username` varchar(100) NOT NULL,
  `Password` varchar(255) NOT NULL,
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('danielpadilla','summer'),('kathrynb','winter'),('user','e0e6097a6f8af07daf5fc7244336ba37133713a8fc7345c36d667dfa513fabaa');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertable`
--

DROP TABLE IF EXISTS `usertable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usertable` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(40) DEFAULT NULL,
  `AccountCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `AccountStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertable`
--

LOCK TABLES `usertable` WRITE;
/*!40000 ALTER TABLE `usertable` DISABLE KEYS */;
INSERT INTO `usertable` VALUES ('abc','abc','2020-01-20 02:29:12',1),('admin','admin','2020-01-19 18:59:00',1);
/*!40000 ALTER TABLE `usertable` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-06 17:31:00
