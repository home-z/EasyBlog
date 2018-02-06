-- 用户
INSERT INTO `eblog`.`sys_user`(`ID`,`Creator`,`UserCode`,`UserPassword`,`UserName`,`Email`) VALUES ('0','0','admin','e10adc3949ba59abbe56e057f20f883e','系统管理员','yufeijob@163.com');
INSERT INTO `eblog`.`sys_user`(`ID`,`Creator`,`UserCode`,`UserPassword`,`UserName`,`Email`) VALUES ('1','0','user','e10adc3949ba59abbe56e057f20f883e','使用人','yangtze_yufei@163.com');


-- 角色
INSERT INTO `eblog`.`sys_role`(`ID`,`Creator`,`RoleName`,`Remark`) VALUES ('0','0','系统管理员组','系统预置角色');
INSERT INTO `eblog`.`sys_role`(`ID`,`Creator`,`RoleName`,`Remark`) VALUES ('1','0','使用人组','系统预置角色');


-- 用户角色
INSERT INTO `eblog`.`sys_userrole`(`ID`,`Creator`,`UserId`,`RoleId`) VALUES ('0','0','0','0');
INSERT INTO `eblog`.`sys_userrole`(`ID`,`Creator`,`UserId`,`RoleId`) VALUES ('1','0','1','1');


-- 菜单
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`Index`) VALUES('0','0','菜单',0);

	-- 一级菜单
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('1','0','博客管理','0',1);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('2','0','收藏管理','0',2);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('3','0','新闻抓取管理','0',3);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('4','0','系统管理','0',4);

		-- 二级菜单-博客管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('5','0','博客类别管理','/adminRoute/articleType.do','1',5);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('6','0','博客内容管理','/adminRoute/article.do','1',6);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('7','0','新建博客','/adminRoute/articleInfo.do','1',7);

		-- 二级菜单-收藏管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('8','0','我的关注','/adminRoute/myfavorite.do','2',8);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('9','0','我的评论','/adminRoute/mycomment.do','2',9);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('10','0','我的推荐','/adminRoute/mysuggests.do','2',10);

		-- 二级菜单-新闻抓取管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('11','0','抓取设置','/adminRoute/crawlsetting.do','3',11);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('12','0','我的抓取','/adminRoute/crawlnews.do','3',12);

		-- 二级菜单-系统管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('13','0','用户管理','/adminRoute/users.do','4',13);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('14','0','角色管理','/adminRoute/roles.do','4',14);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('15','0','用户角色管理','/adminRoute/userroles.do','4',15);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('16','0','角色权限管理','/adminRoute/rolesauth.do','4',16);


-- 角色权限
	-- 系统管理员权限
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('0','0','0','0');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('1','0','0','1');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('2','0','0','2');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('3','0','0','3');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('4','0','0','4');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('5','0','0','5');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('6','0','0','6');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('7','0','0','7');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('8','0','0','8');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('9','0','0','9');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('10','0','0','10');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('11','0','0','11');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('12','0','0','12');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('13','0','0','13');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('14','0','0','14');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('15','0','0','15');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('16','0','0','16');

	-- 普通博客使用者
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('17','0','1','0');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('18','0','1','1');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('19','0','1','2');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('20','0','1','3');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('21','0','1','5');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('22','0','1','6');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('23','0','1','7');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('24','0','1','8');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('25','0','1','9');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('26','0','1','10');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('27','0','1','11');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('28','0','1','12');
