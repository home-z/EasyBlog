<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>博客世界</title>
<%@include file="/common/resinculde.jsp"%>
<link href="${cssPath}/index.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#articleInfo {
	margin-left: 20px;
	margin-right: 20px;
	font-size: 15px;
	color: #333347;
	line-height: 18px;
	margin-top: 5px;
}

#postInfo {
	color: #9D9095;
	font-size: 13px;
}

hr {
	color: #c0c0c0;
}

.aPageDisable
{
	background:#1428E6;
	color:white;
	text-decoration: none;
}

#pageFilter a
{
	text-decoration: none;
	padding:1px 3px 1px 3px;
	border:1px solid #1BA1E2;
}
</style>
<script type="text/javascript">
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
					$.each(
						data.data,
						function(i, item) {
							strAllArticle += "<div>";
							strAllArticle += "<a href='${ctxPath}/MainIndex/getDetailById.do?id="
									+ item.id
									+ "' target='_blank'>";
							strAllArticle += item.title;
							strAllArticle += "</a>";
							strAllArticle += "<p>";
							strAllArticle += item.content;
							strAllArticle += "</p>";
							strAllArticle += "<p id='postInfo'>";
							strAllArticle += "<a style='text-decoration:none' href='${ctxPath}/MainIndex/getArticleByCreateBy.do?user="
									+ item.createBy
									+ "' target='_blank'>"
									+ item.createBy
									+ "</a>" + "发布于"
							strAllArticle += item.createTime;
							strAllArticle += "&nbsp;阅读("
									+ item.readCount + ")";
							strAllArticle += "&nbsp;推荐("
									+ item.suggestCount + ")";
							strAllArticle += "&nbsp;评论("
									+ item.comCount + ")";
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
	
	//页面首先加载全部
	$(document).ready(function() {
		getallArticle();
	});
</script>
</head>
<body>
	<%@include file="/common/menu.jsp"%>
	<div id="articleInfo"></div>
	<div id="pageFilter"></div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>