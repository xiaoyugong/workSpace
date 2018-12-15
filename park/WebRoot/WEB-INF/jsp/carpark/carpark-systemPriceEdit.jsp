<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>停车场|泊泊停车</title>
	<link rel="stylesheet" type="text/css" href="<%=path %>/datetimepicker/css/jquery-ui.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />

	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/datetimepicker/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/datetimepicker/js/jquery-ui-slide.min.js"></script>
	<script type="text/javascript" src="<%=path %>/datetimepicker/js/jquery-ui-timepicker-addon.js"></script>

	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/pcas/PCASClass.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	
	
	<style type="text/css">
		select{height: 28px; line-height: 28px}
	</style>
	<script type="text/javascript">
	
	</script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>系统定价</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="carpark_systemPriceEdit" method="post" enctype="multipart/form-data"  id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="save"/>
							<input type="hidden" name="carpark.carparkid" value="${carpark.carparkid}"/>
							<input type="hidden" name="carpark.isAuthentication" value="${carpark.isAuthentication}"/>
					        <table width="100%" border="0" class="adduser_tab">
					       		<tr>
						            <td align="right" width="110px"> </td>
						            <td align="left">
						            	临停
						            </td>
						             <td align="left">
						            	预约
						            </td>
					            </tr>
					        
					        	<tr>
						            <td align="right" width="110px">首停时间： </td>
						            <td align="left">
						            	<input type="number" class="mes_form1" id="beforeMinsTemporary" name="carpark.carparkSystemPrice.beforeMinsTemporary" value="${carpark.carparkSystemPrice.beforeMinsTemporary}"
						            	 	data-rule="首停时间:required; length[~200, true]"/>
						            </td>
						            
						             <td align="left">
						            	<input type="number" class="mes_form1" id="beforeMinsReserve" name="carpark.carparkSystemPrice.beforeMinsReserve" value="${carpark.carparkSystemPrice.beforeMinsReserve}"
						            	 	data-rule="首停时间:required; length[~200, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">首停价格： </td>
						            <td align="left">
						            	<input type="number" class="mes_form1" id="beforePriceTemporary" name="carpark.carparkSystemPrice.beforePriceTemporary" value="${carpark.carparkSystemPrice.beforePriceTemporary}"
						            	 	data-rule="首停价格:required; length[~200, true]"/>
						            </td>
						            <td align="left">
						            	<input type="number" class="mes_form1" id="beforePriceReserve" name="carpark.carparkSystemPrice.beforePriceReserve" value="${carpark.carparkSystemPrice.beforePriceReserve}"
						            	 	data-rule="首停价格:required; length[~200, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px" valign="top">每增时间： </td>
						            <td align="left">
										<input type="number" class="mes_form1" id="afterMinsTemporary" name="carpark.carparkSystemPrice.afterMinsTemporary" value="${carpark.carparkSystemPrice.afterMinsTemporary}"
						            	 	data-rule="每增时间:required; length[~200, true]"/>						          
						            </td>
						            <td align="left">
										<input type="number" class="mes_form1" id="afterMinsReserve" name="carpark.carparkSystemPrice.afterMinsReserve" value="${carpark.carparkSystemPrice.afterMinsReserve}"
						            	 	data-rule="每增时间:required; length[~200, true]"/>						          
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">每增价格： </td>
						            <td align="left">
						           		<input type="number" class="mes_form1" id="afterPriceTemporary" name="carpark.carparkSystemPrice.afterPriceTemporary" value="${carpark.carparkSystemPrice.afterPriceTemporary}"
						            	 	data-rule="每增价格:required; length[~200, true]"/>	
						            </td>
						             <td align="left">
						           		<input type="number" class="mes_form1" id="afterPriceReserve" name="carpark.carparkSystemPrice.afterPriceReserve" value="${carpark.carparkSystemPrice.afterPriceReserve}"
						            	 	data-rule="每增价格:required; length[~200, true]"/>	
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">违约保证金： </td>
						            <td align="left">
						            	<input type="number" class="mes_form1" id="violatePriceTemporary" name="carpark.carparkSystemPrice.violatePriceTemporary" value="${carpark.carparkSystemPrice.violatePriceTemporary}"
						            	 	data-rule="违约保证金:required; length[~200, true]"/>	
						            </td>
						             <td align="left">
						            	<input type="number" class="mes_form1" id="violatePriceReserve" name="carpark.carparkSystemPrice.violatePriceReserve" value="${carpark.carparkSystemPrice.violatePriceReserve}"
						            	 	data-rule="违约保证金:required; length[~200, true]"/>	
						            </td>
					            </tr>
					            <tr>
					              <td align="right" width="110px">分享开始时间： </td>
						            <td align="left">
						            <input type="text" class="mes_form1" id="shareStartTime" name="shareStartTime" value="${carpark.carparkSystemPrice.strShareStartTime}"
						            	 	data-rule="分享开始时间:required; length[~200, true]"/>
						            </td>
						       	 </tr>
						       	 <tr>
					              <td align="right" width="110px">分享结束时间： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="shareEndTime" name="shareEndTime" value="${carpark.carparkSystemPrice.strShareEndTime}"
						            	 data-rule="分享结束时间:required; length[~200, true]"	/>	
						            </td>
						       	 </tr>
						       	 <tr>
					              <td align="right" width="110px">分享周期： </td>
						            <td align="left" width="300px">
						            	
						            	星期一：<input type="checkbox" name="weekday" value="2" <c:if test="${carpark.carparkSystemPrice.shareRepeatDate.contains('2')}">checked</c:if>/>，
						            	星期二：<input type="checkbox" name="weekday" value="3" <c:if test="${carpark.carparkSystemPrice.shareRepeatDate.contains('3')}">checked</c:if>/>，
						            	星期三：<input type="checkbox" name="weekday" value="4" <c:if test="${carpark.carparkSystemPrice.shareRepeatDate.contains('4')}">checked</c:if>/>，
						            	星期四：<input type="checkbox" name="weekday" value="5" <c:if test="${carpark.carparkSystemPrice.shareRepeatDate.contains('5')}">checked</c:if>/>
						            	星期五：<input type="checkbox" name="weekday" value="6" <c:if test="${carpark.carparkSystemPrice.shareRepeatDate.contains('6')}">checked</c:if>/>，
						            	星期六：<input type="checkbox" name="weekday" value="7" <c:if test="${carpark.carparkSystemPrice.shareRepeatDate.contains('7')}">checked</c:if>/>，
						            	星期日：<input type="checkbox" name="weekday" value="1" <c:if test="${carpark.carparkSystemPrice.shareRepeatDate.contains('1')}">checked</c:if>/>
						            </td>
						       	 </tr>
						       	 
						       	 <tr>
					              <td align="right" width="120px">是否包含出入场费用： </td>
						            <td align="left">
						            	<s:select list="#{'0':'否','1':'是'}" listKey="key" listValue="value" data-rule="是否包含出入场费用:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="carpark.carparkSystemPrice.isContainExitPrice" value="carpark.carparkSystemPrice.isContainExitPrice"></s:select>
						            </td>
						       	 </tr>
						       	 <tr>
					              <td align="right" width="200px">入场时间门槛(默认15分钟)： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="systemEnterTime" name="carpark.carparkSystemPrice.systemEnterTime" value="${carpark.carparkSystemPrice.systemEnterTime}"
						            	 data-rule="入场时间门槛:required; length[~200, true]"	/>	
						            </td>
						       	 </tr>
						       	 	 <tr>
					              <td align="right" width="200px">离场时间门槛(默认15分钟)： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="systemLeaveTime" name="carpark.carparkSystemPrice.systemLeaveTime" value="${carpark.carparkSystemPrice.systemLeaveTime}"
						            	 data-rule="离场时间门槛:required; length[~200, true]"	/>	
						            </td>
						       	 </tr>
						       	 <tr>
					              <td align="right" width="110px">定价类型： </td>
						            <td align="left">
						            	<s:select list="#{'1':'强制定价','2':'参考定价','3':'默认定价'}" listKey="key" listValue="value" data-rule="定价类型:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="carpark.carparkSystemPrice.priceType" value="carpark.carparkSystemPrice.priceType"></s:select>
						            </td>
						       	 </tr>
					            <tr>
					              <td align="right" width="110px">入场须知： </td>
						            <td align="left">
						     			<textarea  rows="4" cols="30" name="carpark.carparkSystemPrice.description" data-rule="入场须知:required;"  >${carpark.carparkSystemPrice.description}</textarea>
						            </td>
						       	 </tr>
					        </table>
							<div class="tijiao">
					        	<input type="submit" value="提  交" class="btn1" />
					        </div>
						</form>   	
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#shareStartTime').timepicker({
	   hourGrid: 2,
	   minuteGrid: 30
   });
  	 	$('#shareEndTime').timepicker({
	   hourGrid: 2,
	   minuteGrid: 30
   });
	});
new PCAS("carpark.province","carpark.city","carpark.county","${carpark.province}","${carpark.city}","${carpark.county}");
$(document).ready(function(){
	$(".m_carpark_l").attr("class","lhover");
});
</script>

</body>
</html>