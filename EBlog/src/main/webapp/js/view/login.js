/**
 * 登录页使用的脚本
 */

$(document).ready(function() {
	$("#txtUserCode").blur(function() {
		if ($("#txtUserCode").val() == "") {
			$("#exMsg").html("用户名不能为空！");
		} else {
			$("#exMsg").html("");
			// 参数，这里是一个json语句
			var param = {
				"userCode" : $("#txtUserCode").val()
			};
			$.ajax({
				type : 'GET',
				contentType : 'application/json',
				url : getContextPath()+'/Login/isExist.do',
				dataType : 'json',
				data : param,
				success : function(data) {
					if (data && data.success == "false") {
						$("#exMsg").html($("#txtUserCode").val() + "用户不存在！");
					} else {
						$("#exMsg").html("");
					}
				},
				error : function() {
					$("#exMsg").html("判断用户是否存在发生错误！");
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

	// 点击登录
	$("#btnLogin").click(function() {
		if (checkForm()) {
			var params = $("#loginForm").serialize();
			$.ajax({
				type : 'POST',
				url : getContextPath()+'/Login/login.do',
				data : params,
				dataType : 'json',
				success : function(data) {
					if (data && data.success == "true") {
						$("#exMsg").html($("#txtUserCode").val() + "登录成功！");
						window.location.href = "../admin/admin.jsp";// 跳转到管理页
					} else {
						$("#exMsg").html("登录失败，用户名或者密码错误！");
					}
				},
				error : function() {
					$("#exMsg").html("登录出现网络错误！");
				}
			});
		}
	});
});

// 登录前参数检查
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

// 按下回车键
document.onkeypress = function(e) {
	e = e || event;
	if (e.keyCode == 13) {
		$("#btnLogin").click();
		event.returnValue = false;
	}
}

// 取消
function cancel() {
	$("#txtUserCode").val("");
	$("#txtPass").val("");
}