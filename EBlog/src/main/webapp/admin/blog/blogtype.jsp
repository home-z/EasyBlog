<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>博客类别管理</title>
	<%@include file="/common/resinculde.jsp"%>
	<%@include file="/common/checklogin.jsp"%>
	<link href="${cssPath}/admin.css" rel="stylesheet" type="text/css" />
	<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${jsPath}/jquery-easyui/local/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script src="${jsPath}/tools/json2.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${jsPath}/jquery-easyui/themes/${cookie.easyuiTheme.value==null?'metro-blue':cookie.easyuiTheme.value}/easyui.css"  
 id="swicth-style" />
</head>
<body>
	<div style="height: 400px; width: 100%;">
		<div id="tb" style="height: auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">刪除</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="save()">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">取消</a>
		</div>
		<table id="typeDataGrid"></table>
	</div>
</body>
<script type="text/javascript">
	var editIndex = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
		if ($('#typeDataGrid').datagrid('validateRow', editIndex)) {
			var ed = $('#typeDataGrid').datagrid('getEditor', {
				index : editIndex,
				field : 'typeName'
			});
			$('#typeDataGrid').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function onClickRow(index) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#typeDataGrid').datagrid('selectRow', index).datagrid(
						'beginEdit', index);
				editIndex = index;
			} else {
				$('#typeDataGrid').datagrid('selectRow', editIndex);
			}
		}
	}
	
	function append() {
		if (endEditing()) {
			$('#typeDataGrid').datagrid('appendRow', {
				typeName : "新类别"
			});
			editIndex = $('#typeDataGrid').datagrid('getRows').length - 1;
			$('#typeDataGrid').datagrid('selectRow', editIndex).datagrid(
					'beginEdit', editIndex);
		}
	}
	
	function removeit(index) {
		if (editIndex == undefined) {
			return;
		}
		$('#typeDataGrid').datagrid('cancelEdit', editIndex).datagrid(
				'deleteRow', editIndex);
		editIndex = undefined;
	}
	
	function save() {
		if (endEditing()) {
			//新增的行
			var newRows = $('#typeDataGrid').datagrid('getChanges', 'inserted');
			if (newRows.length > 0) {
				var newRowsJson = JSON.stringify(newRows);
				var param = {
					"newRowsJson" : newRowsJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/BlogType/addBlogType.do',
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
			var updateRow = $('#typeDataGrid')
					.datagrid('getChanges', 'updated');
			if (updateRow.length > 0) {
				var updateRowJson = JSON.stringify(updateRow);
				var param = {
					"updateRowJson" : updateRowJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/BlogType/updateBlogType.do',
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
			var deleteRows = $('#typeDataGrid').datagrid('getChanges',
					'deleted');
			if (deleteRows.length > 0) {
				var deleteRowsJson = JSON.stringify(deleteRows);
				var param = {
					"deleteRowsJson" : deleteRowsJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/BlogType/deleteBlogType.do',
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
			$('#typeDataGrid').datagrid('acceptChanges');
		}
	}
	
	function reject() {
		$('#typeDataGrid').datagrid('rejectChanges');
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
	
	$(document).ready(function() {
		$('#typeDataGrid').datagrid({
			url : '${ctxPath}/BlogType/getBlogTypeListByUser.do',
			singleSelect : true,
			toolbar : '#tb',
			onClickRow : onClickRow,
			fitColumns : true,
			fit : true,
			rownumbers : true,
			pagination : true,
			pageSize : 10,
			loadFilter : pagerFilter,
			columns : [ [ {
				field : 'typeName',
				title : '类别名称',
				width : 300,
				align : 'center',
				editor : 'text'
			}, {
				field : 'description',
				title : '描述',
				width : 300,
				align : 'center',
				editor : 'text'
			} ] ]
		});
	});
</script>
</html>