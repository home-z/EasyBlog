<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文章内容管理</title>
	<%@include file="/common/resinculde.jsp"%>
	<%@include file="/common/checklogin.jsp"%>
	<link href="${cssPath}/admin.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${jsPath}/jquery-easyui/themes/${cookie.easyuiTheme.value==null?'metro-blue':cookie.easyuiTheme.value}/easyui.css"  
 id="swicth-style" />
	<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${jsPath}/jquery-easyui/local/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	<p>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="showSearchWin()">查找</a>
	</p>
	<div id="blogSearchWin" class="easyui-window" title="查找博客" data-options="modal:true,closed:true,iconCls:'icon-search'" style="width: 500px; height: 300px; padding: 10px 50px 20px 50px">
		<table cellspacing="10">
			<tr>
				<td>文章类型：</td>
				<td><input class="easyui-combobox" id="comblogType" data-options="
								url:'${ctxPath}/BlogType/getBlogTypeByUser.do',
								width:100,
								method:'get',
								valueField:'id',
								textField:'text',
								panelHeight:'auto'
						"></td>
			</tr>
			<tr>
				<td>文章标题：</td>
				<td><input id="txtTitle" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" /></td>
			</tr>
			<tr>
				<td>发布时间：</td>
				<td><input id="startDate" style="width: 100px;" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"> — </input> <input id="endDate" style="width: 100px;" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></input></td>
			</tr>
			<tr>
				<td>文章内容：</td>
				<td><input id="txtContent" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" /></td>
			</tr>
		</table>
		<div align="center">
			<a id="btnSearch" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> <a id="btnReset" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'">重置</a>
		</div>
	</div>
	<table id="blogListDataGrid"></table>
	</div>
<script type="text/javascript">
	function showSearchWin() {
		$('#blogSearchWin').window('open');
	}
	
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	}
	
	function myparser(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	}

	//解码及转换成链接
	function decodeValue(value, row, index) {
		return s = "<a href=\"${ctxPath}/BlogInfo/edit.do?blogid=" + row.BlogID
				+ "\">" + decodeURI(value) + "</a>";
	}
	
	function decodeTypeName(value, rec) {
		return decodeURI(value);
	}
	
	//查询
	function search() {
		$('#blogListDataGrid').datagrid('load', {
			vblogType : $("#comblogType").combobox('getValue'),
			vTitle : encodeURI($("#txtTitle").val()),
			vstartDate : $('#startDate').datebox('getValue'),
			vendDate : $('#endDate').datebox('getValue'),
			vContent : encodeURI($("#txtContent").val())
		});
	}
	
	//重置
	function reset() {
		$("#comblogType").combobox('setValue', '');
		$("#txtTitle").val("");
		$('#startDate').datebox('setValue', "");
		$('#endDate').datebox('setValue', "");
		$("#txtContent").val("");
	}
	
	//检测是否已经登录
	function checkLogin() {
		<c:choose>
		<c:when test="${empty Current_User}">
		location.href = "${ctxPath}/admin/login.jsp";
		</c:when>
		</c:choose>
	}
	
	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $(this);
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		pager.pagination({
			onSelectPage : function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh', {
					pageNumber : pageNum,
					pageSize : pageSize
				});
				dg.datagrid('loadData', data);
			}
		});
		if (!data.originalRows) {
			data.originalRows = (data.rows);
		}
		var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = (data.originalRows.slice(start, end));
		return data;
	}
	
	$(document).ready(function() {
		checkLogin();
		$('#blogListDataGrid').datagrid({
			url : '${ctxPath}/BlogInfo/searchBlog.do',
			width : 700,
			height : 400,
			singleSelect : true,
			fitColumns : true,
			fit : true,
			method : 'get',
			rownumbers : true,
			pagination : true,
			pageSize : 10,
			loadFilter : pagerFilter,
			columns : [ [ {
				field : 'Title',
				title : '标题',
				width : 500,
				formatter : decodeValue
			}, {
				field : 'CreateTime',
				title : '发布时间',
				width : 100
			}, {
				field : 'BlogTypeName',
				title : '类别',
				width : 100,
				formatter : decodeTypeName
			} ] ]
		});
		
		$('#btnSearch').click(function() {
			search();
			$('#blogSearchWin').window('close');
		});
		
		$('#btnReset').click(function() {
			reset();
		});
	});
</script>
</html>