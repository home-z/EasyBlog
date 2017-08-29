/**
 * 首页js 2017-07-08
 */

// 按照文章分类读取
function getCategory() {
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : getContextPath() + '/MainIndex/getCategory.do',
		dataType : 'json',
		success : function(data) {
			if (data && data.success == "true") {
				$('#categoryLinks').html(data.data);
			} else {
				alert("读取文章分类出错！");
			}
		},
		error : function() {
			alert("读取文章分类发生网络异常！");
		}
	});
}

// 生成分页控件
function getPageFilter(action) {
	$('#pageFilter').empty();// 先清空分页信息，避免aPageChange方法中先获取到了上一次的元素
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : getContextPath() + '/MainIndex/getArticlePage.do?action=' + action,
		dataType : 'json',
		success : function(data) {
			if (data && data.success == "true") {
				$('#pageFilter').html(data.data);
			} else {
				alert("读取分页信息出错！");
			}
		},
		error : function() {
			alert("读取分页信息发生网络异常！");
		}
	});
}

// 根据传递的url，取不同的结果，显示在页面
function getArticle(url) {
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : url,
		dataType : 'json',
		success : function(data) {
			if (data && data.success == "true") {
				var strAllArticle = "";
				$.each(data.data, function(i, item) {
					strAllArticle += "<div>";
					strAllArticle += "<a href='" + getContextPath() + "/MainIndex/getDetailById.do?id=";
					strAllArticle += item.id;
					strAllArticle += "' target='_blank'>";
					strAllArticle += item.title;
					strAllArticle += "</a>";
					strAllArticle += "<p>";
					strAllArticle += item.content;
					strAllArticle += "</p>";
					strAllArticle += "<p id='postInfo'>";
					strAllArticle += "<a style='text-decoration:none' href='" + getContextPath() + "/MainIndex/getArticleByCreateBy.do?user=";
					strAllArticle += item.createBy;
					strAllArticle += "' target='_blank'>";
					strAllArticle += item.createBy;
					strAllArticle += "</a>";
					strAllArticle += "发布于";
					strAllArticle += item.createTime;
					strAllArticle += "&nbsp;阅读(";
					strAllArticle += item.readCount + ")";
					strAllArticle += "&nbsp;推荐(";
					strAllArticle += item.suggestCount + ")";
					strAllArticle += "&nbsp;评论(";
					strAllArticle += item.comCount + ")";
					strAllArticle += "</p>";
					strAllArticle += "</div>";
					strAllArticle += "<hr id='articleSplit'/>";
				});
				$('#articleInfo').html(strAllArticle);
			}
		},
		error : function() {
			alert("读取文章信息发生网络异常！");
		}
	});
}

/**
 * menu.jsp页面中点击菜单，调用对应控制器方法，生成分页即内容
 * 
 * @param method
 *            调用的控制器方法 首页：getallArticle； 热读：getArticleRead； 热评：getArticleCommit；
 *            推荐：getArticleSuggest；
 * @returns
 */
function getArticleByMenu(method) {
	getPageFilter("/MainIndex/" + method + ".do");// 生成底部的分页
	aPageChange();// 分页切换
	getArticle(getContextPath() + "/MainIndex/" + method + ".do?page=1");
}

// 分页切换
function aPageChange() {
	var aDisablePage = $(".aPageDisable");
	var diable_change = function(target) {
		// 解决第一次加载获取不到元素的情况
		if (aDisablePage.html() == null) {
			aDisablePage = $(".aPageDisable");
		}
		aDisablePage.removeClass("aPageDisable");
		target.addClass("aPageDisable");
		aDisablePage = target;
	};
	// 后来新增的元素，使用live方法
	$("#pageFilter a").live("click", function() {
		diable_change($(this));
	});
}

// 点击分类
function getArticleType(typeid) {
	// 生成分页
	getPageFilter("/MainIndex/getArticleByType.do?typeid=" + typeid);// 生成底部的分页
	aPageChange();
	getArticle(getContextPath() + '/MainIndex/getArticleByType.do?typeid=' + typeid + '&page=1');
}

// 点击右侧分类，则增加对应分类菜单
function addTypeMenu(typeName, typeid) {
	// 该元素不存在，则创建
	if ($("#" + typeid + "").length <= 0) {
		$("#divMenu ul").append(
						"<li><a id=\""
								+ typeid
								+ "\" href='javascript:void(0);' onclick='getArticleType("
								+ typeid + ")''>" + typeName + "</a></li>");
	}

	// 触发click事件
	$("#" + typeid + "").click();
}

// 搜索
function searchKeyword() {
	var keyword = $("#txtKeyword").val();
	if (keyword != "") {
		// 显示查询菜单
		if ($("#searchMenu").length <= 0) {
			$("#divMenu ul").append(
							"<li><a id='searchMenu' href='javascript:void(0);'>搜索</a></li>");
		}

		$("#searchMenu").click();// 只是为了加上选中样式而进行一次点击
		getSearchBlog(keyword);
	} else {
		alert("请输入关键词！");
	}
}

// 点击搜索
function getSearchBlog(keyword) {
	var param = {
		keyword : keyword
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		url : getContextPath() + "/BlogSearch/searchBlog.do",
		data : $.param(param),
		success : function(data) {
			if (data && data.total != 0) {
				var strSearchArticle = "";
				$.each(
					data.rows,
					function(i, item) {
						strSearchArticle += "<div>";
						strSearchArticle += "<a href='"
								+ getContextPath()
								+ "/MainIndex/getDetailById.do?id=";
						strSearchArticle += item.id;
						strSearchArticle += "' target='_blank'>";
						strSearchArticle += item.title;
						strSearchArticle += "</a>";
						strSearchArticle += "<p>";
						strSearchArticle += item.content;
						strSearchArticle += "</p>";
						strSearchArticle += "<p id='postInfo'>";
						strSearchArticle += "<a style='text-decoration:none' href='"
								+ getContextPath()
								+ "/MainIndex/getArticleByCreateBy.do?user=";
						strSearchArticle += item.createBy;
						strSearchArticle += "' target='_blank'>";
						strSearchArticle += item.createBy;
						strSearchArticle += "</a>";
						strSearchArticle += "发布于";
						strSearchArticle += item.createTime;
						strSearchArticle += "&nbsp;阅读("
						strSearchArticle += item.readCount
								+ ")";
						strSearchArticle += "&nbsp;推荐(";
						strSearchArticle += item.suggestCount
								+ ")";
						strSearchArticle += "&nbsp;评论(";
						strSearchArticle += item.comCount
								+ ")";
						strSearchArticle += "</p>";
						strSearchArticle += "</div>";
						strSearchArticle += "<hr id='articleSplit' />";
					});
				$('#articleInfo').html(strSearchArticle);
				// TODO实现查询分页
				$('#pageFilter').html("");
			}
		},
		error : function() {
			alert('搜索出现网络错误！');
		}
	});
}

// 页面首先加载全部
$(document).ready(function() {
	getArticleByMenu('getallArticle');// 页面加载，先默认点击了首页菜单
	getCategory();
});

