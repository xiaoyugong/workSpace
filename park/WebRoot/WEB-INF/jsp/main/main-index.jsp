<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String path = request.getContextPath();
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/index.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" />
	<script type="text/javascript">
	$(document).ready(function(){
		/*****统计*****/
		$('#appChannelPie').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: ''
	        },
	        credits: {
			     enabled: false
			},
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.y}人</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true
	                },
                    showInLegend: true
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '注册用户数',
	            data: [
	                ${appChannelPieData}
	            ]
	        }]
	    });
	})
</script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="">首页</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="zsy-nr">
				<div class="zsy-ul" style="width: 400px">
					<div class="zsy-u-bt" style="width: 400px">
						<em>APP渠道统计</em>
					</div>
					<div class="zsy-u-nr" id="appChannelPie" style="width: 400px; height: 340px">
					</div>
				</div>
			</div>

		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".content-left dt").each(function(){
		var str = $(this).text();
			str = str.replace(/\s+/g,""); 
		if(str==bttext){
			$(this).parent().attr({"class":""})
		}
	})
})
</script>
<script type="text/javascript" src="<%=path%>/page/plugins/highcharts/highcharts.js"></script>
</body>
</html>