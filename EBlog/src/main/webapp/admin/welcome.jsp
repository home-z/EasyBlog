<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>欢迎</title>
	<%@include file="/common/resinculde.jsp"%>
	<%@include file="/common/checklogin.jsp"%>
</head>
<body>
	<div style="padding: 10px;">
		<span>开始日期：</span> <input id="txtStartDate" class="easyui-datebox" style="width: 100%;"> <span>结束日期：</span> <input id="txtEndDate" class="easyui-datebox" style="width: 100%;"> <span>统计方式：</span> <select id="styleStatistics" class="easyui-combobox" panelheight="auto"
			style="width: 100px;">
			<option value="0">按天统计</option>
			<option value="1">按月统计</option>
			<option value="2">按年统计</option>
		</select> <a id="btnSum" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">统计</a>
		<div id="blogPost" style="height: 400px"></div>
	</div>
	<script src="${jsPath}/MyECharts/resource/ECharts/echarts.js" type="text/javascript"></script>
	<script src="${jsPath}/MyECharts/resource/MyECharts.js" type="text/javascript"></script>
	<link href="${jsPath}/jquery-easyui/themes/metro-blue/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${jsPath}/jquery-easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript">
		//生成统计图
		function getPost() {
			//作为外部页面的子页面，生成图后会刷掉时间值，这里如果值为空，则设置为默认值
			if ($('#txtStartDate').datebox('getValue') == "") {
				$("#txtStartDate").datebox("setValue", getStartDate());
			}

			if ($("#txtEndDate").datebox("getValue") == "") {
				$("#txtEndDate").datebox("setValue", getEndDate());
			}

			var param = {
				styleType : $('#styleStatistics').combobox('getValue'),
				startDate : $('#txtStartDate').datebox('getValue'),
				endDate : $('#txtEndDate').datebox('getValue')
			};

			$.ajax({
				type : 'POST',
				dataType : 'json',
				data : $.param(param),
				url : '${ctxPath}/BlogInfo/getBlogStatistics.do',
				success : function(data, textStatus) {
					var opt1 = MyECharts.ChartOptionTemplates.Bar('博客发表数量','篇', eval(data.data));
					MyECharts.RenderChart(opt1, 'blogPost');
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					$.messager.alert('My Title', 'Here is a error message!',
							'error');
				}
			});
		}

		//设置默认查询时间段
		//mgetMonth(); 获取当前月份(0-11,0代表1月)，所以获取当前月份是myDate.getMonth()+1; 
		//TODO，这里有bug，算上个月有误
		function getStartDate() {
			var curr_time = new Date();
			var y = curr_time.getFullYear();
			var m = curr_time.getMonth();
			var d = curr_time.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'+ (d < 10 ? ('0' + d) : d);
		}

		function getEndDate() {
			var curr_time = new Date();
			var y = curr_time.getFullYear();
			var m = curr_time.getMonth() + 1;
			var d = curr_time.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'+ (d < 10 ? ('0' + d) : d);
		}

		$(document).ready(function() {
			$('#btnSum').live('click', function() {
				getPost();
			});

			$("#txtStartDate").datebox("setValue", getStartDate());
			$("#txtEndDate").datebox("setValue", getEndDate());

			getPost();
		});
	</script>
</body>
</html>