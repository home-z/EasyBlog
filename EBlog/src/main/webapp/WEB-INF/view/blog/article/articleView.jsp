<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/spring.tld" prefix="spring"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${artdto.title}-${artdto.creatorName}-<spring:message code="blogworld" /></title>
<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
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
				<td><div id="logTitle">
						<spring:message code="blogworld" />
					</div></td>
			</tr>
		</table>
	</div>
	<div id="content">
		<input id="articleId" type="hidden" value="${artdto.ID}" />
		<input id="articleTitle" type="hidden" value="${artdto.title}" />
		<div id="articleInfo">
			<p id="title">${artdto.title}</p>
			<p>${artdto.content}</p>
			<p id="postInfo">${artdto.creatorName}&nbsp;<spring:message code="postat" />
				${artdto.createTime} &nbsp;
				<spring:message code="read" />
				(${artdto.readCount}) &nbsp; 
				<c:choose>
					<c:when test="${empty Current_User}"><spring:message code="suggest" /></c:when>
					<c:otherwise><a href="#" onClick="addSuggest()"><spring:message code="suggest" /></a></c:otherwise>
				</c:choose>
				(<span id="suggestCount">${artdto.suggestCount}</span>) &nbsp;
				<spring:message code="commit" />
				(<span id="comCount">${artdto.comCount}</span>)
			</p>
			<br />
		</div>
		<p>
			<spring:message code="commitList" />
		</p>
		<hr style="border: 1px dotted #CCC3C3" />
		<!-- 登录后的用户才显示评论区域 -->
		<c:choose>
			<c:when test="${! empty Current_User}">
				<div id="newComment">
					<table>
						<tr><td>评论人：</td><td><input disabled="disabled" value="${Current_User.userName}" /></td></tr>
						<tr><td>内容：</td><td><textarea id="comContent" rows="4" cols="40"></textarea></td></tr>
						<tr><td></td><td><button onclick="addComment()">提交</button></td></tr>
					</table>
				</div>
			</c:when>
		</c:choose>
		<div id="comDiv">
			<c:forEach items="${comList}" var="item" varStatus="status">
				<p>
					#${status.count}
					<spring:message code="floor" />
					&nbsp;${item.createTime}
					&nbsp;<a href="${ctxPath}/main/getArticleByCreateBy.do?userId=${item.creator}" target='_blank'>${item.creatorName}</a>
				</p>
				<p>${item.comContent}</p>
				<hr style="border: 1px dotted #E4DDDD" />
			</c:forEach>
		</div>
	</div>
	<%@include file="/WEB-INF/view/common/footer.jsp"%>
	<script type="text/javascript">
		//点击推荐
		function addSuggest() {
			var param = {
					articleId : $("#articleId").val(),
					articleTitle : $("#articleTitle").val()
				};
			
				$.ajax({
					type : 'POST',
					dataType : 'json',
					url : '${ctxPath}/admin/suggest/addSuggest.do', //新增信息
					data : $.param(param),
					success : function(data) {
						if (data && data.success == "true") {
							alert('推荐成功！');
							//将之前的推荐数字加1
							$("#suggestCount").html(parseInt($("#suggestCount").html())+1);
						} else {
							alert(data.content);
						}
					},
					error : function() {
						alert('推荐失败！网络错误！');
					}
				});
		}
		
		//提交评论
		function addComment() {
			if ($("#comContent").val()=="") {
				alert("请输入评论内容！");
				return;
			}
			
			var param = {
					articleId : $("#articleId").val(),
					articleTitle : $("#articleTitle").val(),
					comContent : $("#comContent").val()
				};
			
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${ctxPath}/admin/comment/addComment.do', //新增信息
				data : $.param(param),
				success : function(data) {
					if (data && data.success == "true") {
						alert('评论成功！');
						//将之前的评论数字加1
						$("#comCount").html(parseInt($("#comCount").html())+1);
					} else {
						alert(data.content);
					}
				},
				error : function() {
					alert('评论失败！网络错误！');
				}
			});
		}
	</script>
</body>
</html>






