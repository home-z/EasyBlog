<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客世界-后台管理</title>
<%@include file="/common/resinculde.jsp"%>
<link href="${cssPath}/admin.css" rel="stylesheet" type="text/css" />
<link href="${jsPath}/jquery-easyui/themes/metro-blue/easyui.css" rel="stylesheet" type="text/css" />
<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${jsPath}/jquery-easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript">
	function time() {
		var now = new Date();
		var nowTime = now.getFullYear() + "-" + (now.getMonth() + 1) + "-"
				+ now.getDate() + " " + now.getHours() + ":" + now.getMinutes()
				+ ":" + now.getSeconds();
		$("#nowTime").html(nowTime);
	}
	function showTime() {
		setInterval(time, 1000); //每秒刷新一次
	}
	$(function() {
		showTime();
	});
	function open1(url, title) {
		if ($('#tt').tabs('exists', title)) {
			$('#tt').tabs('select', title);
		} else { //注：使用iframe即可防止同一个页面出现js和css冲突的问题 
			$('#tt')
					.tabs(
							'add',
							{
								title : title,
								//href : url,
								closable : true,
								cache : false,
								content : '<iframe name="'
										+ title
										+ '" id="'
										+ title
										+ '" src="'
										+ url
										+ '"width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'
							});
		}
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<div region="north" border="false" style="background: #5590E8; text-align: center">
		<div id="header-inner">
			<table cellpadding="0" cellspacing="0" style="width: 100%;">
				<tr>
					<td>log</td>
					<td style="height: 40px;"><span style="color: #fff; font-size: 20px; font-weight: bold; text-decoration: none">博客世界-后台管理</span> <span style="color: #fff; font-size: 16px;">技术改变世界！</span></td>
					<td><span style="font-size: 18px;" id="currentuser">欢迎您：${Current_User.userName}</span> <a style="color: #fff; font-size: 16px; text-decoration: none" href="../index.jsp">返回主页</a> <a style="color: #fff; font-size: 16px; text-decoration: none" href="${ctxPath}/admin/logout.jsp">退出登录</a></td>
				</tr>
			</table>
		</div>
	</div>
	<div region="west" split="true" title="管理菜单" style="width: 200px; padding: 5px;">
		<ul class="easyui-tree">
			<li iconCls="icon-base"><span>博客管理</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/blog/blogtype.jsp','博客类别管理')">博客类别管理</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/blog/blog.jsp','博客内容管理')">博客内容管理</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/blog/bloginfo.jsp','新建博客')">新建博客</a></li>
					<!--  <li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/blog/kindeditor.jsp','新建博客test')">新建博客test</a></li>-->
				</ul></li>
			<li iconCls="icon-base"><span>收藏管理</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/favorite/myfavorite.jsp','我的关注')">我的关注</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/favorite/mycomment.jsp','我的评论')">我的评论</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/favorite/mysuggests.jsp','我的推荐')">我的推荐</a></li>
					<!--  <li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/blog/kindeditor.jsp','新建博客test')">新建博客test</a></li>-->
				</ul></li>
			<li iconCls="icon-base"><span>新闻抓取管理</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/crawler/crawlsetting.jsp','抓取设置')">抓取设置</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/crawler/crawlnews.jsp','我的抓取')">我的抓取</a></li>
				</ul></li>
			<li iconCls="icon-layout"><span>系统管理</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('${ctxPath}/admin/system/users.jsp','用户管理')">用户管理</a></li>
				</ul></li>
		</ul>
	</div>
	<div region="center">
		<div id="tt" class="easyui-tabs" fit="true" border="false" plain="true">
			<div title="欢迎" href="${ctxPath}/admin/welcome.jsp"></div>
		</div>
	</div>
	<div region="south" style="overflow: hidden;">
		<table style="font-size: 15px; width: 100%;">
			<tr>
				<td style="width: 50%;"><span>当前时间：<span id="nowTime"></span></span></td>
				<td style="width: 50%;"><span style="width: 100px; margin: 0 auto 0 auto;">版权所有：Tim</span></td>
			</tr>
		</table>
	</div>
</body>
</html>