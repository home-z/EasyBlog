<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${dto[0].createBy}-博客世界</title>
<%@include file="/common/resinculde.jsp"%>
<link href="${cssPath}/index.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#articleInfo {
	margin-left: 20px;
	font-size: 16px;
	color: #333347;
	line-height: 20px;
}

#postInfo {
	color: #9D9095;
}

hr {
	color: #c0c0c0;
}
</style>
</head>
<body>
	<%@include file="/common/menu.jsp"%>
	<div id="articleInfo">
		<c:forEach items="${dto}" var="item">
			<a href="${ctxPath}/MainIndex/getDetailById.do?id=${item.id}" target="_blank">${item.title}</a>
			<p>${item.content}</p>
			<p id="postInfo">${item.createBy}发布于${item.createTime}&nbsp;评论(${item.comCount})</p>
			<hr>
		</c:forEach>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>