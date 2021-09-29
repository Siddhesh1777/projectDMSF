-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: iste330groupproject4
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `abstract`
--
CREATE DATABASE IF NOT EXISTS `iste330groupproject4`;
USE `iste330groupproject4`;
DROP TABLE IF EXISTS `abstract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abstract` (
  `AbstractID` int NOT NULL AUTO_INCREMENT,
  `FacultyID` int NOT NULL,
  `AbstractText` mediumtext NOT NULL,
  PRIMARY KEY (`AbstractID`,`FacultyID`),
  KEY `Abstract_FacultyID_FK` (`FacultyID`),
  CONSTRAINT `Abstract_FacultyID_FK` FOREIGN KEY (`FacultyID`) REFERENCES `faculty` (`PersonID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstract`
--

LOCK TABLES `abstract` WRITE;
/*!40000 ALTER TABLE `abstract` DISABLE KEYS */;
INSERT INTO `abstract` VALUES (1,2,'This is a test abstract.'),(2,2,'This is a second test abstract.\r\n\r\nIt even has line breaks!'),(3,2,'Once upon a time, I ate a turtle. It did not taste good.');
/*!40000 ALTER TABLE `abstract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `DepartmentID` int NOT NULL AUTO_INCREMENT,
  `DepartmentName` varchar(50) NOT NULL,
  PRIMARY KEY (`DepartmentID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'CSEC'),(2,'iSchool'),(3,'Psychology');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emails`
--

DROP TABLE IF EXISTS `emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emails` (
  `PersonID` int NOT NULL,
  `Email` varchar(35) NOT NULL,
  PRIMARY KEY (`PersonID`,`Email`),
  CONSTRAINT `Emails_PersonID_FK` FOREIGN KEY (`PersonID`) REFERENCES `person` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emails`
--

LOCK TABLES `emails` WRITE;
/*!40000 ALTER TABLE `emails` DISABLE KEYS */;
INSERT INTO `emails` VALUES (1,'justein230@gmail.com'),(1,'jxs3969@rit.edu'),(2,'da@gmail.com'),(2,'da3612@rit.edu');
/*!40000 ALTER TABLE `emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `PersonID` int NOT NULL,
  `EmploymentStartDate` date DEFAULT NULL,
  `Password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`PersonID`),
  CONSTRAINT `Faculty_PersonID_FK` FOREIGN KEY (`PersonID`) REFERENCES `person` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (2,'2020-11-02','6eaf019a35059950cef4951a9d6b9a385bc79543'),(26,'2020-11-10','584cef0e7e263cd91774da397c35d9696496bd40');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facultydepartment`
--

DROP TABLE IF EXISTS `facultydepartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facultydepartment` (
  `FacultyID` int NOT NULL,
  `DepartmentID` int NOT NULL,
  PRIMARY KEY (`FacultyID`,`DepartmentID`),
  KEY `FacultyDepartment_DepartmentID_FK` (`DepartmentID`),
  CONSTRAINT `FacultyDepartment_DepartmentID_FK` FOREIGN KEY (`DepartmentID`) REFERENCES `department` (`DepartmentID`),
  CONSTRAINT `FacultyDepartment_FacultyID_FK` FOREIGN KEY (`FacultyID`) REFERENCES `faculty` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facultydepartment`
--

LOCK TABLES `facultydepartment` WRITE;
/*!40000 ALTER TABLE `facultydepartment` DISABLE KEYS */;
INSERT INTO `facultydepartment` VALUES (2,1),(2,2),(26,3);
/*!40000 ALTER TABLE `facultydepartment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest` (
  `InterestID` int NOT NULL AUTO_INCREMENT,
  `InterestName` varchar(25) NOT NULL,
  PRIMARY KEY (`InterestID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
INSERT INTO `interest` VALUES (1,'Databases'),(2,'UI/UX'),(3,'Windows Administration'),(4,'Linux Administration'),(5,'RPA'),(6,'Automation'),(7,'Scripting');
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major` (
  `MajorID` int NOT NULL AUTO_INCREMENT,
  `MajorName` varchar(40) NOT NULL,
  PRIMARY KEY (`MajorID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (1,'Computing and Information Technologies'),(2,'Muggle Studies'),(3,'Computing Security'),(4,'Computer Science'),(5,'Web and Mobile'),(6,'Human Centered Computing'),(7,'Psychology'),(8,'Computer Engineering');
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `PersonID` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(25) NOT NULL,
  `LastName` varchar(25) NOT NULL,
  PRIMARY KEY (`PersonID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Justin','Stein'),(2,'Test','FacultyAccount'),(26,'Test','Faculty2'),(27,'Test','Student2');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phonenumbers`
--

DROP TABLE IF EXISTS `phonenumbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phonenumbers` (
  `PersonID` int NOT NULL,
  `PhoneNumber` varchar(15) NOT NULL,
  PRIMARY KEY (`PersonID`,`PhoneNumber`),
  CONSTRAINT `PhoneNumbers_PersonID_FK` FOREIGN KEY (`PersonID`) REFERENCES `person` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phonenumbers`
--

LOCK TABLES `phonenumbers` WRITE;
/*!40000 ALTER TABLE `phonenumbers` DISABLE KEYS */;
INSERT INTO `phonenumbers` VALUES (1,'914-559-8140'),(1,'914-632-0292'),(2,'585-475-2572'),(2,'585-475-5000');
/*!40000 ALTER TABLE `phonenumbers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `ProjectID` int NOT NULL AUTO_INCREMENT,
  `ProjectName` varchar(50) NOT NULL,
  `Abstract` mediumtext,
  `LeadFacultyID` int NOT NULL,
  PRIMARY KEY (`ProjectID`),
  KEY `Project_LeadFacultyID_FK` (`LeadFacultyID`),
  CONSTRAINT `Project_LeadFacultyID_FK` FOREIGN KEY (`LeadFacultyID`) REFERENCES `faculty` (`PersonID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Test Project','This is a test project. It tests the existence of things and also sometimes the universe.',2),(2,'Test Project 2','This project is about scopes... yeah. That.',2),(3,'Test3','Test project abstract 3',2);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectfaculty`
--

DROP TABLE IF EXISTS `projectfaculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projectfaculty` (
  `FacultyID` int NOT NULL,
  `ProjectID` int NOT NULL,
  PRIMARY KEY (`FacultyID`,`ProjectID`),
  KEY `ProjectFaculty_ProjectID_FK` (`ProjectID`),
  CONSTRAINT `ProjectFaculty_FacultyID_FK` FOREIGN KEY (`FacultyID`) REFERENCES `faculty` (`PersonID`),
  CONSTRAINT `ProjectFaculty_ProjectID_FK` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectfaculty`
--

LOCK TABLES `projectfaculty` WRITE;
/*!40000 ALTER TABLE `projectfaculty` DISABLE KEYS */;
INSERT INTO `projectfaculty` VALUES (2,1),(2,2);
/*!40000 ALTER TABLE `projectfaculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `PersonID` int NOT NULL,
  `GradTerm` char(4) NOT NULL,
  PRIMARY KEY (`PersonID`),
  CONSTRAINT `Student_PersonID_FK` FOREIGN KEY (`PersonID`) REFERENCES `person` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'2201'),(27,'2205');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentinterest`
--

DROP TABLE IF EXISTS `studentinterest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentinterest` (
  `PersonID` int NOT NULL,
  `InterestID` int NOT NULL,
  PRIMARY KEY (`PersonID`,`InterestID`),
  KEY `StudentInterest_InterestID_FK` (`InterestID`),
  CONSTRAINT `StudentInterest_InterestID_FK` FOREIGN KEY (`InterestID`) REFERENCES `interest` (`InterestID`),
  CONSTRAINT `StudentInterest_PersonID_FK` FOREIGN KEY (`PersonID`) REFERENCES `student` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentinterest`
--

LOCK TABLES `studentinterest` WRITE;
/*!40000 ALTER TABLE `studentinterest` DISABLE KEYS */;
INSERT INTO `studentinterest` VALUES (1,1),(27,2),(1,3);
/*!40000 ALTER TABLE `studentinterest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentmajor`
--

DROP TABLE IF EXISTS `studentmajor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentmajor` (
  `PersonID` int NOT NULL,
  `MajorID` int NOT NULL,
  PRIMARY KEY (`PersonID`,`MajorID`),
  KEY `StudentMajor_MajorID_FK` (`MajorID`),
  CONSTRAINT `StudentMajor_MajorID_FK` FOREIGN KEY (`MajorID`) REFERENCES `major` (`MajorID`),
  CONSTRAINT `StudentMajor_PersonID_FK` FOREIGN KEY (`PersonID`) REFERENCES `student` (`PersonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentmajor`
--

LOCK TABLES `studentmajor` WRITE;
/*!40000 ALTER TABLE `studentmajor` DISABLE KEYS */;
INSERT INTO `studentmajor` VALUES (1,1),(27,8);
/*!40000 ALTER TABLE `studentmajor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentproject`
--

DROP TABLE IF EXISTS `studentproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentproject` (
  `PersonID` int NOT NULL,
  `ProjectID` int NOT NULL,
  PRIMARY KEY (`PersonID`,`ProjectID`),
  KEY `StudentProject_ProjectID_FK` (`ProjectID`),
  CONSTRAINT `StudentProject_PersonID_FK` FOREIGN KEY (`PersonID`) REFERENCES `student` (`PersonID`),
  CONSTRAINT `StudentProject_ProjectID_FK` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentproject`
--

LOCK TABLES `studentproject` WRITE;
/*!40000 ALTER TABLE `studentproject` DISABLE KEYS */;
INSERT INTO `studentproject` VALUES (1,1);
/*!40000 ALTER TABLE `studentproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'iste330groupproject4'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-15  0:24:26
