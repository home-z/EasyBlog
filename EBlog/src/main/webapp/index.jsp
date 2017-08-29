<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>博客世界</title>
		<%@include file="/common/resinculde.jsp"%>
		<link href="${cssPath}/index.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jsPath}/view/index.js"></script>
	</head>
	<body>
		<%@include file="/common/menu.jsp"%>
		<div id="container">
			<div id="leftArticle">
				<div id="articleInfo"></div>
				<div id="pageFilter"></div>
			</div>
			<div id="rightCategory">
				<p>文章分类</p>
				<hr style='border: 1px dotted #E4DDDD' />
				<div id="categoryLinks"></div>
			</div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</body>
</html>