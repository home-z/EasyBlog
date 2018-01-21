-- 用户
INSERT INTO `eblog`.`sys_user`(`ID`,`Creator`,`UserCode`,`UserPassword`,`UserName`) VALUES ('user0','user0','admin','e10adc3949ba59abbe56e057f20f883e','系统管理员');
INSERT INTO `eblog`.`sys_user`(`ID`,`Creator`,`UserCode`,`UserPassword`,`UserName`) VALUES ('user1','user0','user','e10adc3949ba59abbe56e057f20f883e','使用人');


-- 角色
INSERT INTO `eblog`.`sys_role`(`ID`,`Creator`,`RoleName`,`Remark`) VALUES ('role0','user0','系统管理员组','系统预置角色');
INSERT INTO `eblog`.`sys_role`(`ID`,`Creator`,`RoleName`,`Remark`) VALUES ('role1','user0','使用人组','系统预置角色');


-- 用户角色
INSERT INTO `eblog`.`sys_userrole`(`ID`,`Creator`,`UserId`,`RoleId`) VALUES ('userrole0','user0','user0','role0');
INSERT INTO `eblog`.`sys_userrole`(`ID`,`Creator`,`UserId`,`RoleId`) VALUES ('userrole1','user0','user1','role1');


-- 菜单
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu0','user0','菜单','','',0);

	-- 一级菜单
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu1','user0','博客管理','','menu0',1);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu2','user0','收藏管理','','menu0',2);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu3','user0','新闻抓取管理','','menu0',3);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu4','user0','系统管理','','menu0',4);

		-- 二级菜单-博客管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu5','user0','博客类别管理','/admin/blog/blogtype.jsp','menu1',5);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu6','user0','博客内容管理','/admin/blog/blog.jsp','menu1',6);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu7','user0','新建博客','/admin/blog/bloginfo.jsp','menu1',7);

		-- 二级菜单-收藏管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu8','user0','我的关注','/admin/favorite/myfavorite.jsp','menu2',8);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu9','user0','我的评论','/admin/favorite/mycomment.jsp','menu2',9);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu10','user0','我的推荐','/admin/favorite/mysuggests.jsp','menu2',10);

		-- 二级菜单-新闻抓取管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu11','user0','抓取设置','/admin/crawler/crawlsetting.jsp','menu3',11);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu12','user0','我的抓取','/admin/crawler/crawlnews.jsp','menu3',12);

		-- 二级菜单-系统管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu13','user0','用户管理','/admin/system/users.jsp','menu4',13);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu14','user0','角色管理','/admin/system/roles.jsp','menu4',14);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu15','user0','用户角色管理','/admin/system/userroles.jsp','menu4',15);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('menu16','user0','角色权限管理','/admin/system/rolesauth.jsp','menu4',16);


-- 角色权限
	-- 系统管理员权限
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth0','user0','role0','menu0');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth1','user0','role0','menu1');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth2','user0','role0','menu2');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth3','user0','role0','menu3');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth4','user0','role0','menu4');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth5','user0','role0','menu5');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth6','user0','role0','menu6');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth7','user0','role0','menu7');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth8','user0','role0','menu8');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth9','user0','role0','menu9');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth10','user0','role0','menu10');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth11','user0','role0','menu11');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth12','user0','role0','menu12');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth13','user0','role0','menu13');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth14','user0','role0','menu14');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth15','user0','role0','menu15');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth16','user0','role0','menu16');

	-- 普通博客使用者
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth17','user0','role1','menu0');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth18','user0','role1','menu1');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth19','user0','role1','menu2');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth20','user0','role1','menu3');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth21','user0','role1','menu5');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth22','user0','role1','menu6');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth23','user0','role1','menu7');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth24','user0','role1','menu8');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth25','user0','role1','menu9');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth26','user0','role1','menu10');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth27','user0','role1','menu11');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('roleauth28','user0','role1','menu12');
