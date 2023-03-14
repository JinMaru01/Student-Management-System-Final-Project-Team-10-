-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 14, 2023 at 02:37 AM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `students`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course` (
  `courseID` int NOT NULL AUTO_INCREMENT,
  `courseName` varchar(50) NOT NULL,
  PRIMARY KEY (`courseID`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`courseID`, `courseName`) VALUES
(1, 'OOP'),
(2, 'Web Application'),
(3, 'Operating System'),
(4, 'Database design and analysis');

-- --------------------------------------------------------

--
-- Table structure for table `enroll`
--

DROP TABLE IF EXISTS `enroll`;
CREATE TABLE IF NOT EXISTS `enroll` (
  `enrollID` int NOT NULL AUTO_INCREMENT,
  `studentID` int NOT NULL,
  `courseName` varchar(50) NOT NULL,
  PRIMARY KEY (`enrollID`),
  KEY `studentID` (`studentID`),
  KEY `courseName` (`courseName`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `enroll`
--

INSERT INTO `enroll` (`enrollID`, `studentID`, `courseName`) VALUES
(1, 3, 'Database design and analysis'),
(2, 2, 'Database design and analysis'),
(3, 4, 'OOP'),
(4, 3, 'OOP'),
(5, 3, 'Operating System'),
(6, 2, 'Operating System'),
(7, 2, 'OOP'),
(8, 1, 'OOP'),
(9, 4, 'Database design and analysis'),
(10, 2, 'Web Application'),
(11, 3, 'Web Application'),
(12, 1, 'Web Application'),
(13, 1, 'Operating System'),
(14, 5, 'Web Application'),
(15, 1, 'Database design and analysis'),
(16, 7, 'Database design and analysis'),
(17, 4, 'Operating System'),
(18, 6, 'Database design and analysis'),
(19, 5, 'OOP'),
(20, 5, 'Database design and analysis'),
(21, 8, 'Database design and analysis'),
(22, 9, 'Database design and analysis'),
(23, 4, 'Database design and analysis');

-- --------------------------------------------------------

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
CREATE TABLE IF NOT EXISTS `score` (
  `scoreID` int NOT NULL AUTO_INCREMENT,
  `studentID` int DEFAULT NULL,
  `courseName` varchar(50) NOT NULL,
  `midTerm` double DEFAULT NULL,
  `quiz` double DEFAULT NULL,
  `final` double DEFAULT NULL,
  `assignment` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `average` double DEFAULT NULL,
  PRIMARY KEY (`scoreID`),
  KEY `studentID` (`studentID`),
  KEY `courseName` (`courseName`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `score`
--

INSERT INTO `score` (`scoreID`, `studentID`, `courseName`, `midTerm`, `quiz`, `final`, `assignment`, `total`, `average`) VALUES
(1, 5, 'OOP', 99, 100, 93, 99, 391, 97.75),
(2, 7, 'OOP', 92, 91, 93, 99, 375, 93.75),
(3, 1, 'Database design and analysis', 100, 100, 99, 100, 399, 99.75),
(4, 4, 'Database design and analysis', 90, 90, 90, 98, 368, 92),
(5, 2, 'Database design and analysis', 89, 90, 79, 80, 338, 84.5),
(6, 3, 'Database design and analysis', 100, 100, 100, 100, 400, 100),
(7, 7, 'Database design and analysis', 90, 100, 79, 89, 358, 89.5),
(8, 5, 'Database design and analysis', 90, 100, 79, 98, 367, 91.75),
(9, 1, 'Web Application', 89, 90, 78, 98, 355, 88.75),
(10, 2, 'Operating System', 90, 100, 90, 99, 379, 94.75);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `studentID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `age` int DEFAULT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `birthday` date NOT NULL,
  `gender` enum('Male','Female') NOT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentID`, `firstName`, `lastName`, `age`, `phoneNumber`, `email`, `birthday`, `gender`) VALUES
(1, 'Bob', 'Johnson', 21, '011-555-9012', 'bobjohnson@example.com', '2001-05-11', 'Male'),
(2, 'Mary', 'Williams', 19, '010-555-3456', 'marywilliams@example.com', '2003-10-20', 'Female'),
(3, 'William', 'King', 21, '097-555-2345', 'williamking@example.com', '2002-02-14', 'Male'),
(4, 'Karen', 'Wright', 22, '098-555-6789', 'karenwright@example.com', '2001-03-01', 'Female'),
(5, 'Kong', 'Darachin', 18, '096-907-5215', 'rachinkong@gmail.com', '2004-06-03', 'Male'),
(6, 'Vet', 'Samheang', 20, '068-789-1233', 'samheang.vet@gmail.com', '2003-01-01', 'Male'),
(7, 'cham', 'sreytouch', 21, '019-203-123', 'test@test.com', '2001-05-10', 'Male'),
(8, 'test', 'final', 20, '091-203-1234', 'test@gmail.com', '2003-01-10', 'Male');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `confirmPassword` varchar(20) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `username`, `email`, `password`, `confirmPassword`, `status`) VALUES
(1, 'admin', 'admin@gmail.com', 'admin', 'admin', 1),
(7, 'admin2', 'admin@gmail.com', '12341234', '12341234', 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
