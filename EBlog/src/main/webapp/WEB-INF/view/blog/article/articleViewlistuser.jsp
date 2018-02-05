<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/spring.tld" prefix="spring" %>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${dto[0].creator}-<spring:message code="blogworld"/></title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<link href="${cssPath}/articleViewlistuser.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<!-- 	应该指向具体某人的博客内容，而不是所有的博客内容都共用这一个页面。或者显示每个人自己设置的页面
	TODO -->
	<div id="top">
		<table width="100%">
			<tr>
				<td><img src="${imgPath}/log.jpg"></td>
				<td><div id="logTitle"><spring:message code="blogworld"/></div></td>
			</tr>
		</table>
	</div>
	<div id="articleInfo">
		<c:forEach items="${dto}" var="item">
			<a href="${ctxPath}/BlogInfo/getDetailByIdView.do?id=${item.id}" target="_blank">${item.title}</a>
			<p>${item.content}</p>
			<p id="postInfo">${item.creator}&nbsp;<spring:message code="postat"/>&nbsp;${item.createTime}&nbsp;<spring:message code="commit"/>(${item.comCount})</p>
			<hr>
		</c:forEach>
	</div>
	<%@include file="/WEB-INF/view/common/footer.jsp"%>
</body>
</html>