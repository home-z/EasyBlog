drop database if exists EBlog; -- 直接删除数据库，不提醒
create database EBlog; -- 创建数据库 
use EBlog; -- 选择数据库

--
-- Table structure for table `bll_article`
--
DROP TABLE IF EXISTS `bll_article`;
CREATE TABLE `bll_article` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `TypeID` varchar(60) DEFAULT NULL COMMENT '文章所属类型ID',
  `TypeName` varchar(50) DEFAULT NULL COMMENT '文章所属类型名称',
  `Title` varchar(50) DEFAULT NULL COMMENT '文章标题',
  `Content` varchar(10000) DEFAULT NULL COMMENT '文章内容',
  `ComCount` int(11) DEFAULT '0' COMMENT '文章被评论条数',
  `ReadCount` int(11) DEFAULT '0' COMMENT '文章被阅读条数',
  `SuggestCount` int(11) DEFAULT '0' COMMENT '文章被推荐次数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户发表的文章';


--
-- Table structure for table `bll_articletype`
--
DROP TABLE IF EXISTS `bll_articletype`;
CREATE TABLE `bll_articletype` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `TypeName` varchar(50) DEFAULT NULL COMMENT '文章类型名称',
  `Description` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章类型';


--
-- Table structure for table `bll_attachment`
--
DROP TABLE IF EXISTS `bll_attachment`;
CREATE TABLE `bll_attachment` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `AttName` varchar(50) DEFAULT NULL COMMENT '附件名称',
  `AttPath` varchar(50) DEFAULT NULL COMMENT '附件路径',
  `ArticleID` varchar(60) DEFAULT NULL COMMENT '附件关联的文章标题',
  `AttSize` int(11) DEFAULT NULL COMMENT '附件大小',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章的附件信息';


--
-- Table structure for table `bll_commont`
--
DROP TABLE IF EXISTS `bll_commont`;
CREATE TABLE `bll_commont` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `ArticleID` varchar(60) DEFAULT NULL COMMENT '被评论的文章ID',
  `ArticleTitle` varchar(50) DEFAULT NULL COMMENT '被评论的文章标题',
  `ComContent` varchar(2000) DEFAULT NULL COMMENT '评论内容',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对文章的评论信息';


--
-- Table structure for table `bll_crawltask`
--
DROP TABLE IF EXISTS `bll_crawltask`;
CREATE TABLE `bll_crawltask` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `CrawlURL` varchar(100) DEFAULT NULL COMMENT '抓取的链接',
  `KeyWords` varchar(100) DEFAULT NULL COMMENT '待抓取的关键词',
  `State` int(11) DEFAULT NULL COMMENT '0：创建；1：执行中；2：执行成功；3：执行失败。',
  `FinishTime` datetime DEFAULT NULL COMMENT '抓取任务完成时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抓取任务';


--
-- Table structure for table `bll_favarticle`
--
DROP TABLE IF EXISTS `bll_favarticle`;
CREATE TABLE `bll_favarticle` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `ArticleTitle` varchar(50) DEFAULT NULL COMMENT '文章标题',
  `Describle` varchar(500) DEFAULT NULL COMMENT '描述',
  `ArticleURL` varchar(200) DEFAULT NULL COMMENT '文章链接',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注的文章';


--
-- Table structure for table `bll_favuser`
--
DROP TABLE IF EXISTS `bll_favuser`;
CREATE TABLE `bll_favuser` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `FavUser` varchar(60) DEFAULT NULL COMMENT '被关注的人ID',
  `Describle` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注的博主';


--
-- Table structure for table `bll_pageinfo`
--
DROP TABLE IF EXISTS `bll_pageinfo`;
CREATE TABLE `bll_pageinfo` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `URL` varchar(100) DEFAULT NULL  COMMENT '抓取的文章地址',
  `Title` varchar(100) DEFAULT NULL COMMENT '抓取的文章标题',
  `PostTime` varchar(60) DEFAULT NULL COMMENT '抓取的文章发表时间',
  `Content` text COMMENT '抓取的文章内容',
  `Author` varchar(45) DEFAULT NULL COMMENT '抓取的文章作者',
  `AuthorPage` varchar(60) DEFAULT NULL COMMENT '抓取的文章作者的主页',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抓取的文章';


--
-- Table structure for table `bll_suggest`
--
DROP TABLE IF EXISTS `bll_suggest`;
CREATE TABLE `bll_suggest` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `ArticleID` varchar(60) DEFAULT NULL COMMENT '推荐文章的ID',
  `ArticleTitle` varchar(50) DEFAULT NULL COMMENT '推荐的文章标题',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推荐的文章';



--
-- Table structure for table `sys_menu`
--
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `MenuName` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `URL` varchar(100) DEFAULT NULL COMMENT '菜单地址',
  `ParentID` varchar(60) DEFAULT NULL COMMENT '父级菜单id',
  `Index` int(11) DEFAULT NULL COMMENT '菜单顺序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单';


--
-- Table structure for table `sys_role`
--
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `RoleName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `Remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';


--
-- Table structure for table `sys_roleauth`
--
DROP TABLE IF EXISTS `sys_roleauth`;
CREATE TABLE `sys_roleauth` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `RoleId` varchar(60) DEFAULT NULL COMMENT '角色ID',
  `MenuId` varchar(60) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单';


--
-- Table structure for table `sys_userrole`
--
DROP TABLE IF EXISTS `sys_userrole`;
CREATE TABLE `sys_userrole` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `UserId` varchar(60) DEFAULT NULL COMMENT '用户ID',
  `RoleId` varchar(60) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户';


--
-- Table structure for table `sys_user`
--
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `UserCode` varchar(50) DEFAULT NULL COMMENT '登录名',
  `UserPassword` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `UserName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `Email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `PhotoPath` varchar(200) DEFAULT NULL COMMENT '用户图片存放路径。',
  `PhotoFingerPrint` varchar(45) DEFAULT NULL COMMENT '用户头像指纹码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';