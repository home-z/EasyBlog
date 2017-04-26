<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/context.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试kindeditor</title>
<link rel="stylesheet" href="../../js/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="../../js/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="../../js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="../../js/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="../../js/kindeditor/plugins/code/prettify.js"></script>
<script>
	KindEditor.ready(function(K) {
		var editor1 = K.create('textarea[name="content"]', {
			cssPath : '../../js/kindeditor/plugins/code/prettify.css',
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
			}
		});
		prettyPrint();
	});
</script>
</head>
<body>
	<div id="container">
		<div id="mainContent">
			<form name="example" method="post" action="${ctxPath}/BlogInfo/saveInfo.do">
				题目： <input type="text" name="title"> <br /> 内容：
				<textarea name="content" cols="100" rows="8"></textarea>
				<br /> <input type="submit" name="button" value="提交" /> (提交快捷键: Ctrl + Enter)
			</form>
		</div>
	</div>
</body>
</html>