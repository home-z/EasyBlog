<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>博客世界</title>
<%@include file="/common/resinculde.jsp"%>
<link href="${cssPath}/index.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#container {
	
}

#articleInfo {
	margin: 10px;
	font-size: 15px;
	color: #333347;
	line-height: 18px;
	margin-top: 5px;
}

#leftArticle {
	width: 80%;
	float: left;
	margin: 5px;
	border: 1px solid #D6D6D6;
}

#rightCategory {
	float: left;
	font-size: 15px;
	color: #333347;
	line-height: 15px;
	margin: 5px;
	width: 18%;
	border: 1px solid #D6D6D6;
}

#postInfo {
	color: #9D9095;
	font-size: 12px;
	margin-top: 5px;
}

hr {
	color: #c0c0c0;
}

#categoryLinks{
	line-height: 25px;
	margin-left: 20px;
}

#categoryLinks li{
	list-style:none;
}

#rightCategory p{
	margin: 5px;
}
</style>
<script type="text/javascript">

	//按照文章分类读取
	function getCategory() {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : '${ctxPath}/MainIndex/getCategory.do',
			dataType : 'json',
			success : function(data) {
				if (data && data.success == "true") {
					$('#categoryLinks').html(data.data);
				}
			},
			error : function() {
				alert("error");
			}
		});
	}

	//生成分页控件
	function getPageFilter(action) {
		$('#pageFilter').empty();//先清空分页信息，避免aPageChange方法中先获取到了上一次的元素
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : '${ctxPath}/MainIndex/getArticlePage.do?action='+action,
			dataType : 'json',
			success : function(data) {
				if (data && data.success == "true") {
					$('#pageFilter').html(data.data);
				}
			},
			error : function() {
				alert("error");
			}
		});
	}

	//根据传递的url，取不同的结果
	function getArticle(url) {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : url,
			dataType : 'json',
			success : function(data) {
				if (data && data.success == "true") {
					var strAllArticle = "";
					$.each(data.data,
						function(i, item) {
							strAllArticle += "<div>";
							strAllArticle += "<a href='${ctxPath}/MainIndex/getDetailById.do?id="
							strAllArticle += item.id
							strAllArticle += "' target='_blank'>";
							strAllArticle += item.title;
							strAllArticle += "</a>";
							strAllArticle += "<p>";
							strAllArticle += item.content;
							strAllArticle += "</p>";
							strAllArticle += "<p id='postInfo'>";
							strAllArticle += "<a style='text-decoration:none' href='${ctxPath}/MainIndex/getArticleByCreateBy.do?user="
							strAllArticle += item.createBy
							strAllArticle += "' target='_blank'>"
							strAllArticle += item.createBy
							strAllArticle += "</a>"
							strAllArticle += "发布于"
							strAllArticle += item.createTime;
							strAllArticle += "&nbsp;阅读("
							strAllArticle += item.readCount + ")";
							strAllArticle += "&nbsp;推荐("
							strAllArticle += item.suggestCount + ")";
							strAllArticle += "&nbsp;评论("
							strAllArticle += item.comCount + ")";
							strAllArticle += "</p>";
							strAllArticle += "</div>";
							strAllArticle += "<hr style='border: 1px dotted #E4DDDD' />";
						});
					$('#articleInfo').html(strAllArticle);
				}
			},
			error : function() {
				alert("error");
			}
		});
	}
	
	//点击“首页”
	function getallArticle()
	{
	    //生成分页
	    getPageFilter("/MainIndex/getallArticle.do");//生成底部的分页
	    aPageChange();
		getArticle('${ctxPath}/MainIndex/getallArticle.do?page=1');
	}
	
	//点击“热读”
	function getArticleRead()
	{
		//生成分页
	    getPageFilter("/MainIndex/getArticleRead.do");//生成底部的分页
	    aPageChange();
		getArticle('${ctxPath}/MainIndex/getArticleRead.do?page=1');
	}
	
	//点击“热评”
	function getArticleCommit()
	{
		//生成分页
	    getPageFilter("/MainIndex/getArticleCommit.do");//生成底部的分页
	    aPageChange();
		getArticle('${ctxPath}/MainIndex/getArticleCommit.do?page=1');
	}
	
    //点击“推荐”
	function getArticleSuggest()
	{
		//生成分页
	    getPageFilter("/MainIndex/getArticleSuggest.do");//生成底部的分页
	    aPageChange();
		getArticle('${ctxPath}/MainIndex/getArticleSuggest.do?page=1');
	}
	
	//分页切换
	function aPageChange()
	{
		var aDisablePage = $(".aPageDisable");
		var diable_change = function(target) {
			//解决第一次加载获取不到元素的情况
			if(aDisablePage.html()==null)
			{
				aDisablePage=$(".aPageDisable");
			}
			aDisablePage.removeClass("aPageDisable");
			target.addClass("aPageDisable");
			aDisablePage = target;
		};
		//后来新增的元素，使用live方法
		$("#pageFilter a").live("click",function() {
			diable_change($(this));
		});
	}
	
	//点击分类
	function getArticleType(typeid)
	{
		//生成分页
	    getPageFilter("/MainIndex/getArticleByType.do?typeid="+typeid);//生成底部的分页
	    aPageChange();
		getArticle('${ctxPath}/MainIndex/getArticleByType.do?typeid='+typeid+'&page=1');
	}
	
	//点击右侧分类，则增加对应分类菜单
	function addTypeMenu(typeName,typeid) {
		//该元素不存在，则创建
		if($("#"+typeid+"").length <= 0) {
			$("#divMenu ul").append("<li><a id=\""+typeid+"\" href='javascript:void(0);' onclick='getArticleType("+typeid+")''>"+typeName+"</a></li>");
		}
		
		//触发click事件
		$("#"+typeid+"").click();
	}
	
	//搜索
	function searchKeyword() {
		var keyword=$("#txtKeyword").val();
		if (keyword!="") {
			//显示查询菜单
			if($("#searchMenu").length <= 0) {
				$("#divMenu ul").append("<li><a id='searchMenu' href='javascript:void(0);'>搜索</a></li>");
			}
			
			$("#searchMenu").click();//只是为了加上选中样式而进行一次点击
			getSearchBlog(keyword);
		}
		else {
			alert("请输入关键词！");
		}
	}
	
	//点击搜索
	function getSearchBlog(keyword) {
		var param = {
				keyword : keyword
			};
	     $.ajax({
				type : 'POST',
				dataType : "json",
				url : "${ctxPath}/BlogSearch/searchBlog.do",
				data : $.param(param),
				success : function(data) {
					if (data && data.total != 0) {
						var strSearchArticle = "";
						$.each(data.rows,
							function(i, item) {
							strSearchArticle += "<div>";
							strSearchArticle += "<a href='${ctxPath}/MainIndex/getDetailById.do?id="
							strSearchArticle += item.id
							strSearchArticle += "' target='_blank'>";
							strSearchArticle += item.title;
							strSearchArticle += "</a>";
							strSearchArticle += "<p>";
							strSearchArticle += item.content;
							strSearchArticle += "</p>";
							strSearchArticle += "<p id='postInfo'>";
							strSearchArticle += "<a style='text-decoration:none' href='${ctxPath}/MainIndex/getArticleByCreateBy.do?user="
							strSearchArticle += item.createBy
							strSearchArticle += "' target='_blank'>"
							strSearchArticle += item.createBy
							strSearchArticle += "</a>"
							strSearchArticle += "发布于"
							strSearchArticle += item.createTime;
							strSearchArticle += "&nbsp;阅读("
							strSearchArticle += item.readCount + ")";
							strSearchArticle += "&nbsp;推荐("
							strSearchArticle += item.suggestCount + ")";
							strSearchArticle += "&nbsp;评论("
							strSearchArticle += item.comCount + ")";
							strSearchArticle += "</p>";
							strSearchArticle += "</div>";
							strSearchArticle += "<hr style='border: 1px dotted #E4DDDD' />";
							});
						$('#articleInfo').html(strSearchArticle);
						//TODO实现查询分页
						$('#pageFilter').html("");
					}
				},
				error : function() {
					alert('error');
				}
			});
	}
	
	//页面首先加载全部
	$(document).ready(function() {
		getallArticle();
		getCategory();
	});
</script>
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