<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注销</title>
</head>
<body>
	<%
		response.setHeader("refresh", "2;URL=../index.jsp");
		session.invalidate();
	%>
	<h3>你已经退出后台管理，两秒钟回到主页面！Bye!</h3>
</body>
</html>