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
	
	#txtUserCode,#txtPass{
		width: 200px;
		height:25px;
		border:2px solid #D5CECE;
	}
	
	#btnCancel,#btnLogin{
		height:30px;
		width:90px;
		border:2px solid #D5CECE;
		background-color: #2EBB4E;
		color: white;
	}
	
	#btnCancel:HOVER,#btnLogin:HOVER {
	background-color: #20923A;
}
</style>
<script type="text/javascript" src="${jsPath}/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    
	    $("#txtUserCode").blur(function() {
	        if ($("#txtUserCode").val() == "") {
	            $("#exMsg").html("用户名不能为空！");
	        } else {
	            $("#exMsg").html("");
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
	                        $("#exMsg").html($("#txtUserCode").val() + "：该用户不存在！");
	                    } else {
	                        $("#exMsg").html("");
	                    }
	                },
	                error: function() {
	                	$("#exMsg").html("错误！");
	                }
	            });
	        }
	    });
	    
	    $("#txtUserCode").focus(function() {
	   	  $(this).css('border', '2px solid #69C3C1');
	   	  }).blur(function() {
	   	    $(this).css('border', '');
	   	  });
	    	  
	    $("#txtPass").focus(function() {
    	    $(this).css('border', '2px solid #69C3C1');
    	  }).blur(function() {
    	    $(this).css('border', '');
    	  });
    
	});
		
	function checkForm() {
		var username = $("#txtUserCode").val();
		var password = $("#txtPass").val();
		if (username == null || username == "") {
			$("#exMsg").html("用户名不能为空！");
			return false;
		}
		if (password == null || password == "") {
			$("#exMsg").html("密码不能为空！");
			return false;
		}
		return true;
	}
	
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
	<table width="600" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 5px solid #5590E8;background-color: #FEFEFE;border-color: ">
		<tbody>
			<tr>
				<td align="center"><br>
					<table width="570" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" width="570" border="0">
									<tbody>
										<tr>
										    <td><img style="float: right;" src="${imgPath}/log.jpg"></td>
											<td width="230" height="40" align="center" valign="top">
												<p id="logTitle" style="height: 10px; width: 230px; font-size: 25px;color:#E33E06;">博客世界管理-登录</p>
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
								<form id="loginForm" action="${ctxPath}/Login/login.do" method="post" onsubmit="return checkForm()">
									<table width="520" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="center">
												<table cellspacing="0" cellpadding="5" border="0">
													<tr>
														<td height="25" valign="top">用户名： <input tabindex="1" maxlength="22" size="25" name="userCode" id="txtUserCode">
														</td>
													</tr>
													<tr>
														<td valign="bottom" height="12">密&nbsp;&nbsp; 码： <input name="userPassWord" type="password" tabindex="1" size="25" maxlength="22" id="txtPass">
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
										<td height="70" align="center">版权所有：Tim©2017</td>
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