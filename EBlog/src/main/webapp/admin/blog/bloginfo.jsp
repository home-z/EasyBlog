<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文章新建/编辑</title>
	<%@include file="/common/resinculde.jsp"%>
	<%@include file="/common/checklogin.jsp"%>
	<link href="${cssPath}/admin.css" rel="stylesheet" type="text/css" />
	<link href="${jsPath}/jquery-easyui/themes/metro-blue/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${jsPath}/jquery-easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<link href="${jsPath}/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${jsPath}/kindeditor/plugins/code/prettify.css" />
	<script src="${jsPath}/kindeditor/kindeditor-min.js" type="text/javascript"></script>
	<script src="${jsPath}/kindeditor/lang/zh_CN.js" type="text/javascript"></script>
	<script src="${jsPath}/kindeditor/plugins/code/prettify.js"></script>
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
	<p>
		<a <c:choose><c:when test="${not empty artdto}"></c:when>
									<c:otherwise>style="display: none;"</c:otherwise>
								</c:choose> id="btnDelete" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">删除</a>
	</p>
	<form id="dbForm" name="dbForm" action="${ctxPath}/BlogInfo/saveInfo.do" method="post">
		<input id="vblogid" type="hidden" name="id" value="${artdto.id}" />
		<input type="hidden" name="createBy" value="${artdto.createBy}" />
		<input type="hidden" name="oldcreateTime" value="${artdto.createTime}" />
		<input type="hidden" name="oldcomCount" value="${artdto.comCount}" />
		<input type="hidden" name="oldreadCount" value="${artdto.readCount}" />
		<input id="currenttypeName" type="hidden" name="currenttypeName" value="${artdto.typeName}" />
		<div style="width: 100%;">
			<div id="palBaseInfo" class="easyui-panel" title="基本信息" style="width: 100%; height: 50px; padding: 10px;" data-options="fit:true,collapsible:true">
				<div align="center">
					<table cellspacing="10">
						<tr>
							<td>文章标题：</td>
							<td><input name="title" value="${artdto.title}" id="txtTitle" class="easyui-validatebox tb" data-options="required:true,validateOnCreate:false,validateOnBlur:true" style="width: 300; height: 22px; border: 1px solid #95B8E7;" /></td>
							<td>文章类型：</td>
							<td><input name="typeId" class="easyui-combobox" id="comblogType" data-options="
								url:'${ctxPath}/BlogType/getBlogTypeByUser.do',
								onSelect : function(param){
                					$('#currenttypeName').val(param.text);
       							},
								width:100,
								method:'get',
								valueField:'id',
								textField:'text',
								panelHeight:'auto'
								<c:choose>
									<c:when test="${empty artdto}"></c:when>
									<c:otherwise>,value:${artdto.typeId}</c:otherwise>
								</c:choose>"></td>
							<c:choose>
								<c:when test="${empty artdto}"></c:when>
								<c:otherwise>
									<td>发布时间：</td>
									<td><p id="postDate">${artdto.createTime}</p></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div style="width: 100%;">
			<div class="easyui-panel" title="文章内容" style="height: 320px; width: 100%;" data-options="fit:true">
				<textarea name="content" style="width: 99%; height: 320px;">${artdto.content}</textarea>
			</div>
		</div>
		<div align="center">
			<input type="submit" name="button" value="保存" onclick="return validInput();" />
		</div>
	</form>
</body>
<script type="text/javascript">
	var vblogid;//文章id
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			cssPath : '../../js/kindeditor/plugins/code/prettify.css',
			resizeType : 1,
			allowImageUpload : false
		});
		prettyPrint();
	});
	//重置
	function reset() {
		$("#comblogType").combobox('setValue', '');
		$("#txtTitle").val("");
		editor.html('');//清空内容
	}
	//保存前，各个内容是否为空校验
	function validInput() {
		var vTitle = encodeURI($("#txtTitle").val());
		var vBlogType = $("#comblogType").combobox('getValue');
		var vContent = editor.html();//取得HTML内容
		if (vTitle == "") {
			$.messager.alert('提醒', '请输入标题！');
			return false;
		}
		if (vBlogType == "") {
			$.messager.alert('提醒', '请选择文章类型！');
			return false;
		}
		if (vContent == "") {
			$.messager.alert('提醒', '请输入文章内容！');
			return false;
		}
		return true;
	}
	function saveInfo() {
		var passInput = validInput();
		if (passInput) {
			var vTitle = encodeURI($("#txtTitle").val());
			var vBlogType = $("#comblogType").combobox('getValue');
			var vContent = editor.html();//取得HTML内容
			//新建
			if (vblogid == "") {
				var param = {
					vTitle : vTitle,
					vBlogType : vBlogType,
					vContent : vContent
				};
				$.ajax({
					type : 'POST',
					dataType : 'json',
					url : '../../BlogManage?tag=addBlog', //新增信息
					data : $.param(param),
					success : function(msg) {
						if (msg == 1) {
							$.messager.alert('成功', '新增成功！');
							window.location.href = "blog.jsp";
						} else {
							$.messager.alert('失败', '新增失败！');
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						$.messager.alert('失败', '新增失败！');
					}
				});
			} else {
				var param = {
					vBlogid : vblogid,
					vTitle : vTitle,
					vBlogType : vBlogType,
					vContent : vContent
				};
				$.ajax({
					type : 'POST',
					dataType : 'json',
					url : '../../BlogManage?tag=updateBlog', //更新信息
					data : $.param(param),
					success : function(msg) {
						if (msg == 1) {
							$.messager.alert('成功', '更新成功！');
						} else {
							$.messager.alert('失败', '更新失败！');
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						$.messager.alert('失败', '更新失败！');
					}
				});
			}
		}
	}
	//删除
	function deleteInfo() {
		$.messager
				.confirm(
						'确认',
						'确定删除该文章？',
						function(r) {
							if (r) {
								//文章id
								var param = {
									vblogid : $("#vblogid").val()
								};
								$
										.ajax({
											type : 'POST',
											dataType : "json",
											url : "${ctxPath}/BlogInfo/deleteBlog.do",
											data : $.param(param),
											success : function(data) {
												if (data
														&& data.success == "true") {
													$.messager.alert('成功',
															'删除成功！');
													window.location.href = "${ctxPath}/admin/blog/blog.jsp";
												} else {
													$.messager.alert('失败',
															'删除失败！');
												}
											},
											error : function(XmlHttpRequest,
													textStatus, errorThrown) {
												$.messager.alert('失败', '删除失败！');
											}
										});
							}
						});
	}
	//检测是否已经登录
	function checkLogin() {
		<c:choose>
		<c:when test="${empty Current_User}">
		location.href = "${ctxPath}/admin/login.jsp";
		</c:when>
		</c:choose>
	}
	$(document).ready(function() {
		checkLogin();
		$('#btnDelete').click(function() {
			deleteInfo();
		});
	});
</script>
</html>