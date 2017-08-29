<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>博客世界-登录</title>
	<link href="${cssPath}/login.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${jsPath}/tools/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${jsPath}/tools/common.js"></script>
	<script type="text/javascript" src="${jsPath}/view/login.js"></script>
</head>
<body style="background-color: #EEF2F5;">
    <a style="float: right;" href="../admin/loginByPhoto.jsp">使用头像登录</a>
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
											<td align="right" valign="top"><a href="${ctxPath}/admin/register.jsp">立即注册</a><a href="${ctxPath}/index.jsp">回到主页</a></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<p style="height: 5px;" id="exMsg"></p>
								<form id="loginForm">
									<table width="520" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="center">
												<table cellspacing="0" cellpadding="5" border="0">
													<tr>
														<td height="25" valign="top">用户名： <input tabindex="1" maxlength="22" size="25" name="userCode" id="txtUserCode" />
														</td>
													</tr>
													<tr>
														<td valign="bottom" height="12">密&nbsp;&nbsp; 码： <input name="userPassWord" type="password" tabindex="1" size="25" maxlength="22" id="txtPass" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
														<input type="button" id="btnCancel" onClick="cancel();" value="取消" /> 
														<input type="button" id="btnLogin" value="登录" />
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