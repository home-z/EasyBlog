<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/spring.tld" prefix="spring" %>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${artdto.title}-${artdto.creatorName}-<spring:message code="blogworld"/></title>
	<%@include file="/common/resinculde.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${cssPath}/articleView.css" rel="stylesheet" type="text/css" />
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
	<div id="content">
		<div id="articleInfo">
			<p id="title">${artdto.title}</p>
			<p>${artdto.content}</p>
			<p id="postInfo">${artdto.creatorName}&nbsp;<spring:message code="postat"/>&nbsp;${artdto.createTime}
			&nbsp;<spring:message code="read"/>&nbsp;(${artdto.readCount})
			&nbsp;<spring:message code="suggest"/>&nbsp;(${artdto.suggestCount})
			&nbsp;<spring:message code="commit"/>&nbsp;(${artdto.comCount})</p>
			<br />
		</div>
		<p><spring:message code="commitList"/></p>
		<hr style="border: 1px dotted #CCC3C3" />
		<div id="comDiv">
			<c:forEach items="${comList}" var="item" varStatus="status">
				<p>#${status.count}<spring:message code="floor"/>&nbsp;${item.comTime}&nbsp;${item.person}</p>
				<p>${item.comContent}</p>
				<hr style="border: 1px dotted #E4DDDD" />
			</c:forEach>
		</div>

	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>