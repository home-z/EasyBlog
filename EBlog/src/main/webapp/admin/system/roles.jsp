<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色权限管理</title>
	<%@include file="/common/resinculde.jsp"%>
	<%@include file="/common/checklogin.jsp"%>
	<link href="${cssPath}/admin.css" rel="stylesheet" type="text/css" />
	<link href="${jsPath}/jquery-easyui/themes/metro-blue/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${jsPath}/jquery-easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style scoped="scoped">
.tb {
	width: 100%;
	margin: 0;
	padding: 5px 4px;
	border: 1px solid #ccc;
	box-sizing: border-box;
}
</style>
</head>
<body>
	<div style="height: 400px; width: 100%;">
		<div id="tb" style="height: auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">刪除</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="save()">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">取消</a>
		</div>
		<table id="usersDataGrid"></table>
		<div id="addUserWin" class="easyui-window" title="新增用户" data-options="modal:true,closed:true,iconCls:'icon-add'" style="width: 450px; height: 300px; padding: 10px 50px 20px 50px">
			<form id="userForm" action="${ctxPath}/User/addUser.do" method="post">
				<table cellpadding="5">
					<tr>
						<td>登录名:</td>
						<td><input class="easyui-validatebox tb" type="text" name="userCode" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input id="userPassword" class="easyui-validatebox tb" type="password" name="userPassword" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
					</tr>
					<tr>
						<td>确认密码:</td>
						<td><input class="easyui-validatebox tb" type="password" data-options="required:true,validateOnCreate:false,validateOnBlur:true" validType="equals['#userPassword']"></input></td>
					</tr>
					<tr>
						<td>用户名称:</td>
						<td><input class="easyui-validatebox" type="text" name="userName" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
					</tr>
					<tr>
						<td>邮箱:</td>
						<td><input class="easyui-validatebox tb" type="text" name="email" data-options="required:true,validType:'email',validateOnCreate:false,validateOnBlur:true"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
			</div>
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
	$.extend($.fn.validatebox.defaults.rules, {
		equals : {
			validator : function(value, param) {
				return value == $(param[0]).val();
			},
			message : '两次密码不一致！'
		}
	});
	function submitForm() {
		$('#userForm').form('submit');
	}
	function clearForm() {
		$('#userForm').form('clear');
	}
	
	var editIndex = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true;
		}
		if ($('#usersDataGrid').datagrid('validateRow', editIndex)) {
			var ed = $('#usersDataGrid').datagrid('getEditor', {
				index : editIndex,
				field : 'typeName'
			});
			$('#usersDataGrid').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#usersDataGrid').datagrid('selectRow', index).datagrid(
						'beginEdit', index);
				editIndex = index;
			} else {
				$('#usersDataGrid').datagrid('selectRow', editIndex);
			}
		}
	}
	function append() {
		$('#addUserWin').window('open');
	}
	function removeit(index) {
		if (editIndex == undefined) {
			return;
		}
		$('#usersDataGrid').datagrid('cancelEdit', editIndex).datagrid(
				'deleteRow', editIndex);
		editIndex = undefined;
	}
	function save() {
		if (endEditing()) {
			//更新的行
			var updateRow = $('#usersDataGrid').datagrid('getChanges',
					'updated');
			if (updateRow.length > 0) {
				var updateRowJson = JSON.stringify(updateRow);
				var param = {
					"updateRowJson" : updateRowJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/User/updateUser.do',
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
			var deleteRows = $('#usersDataGrid').datagrid('getChanges',
					'deleted');
			if (deleteRows.length > 0) {
				var deleteRowsJson = JSON.stringify(deleteRows);
				var param = {
					"deleteRowsJson" : deleteRowsJson
				};
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/User/deleteUser.do',
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
			$('#usersDataGrid').datagrid('acceptChanges');
		}
	}
	function reject() {
		$('#usersDataGrid').datagrid('rejectChanges');
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
		checkLogin();
		$('#usersDataGrid').datagrid({
			url : '${ctxPath}/User/index.do',
			singleSelect : true,
			toolbar : '#tb',
			onClickRow : onClickRow,
			rownumbers : true,
			pagination : true,
			fitColumns : true,
			fit : true,
			pageSize : 10,
			loadFilter : pagerFilter,
			columns : [ [ {
				field : 'userCode',
				title : '登录名',
				width : 200
			}, {
				field : 'userName',
				title : '用户名称',
				width : 300,
				editor : 'text'
			}, {
				field : 'email',
				title : '邮箱',
				width : 300,
				editor : 'text'
			} ] ]
		});
	});
</script>
</html>