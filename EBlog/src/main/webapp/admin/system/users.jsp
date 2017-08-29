<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>系统用户管理</title>
	<%@include file="/common/resinculde.jsp"%>
	<%@include file="/common/checklogin.jsp"%>
	<link href="${cssPath}/admin.css" rel="stylesheet" type="text/css" />
	<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${jsPath}/jquery-easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${jsPath}/jquery-easyui/themes/${cookie.easyuiTheme.value==null?'metro-blue':cookie.easyuiTheme.value}/easyui.css"  
 id="swicth-style" />
</head>
<body>
	<div style="height: 100%; width: 100%;">
		<div id="tb" style="height: auto">
			<a href="usersEdit.jsp" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteUser()">刪除</a> <a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="showSearchWin()">查找</a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-downlevel',plain:true" onclick="exportUser()">导出</a>
		</div>
		<table id="usersDataGrid"></table>
		<input type="hidden" id="currentUserId" />
		<div id="searchUserWin" class="easyui-window" title="查找用户" data-options="modal:true,closed:true,iconCls:'icon-search',minimizable:false,maximizable:false,collapsible:false" style="width: 450px; height: 300px; padding: 10px 50px 20px 50px">
			<table cellpadding="5">
				<tr>
					<td>登录名:</td>
					<td><input id="userCode" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" type="text" name="userCode"></input></td>
				</tr>
				<tr>
					<td>用户名称:</td>
					<td><input id="userName" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" type="text" name="userName"></input></td>
				</tr>
				<tr>
					<td>邮箱:</td>
					<td><input id="email" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" type="text" name="email"></input></td>
				</tr>
			</table>
			<div style="text-align: center; padding: 5px">
				<a id="btnSearch" href="javascript:void(0)" class="easyui-linkbutton">查找</a> <a id="btnReset"  href="javascript:void(0)" class="easyui-linkbutton">重置</a>
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

	//弹出搜索框
	function showSearchWin() {
		$('#searchUserWin').window('open');
	}

	//删除选择的用户
	function deleteUser() {
		var checkedRows = $('#usersDataGrid').datagrid('getChecked');//获取选择的用户
		if (checkedRows.length > 0) {
			$.messager.confirm('确认', '确定删除？', function(r) {
				if (r) {
					var deleteIds = [];//获取待删除的用户id
					for (var i = 0; i < checkedRows.length; i++) {
						//id为0、1的账号为系统预置账号，不能删除
						if (checkedRows[i].id == "0"|| checkedRows[i].id == "1") {
							$.messager.alert('提醒', '不能删除系统预置账号！');
							
							return;
						} else if (checkedRows[i].id == $("#currentUserId").val()) {
							$.messager.alert('提醒', '不能删除当前登录用户账号！');
							
							return;
						} else {
							deleteIds.push(checkedRows[i].id);//获取用户id
						}
					}

					//调用删除。数组转字符串，以“,”分割
					var param = {
						"userId" : deleteIds.join(",")
					};
					$.ajax({
						type : 'GET',
						contentType : 'application/json',
						url : '${ctxPath}/User/deleteUser.do',
						dataType : 'json',
						data : param,
						success : function(data) {
							if (data && data.success == "true") {
								$.messager.alert("成功", "用户删除成功！", "info");
							} else {
								$.messager.alert("失败", "用户删除失败！");
							}
							$('#usersDataGrid').datagrid('reload');//重新刷新
						},
						error : function() {
							$.messager.alert("错误", "删除用户发送网络异常！", "error");
						}
					})
				}
			});

		} else {
			$.messager.alert('提醒', '请选择需要删除的用户！', 'warning');
		}
	}

	//导出用户到excel中
	function exportUser() {
		var checkedRows = $('#usersDataGrid').datagrid('getChecked');//获取选择的用户
		if (checkedRows.length > 0) {
			var selectedIds = [];
			for (var i = 0; i < checkedRows.length; i++) {
				selectedIds.push(checkedRows[i].id);//获取用户id
			}
			//调用导出
			var param = {
				"userId" : selectedIds.join(",")
			//数组转字符串，以“,”分割
			};
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : '${ctxPath}/User/exportUser.do',
				dataType : 'json',
				data : param,
				error : function() {
					$.messager.alert("错误", "导出用户发生网络异常！", "error");
				}
			})
		} else {
			$.messager.confirm('确认', '未选择要导出的记录，则会导出全部记录。确定导出全部记录？',
					function(r) {
						if (r) {
							$.ajax({
								type : 'GET',
								contentType : 'application/json',
								url : '${ctxPath}/User/exportAllUser.do',
								dataType : 'json',
								data : param,
								error : function() {
									$.messager.alert("错误", "导出用户发生网络异常！","error");
								}
							})
						}
					});
		}
	}

	//分页
	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') {
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

	$(document).ready(
		function() {
			checkLogin();
			$("#currentUserId").val("${Current_User.id}");//获取当前登录用户id
			$('#usersDataGrid').datagrid({
				url : '${ctxPath}/User/searchUser.do',
				toolbar : '#tb',
				rownumbers : true,
				pagination : true,
				fitColumns : true,
				fit : true,
				pageSize : 10,
				loadFilter : pagerFilter,
				columns : [[
						{
							checkbox : true,
						},
						{
							field : 'userName',
							title : '用户名',
							width : 100,
							align : 'center',
							formatter : function(value, row,index) {
								return "<a href='${ctxPath}/User/editUser.do?userId="
										+ row.id
										+ "'>"
										+ row.userName
										+ "</a>";
							}
						},
						{
							field : 'userCode',
							title : '登录名',
							width : 100,
							align : 'center'
						},
						{
							field : 'email',
							title : '邮箱',
							width : 150,
							align : 'center'
						},
						{
							field : 'createTime',
							title : '创建时间',
							width : 150,
							align : 'center',
							formatter : function(value, row,index) {
								return new Date(parseInt(row.createTime)).toLocaleDateString();
							}
						},
						{
							field : 'creator',
							title : '创建人',
							width : 150,
							align : 'center'
						},
						{
							field : 'modifiedTime',
							title : '修改时间',
							width : 150,
							align : 'center',
							formatter : function(value, row,index) {
								if (row.modifiedTime == ""|| row.modifiedTime == null) {
									return "未修改";
								} else {
									return new Date(parseInt(row.modifiedTime)).toLocaleDateString();
								}
							}
						}, {
							field : 'modifiedtor',
							title : '修改人',
							width : 150,
							align : 'center'
						}]]
			});
			
			$('#btnSearch').click(function() {
				$('#usersDataGrid').datagrid('load', {
					vuserCode : $("#userCode").val(),
					vuserName : $("#userName").val(),
					vemail : $("#email").val()
				});
				
				$('#searchUserWin').window('close');
			});
			
			$('#btnReset').click(function() {
				$("#userCode").val("");
				$("#userName").val("");
				$("#email").val("");
			});
	});
</script>
</html>