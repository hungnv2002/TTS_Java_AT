-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: testschool
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `teacher_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmtq71nmcpoqpxqvgklo7v06na` (`code`),
  UNIQUE KEY `UKi9lpo8y0p2v93c28ibeqno21c` (`teacher_id`),
  CONSTRAINT `FK28f8ba9n0feejnamfay479ae1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'A1','Class A1',1),(2,'A2','Class A2',2),(3,'B1','Class B1',3),(4,'B2','Class B2',4),(5,'C1','Class C1',5),(6,'C2','Class C2',6),(7,'D1','Class D1',7),(8,'D2','Class D2',8),(9,'E1','Class E1',9),(10,'E2','Class E2',10),(12,'CL04','Lớp Lý 12',12);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `score` double NOT NULL,
  `class_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdwhkib64u47wc4yo4hk0cub90` (`class_id`),
  CONSTRAINT `FKdwhkib64u47wc4yo4hk0cub90` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'123 Nguyen Trai, Ha Noi',16,'nguyenvana@student.com','Nguyen Van A',10,1),(2,'456 Le Loi, Ho Chi Minh City',15,'tranthib@student.com','Tran Thi B',9,2),(3,'789 Hai Ba Trung, Da Nang',17,'phamvanc@student.com','Pham Van C',9,3),(4,'321 Tran Hung Dao, Hue',16,'lethid@student.com','Le Thi D',8,4),(5,'654 Hoang Van Thu, Can Tho',15,'nguyenvane@student.com','Nguyen Van E',7,5),(6,'987 Nguyen Hue, Nha Trang',16,'phamthif@student.com','Pham Thi F',8,6),(7,'147 Bach Dang, Hai Phong',17,'tranvang@student.com','Tran Van G',7,7),(8,'369 Le Thanh Ton, Da Lat',15,'lethih@student.com','Le Thi H',9.5,8),(9,'258 Truong Chinh, Vinh',16,'nguyenthi@student.com','Nguyen Thi I',7.5,9),(10,'852 Ly Thuong Kiet, Quang Ninh',17,'phamvank@student.com','Pham Van K',0,10),(11,'123 Main St',18,NULL,'John Doe',0,12),(12,'123 Main St',18,NULL,'John Doe',0,12),(13,'123 Main St',18,'johndoe@example.com','John Doe',0,12);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'123 Nguyen Trai, Ha Noi',30,'nguyenvana@gmail.com','Nguyen Van A'),(2,'456 Le Loi, Ho Chi Minh City',29,'tranthib@gmail.com','Tran Thi B'),(3,'789 Hai Ba Trung, Da Nang',35,'phamvanc@gmail.com','Pham Van C'),(4,'321 Tran Hung Dao, Hue',40,'lethid@gmail.com','Le Thi D'),(5,'654 Hoang Van Thu, Can Tho',28,'nguyenvane@gmail.com','Nguyen Van E'),(6,'987 Nguyen Hue, Nha Trang',33,'phamthif@gmail.com','Pham Thi F'),(7,'147 Bach Dang, Hai Phong',31,'tranvang@gmail.com','Tran Van G'),(8,'369 Le Thanh Ton, Da Lat',27,'lethih@gmail.com','Le Thi H'),(9,'258 Truong Chinh, Vinh',36,'nguyenthi@gmail.com','Nguyen Thi I'),(10,'852 Ly Thuong Kiet, Quang Ninh',38,'phamvank@gmail.com','Pham Van K'),(12,'Bac Giang',40,'nguyenvanhung@example.com','Nguyen Van Dang');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-08  0:16:41
