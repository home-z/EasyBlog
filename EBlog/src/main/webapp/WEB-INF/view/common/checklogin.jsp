<%@ page pageEncoding="UTF-8"%>
<script type="text/javascript">
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
	});
</script>