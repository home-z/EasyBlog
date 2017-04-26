<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客世界-注册</title>
<link href="${cssPath}/login.css" type="text/css" rel="stylesheet">
<style type="text/css">
#exMsg {
	color: red;
}
</style>
<script type="text/javascript" src="${jsPath}/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#btnsubmit").attr({
					"disabled" : "disabled"
				});//禁用提交按钮
				
				$("#userCode").blur(
						function() {
							if ($("#userCode").val() == "") {
								$("#exMsg").html("用户名不能为空，请重新输入用户名！");
								$("#btnsubmit").attr({
									"disabled" : "disabled"
								});//禁用提交按钮
							}
							else
								{
								$("#exMsg").html("");
								$("#btnsubmit").removeAttr("disabled");//将按钮可用
								
								//参数，这里是一个json语句
								var param = {
									"usercode" : $("#userCode").val()
								};
								$.ajax({
									type : 'GET',
									contentType : 'application/json',
									url : '${ctxPath}/Login/isExist.do',
									dataType : 'json',
									data : param,
									success : function(data) {
										if (data && data.success == "true") {
											$("#exMsg").html(
													"用户名为：" + $("#userCode").val()
															+ "的用户已经存在，请重新输入用户名！");
											$("#btnsubmit").attr({
												"disabled" : "disabled"
											});//禁用提交按钮
										} else {
											$("#exMsg").html("");
											$("#btnsubmit").removeAttr("disabled");//将按钮可用
										}
									},
									error : function() {
										alert("error");
									}
								});
								}
						});

				//验证两次输入的密码是否一致
				$("#userPasswordAgain").blur(function() {
					var userPsword = $("#userPassword").val();
					var userPswordAgain = $("#userPasswordAgain").val();
					if (userPsword != userPswordAgain) {
						$("#exMsg").html("两次输入的密码不一致，请确认！");
						$("#btnsubmit").attr({
							"disabled" : "disabled"
						});//禁用提交按钮
					} else {
						$("#exMsg").html("");
						$("#btnsubmit").removeAttr("disabled");//将按钮可用
					}
				});

				//验证两次输入的密码是否一致
				$("#userPassword").blur(
					function() {
						var userPsword = $("#userPassword").val();
						var userPswordAgain = $(
								"#userPasswordAgain").val();
						if (userPswordAgain != ""
								&& userPsword != userPswordAgain) {
							$("#exMsg").html("两次输入的密码不一致，请确认！");
							$("#btnsubmit").attr({
								"disabled" : "disabled"
							});//禁用提交按钮
						} else {
							$("#exMsg").html("");
							$("#btnsubmit").removeAttr("disabled");//将按钮可用
						}
					});
			});
</script>
</head>
<body>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<table width="620" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 5px solid #5590E8;">
		<tbody>
			<tr>
				<td align="center"><br>
					<table width="570" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" width="570" border="0">
									<tbody>
										<tr>
											<td width="245" height="40" align="center" valign="top">
												<p style="height: 10px; width: 245px; font-size: 25px;">博客世界管理-注册</p>
											</td>
											<td align="right" valign="top"><a href="login.jsp">立即登录</a> <a href="../index.jsp">回到主页</a></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<p style="height: 5px;" id="exMsg"></p>
								<form id="myform" action="${ctxPath}/User/registerUser.do" method="post">
									<table>
										<tr>
											<td>用户名：</td>
											<td><input id="userCode" type="text" name="userCode" /></td>
										</tr>
										<tr>
											<td>密码：</td>
											<td><input id="userPassword" type="password" name="userPassword" /></td>
										</tr>
										<tr>
											<td>确认密码：</td>
											<td><input id="userPasswordAgain" type="password" name="userPasswordAgain" /></td>
										</tr>
										<tr>
											<td>姓名：</td>
											<td><input type="text" name="userName" /></td>
										</tr>
										<tr>
											<td>邮箱：</td>
											<td><input type="text" name="email" /></td>
										</tr>
										<tr>
											<td><input id="btnreset" type="reset" value="重填" /></td>
											<td><input id="btnsubmit" type="submit" value="提交" /></td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="70" align="center">版权所有：余飞©2016</td>
									</tr>
								</table>
							</td>
						</tr>
					</table></td>
			</tr>
		</tbody>
	</table>
</body>
</html>