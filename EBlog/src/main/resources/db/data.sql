-- 系统预置数据

-- 用户（密码：123456）
insert into sys_users (id,usercode,userpassword,username,'CreateTime','Creator') values ('0','admin','e10adc3949ba59abbe56e057f20f883e','系统管理员',now(),'admin');
insert into sys_users (id,usercode,userpassword,username,'CreateTime','Creator') values ('1','demo','e10adc3949ba59abbe56e057f20f883e','博客使用人',now(),'admin');


-- 角色
INSERT INTO 'blogsystem'.'sys_role' ('ID','RoleName','CreateTime','Creator','Remark') VALUES (0,'系统管理员',now(),'admin','系统预置角色');
INSERT INTO 'blogsystem'.'sys_role' ('ID','RoleName','CreateTime','Creator','Remark') VALUES (1,'博客使用人',now(),'admin','系统预置角色');


-- 用户角色
INSERT INTO 'blogsystem'.'sys_userrole'('ID','UserCode','RoleId') VALUES ('0','admin',0);
INSERT INTO 'blogsystem'.'sys_userrole'('ID','UserCode','RoleId') VALUES ('1','demo',1);


-- 菜单
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL') VALUES(0,'菜单','');

INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(1,'博客管理','',0);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(2,'收藏管理','',0);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(3,'新闻抓取管理','',0);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(4,'系统管理','',0);

INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(5,'博客类别管理','/admin/blog/blogtype.jsp',1);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(6,'博客内容管理','/admin/blog/blog.jsp',1);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(7,'新建博客','/admin/blog/bloginfo.jsp',1);

INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(8,'我的关注','/admin/favorite/myfavorite.jsp',2);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(9,'我的评论','/admin/favorite/mycomment.jsp',2);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(10,'我的推荐','/admin/favorite/mysuggests.jsp',2);

INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(11,'抓取设置','/admin/crawler/crawlsetting.jsp',3);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(12,'我的抓取','/admin/crawler/crawlnews.jsp',3);

INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(13,'用户管理','/admin/system/users.jsp',4);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(14,'角色管理','/admin/system/roles.jsp',4);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(15,'用户角色管理','/admin/system/userroles.jsp',4);
INSERT INTO 'blogsystem'.'sys_menu'('ID','MenuName','URL','ParentID') VALUES(16,'角色权限管理','/admin/system/rolesauth.jsp',4);


-- 角色权限
	-- 系统管理员权限
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('1',0,0);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('2',0,1);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('3',0,2);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('4',0,3);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('5',0,4);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('6',0,5);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('7',0,6);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('8',0,7);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('9',0,8);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('10',0,9);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('11',0,10);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('12',0,11);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('13',0,12);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('14',0,13);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('15',0,14);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('16',0,15);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('17',0,16);

	-- 普通博客使用者
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('18',1,0);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('19',1,1);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('20',1,2);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('21',1,3);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('22',1,5);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('23',1,6);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('24',1,7);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('25',1,8);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('26',1,9);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('27',1,10);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('28',1,11);
INSERT INTO 'blogsystem'.'sys_roleauth'('ID','RoleId','MenuId')VALUES('29',1,12);
