<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${artdto.title}-${artdto.createBy}-博客世界</title>
<%@include file="/common/resinculde.jsp"%>
<link href="${cssPath}/index.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#top {
	border: 1px solid #D8DFEA;
	margin: 5px;
}

#logTitle {
	font-size: 25px;
	color: #E33E06;
	width:900px;
}

#content {
	margin-left: 20px;
	margin-right: 20px;
}

#articleInfo {
	font-size: 16px;
	color: #333347;
	line-height: 20px;
}

#title {
	font-size: 24px;
	line-height: 30px;
}

#postInfo {
	color: #9D9095;
	float: right;
}
</style>
</head>
<body>
	<!-- 	应该指向具体某人的博客内容，而不是所有的博客内容都共用这一个页面。
	TODO -->
	<div id="top">
		<table width="100%">
			<tr>
				<td><img src="${imgPath}/log.jpg"></td>
				<td><div id="logTitle">博客世界</div></td>
			</tr>
		</table>
	</div>
	<div id="content">
		<div id="articleInfo">
			<p id="title">${artdto.title}</p>
			<p>${artdto.content}</p>
			<p id="postInfo">${artdto.createBy}发布于${artdto.createTime}</p>
			<br />
		</div>
		<p>评论列表</p>
		<hr style="border: 1px dotted #CCC3C3" />
		<div id="comDiv">
			<c:forEach items="${comList}" var="item" varStatus="status">
				<p>#${status.count}楼&nbsp;${item.comTime}&nbsp;${item.person}</p>
				<p>${item.comContent}</p>
				<hr style="border: 1px dotted #E4DDDD" />
			</c:forEach>
		</div>

	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>