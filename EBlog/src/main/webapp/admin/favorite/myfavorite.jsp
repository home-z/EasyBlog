<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>我的关注</title>
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
	<div class="easyui-tabs" style="width: 700px; height: auto">
		<div title="关注的博客" style="padding: 10px">
			<div style="height: 400px; width: 100%;">
				<div id="tb" style="height: auto">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">刪除</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="save()">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">取消</a>
				</div>
				<table id="favUserDataGrid"></table>
			</div>
		</div>
		<div title="关注的文章" style="padding: 10px">
			<div style="height: 400px; width: 100%;">
				<div id="tbArticle" style="height: auto">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendArticle()">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeArticle()">刪除</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveArticle()">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectArticle()">取消</a>
				</div>
				<table id="favArticleDataGrid"></table>
			</div>
		</div>
	</div>
	<div id="addFavArticle" class="easyui-window" title="新增关注的文章" data-options="modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true,iconCls:'icon-add',onClose:afterCloseaddUserWin" style="width: 400px; height: 300px; padding: 10px 50px 20px 50px">
	<form id="favArticleForm" action="${ctxPath}/FavoriteArticle/addMyFavoriteArticle.do" method="post">
		<table cellpadding="5">
			<tr>
				<td>标题:</td>
				<td><input class="easyui-validatebox tb" type="text" name="articleTitle" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
			</tr>
			<tr>
				<td>网址:</td>
				<td><input class="easyui-validatebox tb" type="text" name="articleURL" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
			</tr>
			<tr>
				<td>描述:</td>
				<td><input class="easyui-validatebox" type="text" name="describle" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
			</tr>
		</table>
	</form>
	<div style="text-align: center; padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
</body>
<script type="text/javascript">
	//检测是否已经登录
	function checkLogin() {
		<c:choose>
		<c:when test="${empty Current_User}">
		location.href = "${ctxPath}/admin/login.jsp";
		</c:when>
		</c:choose>
	}
	var editIndex = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
		if ($('#favUserDataGrid').datagrid('validateRow', editIndex)) {
			var ed = $('#favUserDataGrid').datagrid('getEditor', {
				index : editIndex,
				field : 'favUser'
			});
			$('#favUserDataGrid').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onDbClickRow(index) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#favUserDataGrid').datagrid('selectRow', index).datagrid(
						'beginEdit', index);
				editIndex = index;
			} else {
				$('#favUserDataGrid').datagrid('selectRow', editIndex);
			}
		}
	}
	function append() {
		if (endEditing()) {
			$('#favUserDataGrid').datagrid('appendRow', {
				favUser : "博主名称",
				describle : "描述"
			});
			editIndex = $('#favUserDataGrid').datagrid('getRows').length - 1;
			$('#favUserDataGrid').datagrid('selectRow', editIndex).datagrid(
					'beginEdit', editIndex);
		}
	}
	function removeit(index) {
		if (editIndex == undefined) {
			return;
		}
		$('#favUserDataGrid').datagrid('cancelEdit', editIndex).datagrid(
				'deleteRow', editIndex);
		editIndex = undefined;
	}
	function save() {
		if (endEditing()) {
			//新增的行
			var newRows = $('#favUserDataGrid').datagrid('getChanges',
					'inserted');
			if (newRows.length > 0) {
				var newRowsJson = JSON.stringify(newRows);
				var param = {
					"newRowsJson" : newRowsJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/FavoriteUser/addMyFavoriteUser.do',
					dataType : 'json',
					data : param,
					success : function(data) {
						if (data && data.success == "true") {
							$.messager.alert('成功', '新增成功！');
						}
					},
					error : function() {
						$.messager.alert('error', 'error');
					}
				})
			}
			//更新的行
			var updateRow = $('#favUserDataGrid').datagrid('getChanges',
					'updated');
			if (updateRow.length > 0) {
				var updateRowJson = JSON.stringify(updateRow);
				var param = {
					"updateRowJson" : updateRowJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/FavoriteUser/updateMyFavoriteUser.do',
					dataType : 'json',
					data : param,
					success : function(data) {
						if (data && data.success == "true") {
							alert("更新成功！");
						}
					},
					error : function() {
						alert("error");
					}
				})
			}
			//删除
			var deleteRows = $('#favUserDataGrid').datagrid('getChanges',
					'deleted');
			if (deleteRows.length > 0) {
				var deleteRowsJson = JSON.stringify(deleteRows);
				var param = {
					"deleteRowsJson" : deleteRowsJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/FavoriteUser/deleteMyFavoriteUser.do',
					dataType : 'json',
					data : param,
					success : function(data) {
						if (data && data.success == "true") {
							alert("删除成功！");
						}
					},
					error : function() {
						alert("error");
					}
				})
			}
			//界面上接受，避免刷新
			$('#favUserDataGrid').datagrid('acceptChanges');
		}
	}
	function reject() {
		$('#favUserDataGrid').datagrid('rejectChanges');
		editIndex = undefined;
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
	
		function pagerFilterArticle(data) {
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
	
	function appendArticle() {
		$('#addFavArticle').window('open');
	}
	
	function submitForm() {
		$('#favArticleForm').form('submit');
	}
	function clearForm() {
		$('#favArticleForm').form('clear');
	}
	function afterCloseaddUserWin()
	{
	    $('#favArticleDataGrid').datagrid('reload');
	}
	
	$(document).ready(function() {
		checkLogin();
		$('#favUserDataGrid').datagrid({
			url : '${ctxPath}/FavoriteUser/getMyFavoriteUser.do',
			singleSelect : true,
			width : 700,
			height : 300,
			toolbar : '#tb',
			onDblClickRow : onDbClickRow,
			fitColumns : true,
			fit : true,
			rownumbers : true,
			pagination : true,
			pageSize : 10,
			loadFilter : pagerFilter,
			columns : [ [ {
				field : 'favUser',
				title : '博主',
				width : 300,
				align : 'center',
				formatter:
				function(value,row){
					return "<a href='${ctxPath}/MainIndex/getArticleByCreateBy.do?user="
					+ value + "' target='_blank'>"+value+"</a>" 
				},
				editor:{
					type:'combobox',
					options:{
						valueField:'text',
						textField:'text',
						method:'get',
						url:'${ctxPath}/User/getUserCode.do',
						required:true
							}
			}
			}, {
				field : 'describle',
				title : '描述',
				width : 300,
				align : 'center',
				editor : 'text'
			} ] ]
		});
		
		$('#favArticleDataGrid').datagrid({
			url : '${ctxPath}/FavoriteArticle/getMyFavoriteArticle.do',
			singleSelect : true,
			width : 700,
			height : 300,
			toolbar : '#tbArticle',
			fitColumns : true,
			fit : true,
			rownumbers : true,
			pagination : true,
			pageSize : 10,
			loadFilter : pagerFilterArticle,
			columns : [ [ {
				field : 'articleTitle',
				title : '标题',
				width : 300,
				align : 'center',
				formatter:
					function(value,row){
						return "<a href='"
						+ row.articleURL + "' target='_blank'>"+value+"</a>" 
					}
			}, {
				field : 'describle',
				title : '描述',
				width : 300,
				align : 'center',
				editor : 'text'
			} ] ]
		});
	});
</script>
</html>