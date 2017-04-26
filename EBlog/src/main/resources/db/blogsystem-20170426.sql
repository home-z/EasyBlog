-- MySQL dump 10.13  Distrib 5.7.17, for Linux (i686)
--
-- Host: localhost    Database: blogsystem
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.2

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

drop database blogsystem if exists "blogsystem"; --直接删除数据库，不提醒
create database blogsystem; --创建数据库 
use blogsystem; --选择数据库

--
-- Table structure for table `bll_article`
--

DROP TABLE IF EXISTS `bll_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_article` (
  `ID` varchar(60) NOT NULL,
  `TypeID` varchar(60) DEFAULT NULL,
  `Title` varchar(50) DEFAULT NULL,
  `Content` text,
  `CreateTime` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `ComCount` int(11) DEFAULT '0' COMMENT '文章评论条数',
  `ReadCount` int(11) DEFAULT '0',
  `TypeName` varchar(50) DEFAULT NULL,
  `SuggestCount` int(11) DEFAULT '0' COMMENT '推荐次数',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_1` (`TypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户发表的文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bll_articletype`
--

DROP TABLE IF EXISTS `bll_articletype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_articletype` (
  `ID` varchar(60) NOT NULL,
  `TypeName` varchar(50) DEFAULT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `UserID` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存文章类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bll_attachment`
--

DROP TABLE IF EXISTS `bll_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_attachment` (
  `ID` varchar(60) NOT NULL,
  `AttName` varchar(50) DEFAULT NULL,
  `AttPath` varchar(50) DEFAULT NULL,
  `ArticleID` varchar(60) DEFAULT NULL,
  `AttSize` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章的附件信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bll_commont`
--

DROP TABLE IF EXISTS `bll_commont`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_commont` (
  `ID` varchar(60) NOT NULL,
  `ArticleID` varchar(60) DEFAULT NULL,
  `Person` varchar(50) DEFAULT NULL,
  `ComContent` varchar(2000) DEFAULT NULL,
  `ComTime` datetime DEFAULT NULL,
  `ComEmail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对文章的评论信息';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger updateComCount
after insert on bll_commont
for each row
	update bll_article set ComCount=ComCount+1 where id=new.ArticleID */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `bll_crawltask`
--

DROP TABLE IF EXISTS `bll_crawltask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_crawltask` (
  `ID` varchar(60) NOT NULL,
  `CreateBy` varchar(45) DEFAULT NULL,
  `CrawlURL` varchar(100) DEFAULT NULL,
  `KeyWords` varchar(100) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `State` int(11) DEFAULT NULL COMMENT '0：创建；1：执行中；2：执行成功；3：执行失败。',
  `FinishTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bll_favarticle`
--

DROP TABLE IF EXISTS `bll_favarticle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_favarticle` (
  `ID` varchar(60) NOT NULL,
  `User` varchar(50) DEFAULT NULL COMMENT '关注人编码。sys_users表的UserCode',
  `ArticleTitle` varchar(50) DEFAULT NULL,
  `Describle` varchar(500) DEFAULT NULL,
  `ArticleURL` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bll_favuser`
--

DROP TABLE IF EXISTS `bll_favuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_favuser` (
  `ID` varchar(60) NOT NULL,
  `User` varchar(50) DEFAULT NULL COMMENT '关注人编码。sys_users表的UserCode',
  `FavUser` varchar(60) DEFAULT NULL COMMENT '被关注的人。sys_users表的UserCode',
  `describle` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bll_pageinfo`
--

DROP TABLE IF EXISTS `bll_pageinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_pageinfo` (
  `ID` varchar(60) NOT NULL,
  `URL` varchar(100) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `PostTime` varchar(60) DEFAULT NULL,
  `Content` text,
  `Author` varchar(45) DEFAULT NULL,
  `AuthorPage` varchar(60) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `CreateBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bll_suggest`
--

DROP TABLE IF EXISTS `bll_suggest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bll_suggest` (
  `ID` varchar(60) NOT NULL,
  `User` varchar(50) DEFAULT NULL COMMENT '推荐人编码。sys_users表的UserCode',
  `ArticleID` varchar(60) DEFAULT NULL COMMENT '推荐文章的ID。bll_article表的ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_users`
--

DROP TABLE IF EXISTS `sys_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_users` (
  `ID` varchar(60) DEFAULT NULL,
  `UserCode` varchar(50) NOT NULL,
  `UserPassword` varchar(50) DEFAULT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`UserCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-26 21:12:05
