<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<link href="${cssPath}/gotoTop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${jsPath}/gotoTop.js"></script>
<script type="text/javascript">
	// 定义菜单栏离页面顶部的距离，默认为100    
	var divOffsetTop = 100;
	//获取已经登录的用户名称
	function getCurrentUserName() {
		<c:choose>
		<c:when test="${empty Current_User}">
		$("#loginInfo").html("<a href='${ctxPath}/Login/loginpage.do'>登录</a>");
		</c:when>
		<c:otherwise>
		$("#loginInfo").html("<a href='${ctxPath}/admin/admin.jsp'>${Current_User.userName}</a> <a href='${ctxPath}/admin/logout.jsp'>注销</a>");
		</c:otherwise>
		</c:choose>
	}
	$(document).ready(function() {
		getCurrentUserName();
		//点击菜单，加载背景
		var subNav_active = $(".adv_active");
		var subNav_change = function(target) {
			subNav_active.removeClass("adv_active");
			target.parent().addClass("adv_active");
			subNav_active = target.parent();
		};
		$("#divMenu a").click(function() {
			subNav_change($(this));
		});
		//页面加载完之后，计算菜单栏到页面顶部的实际距离
		var divMenu = document.getElementById("topMenu");
		divOffsetTop = divMenu.offsetTop - 5;//因为设置了margin-top=5
		gotoTop();//加载“返回顶部按钮”
		$(window).scroll(function() {
			//滚动固定菜单栏
			// 计算页面滚动了多少（需要区分不同浏览器）    
			var topVal = 0;
			if (window.pageYOffset) {//这一条滤去了大部分， 只留了IE678    
				topVal = window.pageYOffset;
			} else if (document.documentElement.scrollTop) {//IE678 的非quirk模式    
				topVal = document.documentElement.scrollTop;
			} else if (document.body.scrolltop) {//IE678 的quirk模式    
				topVal = document.body.scrolltop;
			}
			if (topVal <= divOffsetTop) {
				divMenu.style.position = "";
			} else {
				divMenu.style.position = "fixed";
			}
		});
	});
</script>
<div id="topMenu">
	<div id="top">
		<table width="100%">
			<tr>
				<td><img src="${imgPath}/log.jpg"></td>
				<td><div id="logTitle">博客世界</div></td>
				<td><div id="midSearch">
						<input /><span>搜索一下</span>
					</div></td>
				<td style="text-align: right"><span id="loginInfo"></span>&nbsp; <a href="admin/register.jsp">注册</a></td>
			</tr>
		</table>
	</div>

	<div id="divMenu">
		<ul>
			<!--<li class="adv_active"><a href="javascript:void(0);" onclick="getArticle('${ctxPath}/MainIndex/getallArticle.do?page=1')">首页</a></li>
			  <li><a href="javascript:void(0);" onclick="getArticle('${ctxPath}/MainIndex/getArticleRead.do')">热读</a></li>
			  <li><a href="javascript:void(0);" onclick="getArticle('${ctxPath}/MainIndex/getArticleCommit.do')">热评</a></li>
			  -->
			<li class="adv_active"><a href="javascript:void(0);" onclick="getallArticle()">首页</a></li>
			<li><a href="javascript:void(0);" onclick="getArticleRead()">热读</a></li>
			<li><a href="javascript:void(0);" onclick="getArticleSuggest()">推荐</a></li>
			<li><a href="javascript:void(0);" onclick="getArticleCommit()">热评</a></li>
		</ul>
	</div>

</div>
