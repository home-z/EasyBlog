<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客世界-登录</title>
<link href="${cssPath}/login.css" type="text/css" rel="stylesheet">
<style type="text/css">
#exMsg {
	color: red;
}
</style>
<script type="text/javascript" src="${jsPath}/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $("#btnLogin").attr({
	        "disabled": "disabled"
	    }); //禁用登录按钮
	    $("#txtUserCode").blur(function() {
	        if ($("#txtUserCode").val() == "") {
	            $("#exMsg").html("用户名不能为空，请重新输入用户名！");
	            $("#btnLogin").attr({
	                "disabled": "disabled"
	            }); //禁用登录按钮
	        } else {
	            $("#exMsg").html("");
	            $("#btnLogin").removeAttr("disabled"); //将按钮可用
	            //参数，这里是一个json语句
	            var param = {
	                "usercode": $("#txtUserCode").val()
	            };
	            $.ajax({
	                type: 'GET',
	                contentType: 'application/json',
	                url: '${ctxPath}/Login/isExist.do',
	                dataType: 'json',
	                data: param,
	                success: function(data) {
	                    if (data && data.success == "false") {
	                        $("#exMsg").html("用户名为：" + $("#txtUserCode").val() + "的用户不存在，请检查用户名！");
	                        $("#btnLogin").attr({
	                            "disabled": "disabled"
	                        }); //禁用登录按钮
	                    } else {
	                        $("#exMsg").html("");
	                        $("#btnLogin").removeAttr("disabled"); //将按钮可用
	                    }
	                },
	                error: function() {
	                    alert("error");
	                }
	            });
	        }
	    });
	});
		
	//按下回车键
	document.onkeypress = function(e) {
		e = e || event;
		if (e.keyCode == 13) {
			document.getElementById("btnLogin").click();
			event.returnValue = false;
		}
	}
	//取消
	function cancel() {
		$("#txtUserCode").val("");
		$("#txtPass").val("");
	}
</script>
</head>
<body style="background-color: #EEF2F5;">
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<table width="620" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 5px solid #5590E8;background-color: #FEFEFE;">
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
												<p style="height: 10px; width: 245px; font-size: 25px;">博客世界管理-登录</p>
											</td>
											<td align="right" valign="top"><a href="register.jsp">立即注册</a> <a href="../index.jsp">回到主页</a></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<p style="height: 5px;" id="exMsg"></p>
								<form id="loginForm" action="${ctxPath}/Login/login.do" method="post">
									<table width="520" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="center">
												<table cellspacing="0" cellpadding="5" border="0">
													<tr>
														<td height="25" valign="top">用户名： <input style="width: 200px;height:25px;border:1px solid #D5CECE;" tabindex="1" maxlength="22" size="25" name="userCode" id="txtUserCode">
														</td>
													</tr>
													<tr>
														<td valign="bottom" height="12">密&nbsp;&nbsp; 码： <input name="userPassWord" style="width: 200px;height:25px;border:1px solid #D5CECE;" type="password" tabindex="1" size="25" maxlength="22" id="txtPass">
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <input type="button" id="btnCancel" onClick="cancel();" value="取消"> <input type="button" id="btnLogin" value="登录" onClick="$('#loginForm').submit();">
														</td>
													</tr>
												</table>
											</td>
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