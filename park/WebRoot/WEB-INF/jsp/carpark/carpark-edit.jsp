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
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
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
					<i>停车场编辑</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="carpark_edit" method="post" enctype="multipart/form-data"  id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="edit"/>
							<input type="hidden" name="carpark.carparkid" value="${carpark.carparkid}"/>
							<input type="hidden" name="carpark.isAuthentication" value="${carpark.isAuthentication}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">名称： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="name" name="carpark.name" value="${carpark.name}"
						            	 	data-rule="名称:required; length[~200, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">省市区： </td>
						            <td align="left">
						            	<select name="carpark.province" data-rule="省市区:required; "></select>
						            	<select name="carpark.city" ></select>
						            	<select name="carpark.county" ></select>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px" valign="top">地址： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="address" name="carpark.address"  value="${carpark.address}"/> 
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">停车场类别： </td>
						            <td align="left">
						           		<s:select list="carparkCategories" listKey="categoryid" listValue="name" 
						           		value="carpark.carparkCategory.categoryid" data-rule="停车场类别:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="carpark.carparkCategory.categoryid"></s:select>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">车位数： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" data-rule="integer; " name="carpark.totalBerth"  value="${carpark.totalBerth}"/>
						            </td>
					            </tr>
					            <tr>
					             <td align="right" width="110px" valign="top">车场图片： </td>
						            <td align="left">
						            	<input type="file" class="mes_form1" name="upload"  /> 
						            	<input type="hidden" name="carpark.picarr" value="${carpark.picarr}"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">允许外来停车： </td>
						            <td align="left">
						           		<s:select list="#{'0':'不允许','1':'允许'}" listKey="key" listValue="value" data-rule="允许外来停车:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="carpark.isAllowed" value="carpark.isAllowed"></s:select>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">首停时间(分钟)： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="beforeMins" name="carpark.beforeMins"  value="${carpark.beforeMins}"
						            	 	data-rule="integer; range[0~]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">首停价格(元)： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="beforePrice" name="carpark.beforePrice" value="${carpark.beforePrice}"
						            	 	data-rule="range[0~]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">每增时间(分钟)： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="afterMins" name="carpark.afterMins" value="${carpark.afterMins}"
						            	 	data-rule="integer; range[0~]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">每增费用(元)： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="afterPrice" name="carpark.afterPrice" value="${carpark.afterPrice}"
						            	 	data-rule="range[0~]"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px" valign="top">收费标准： </td>
						            <td align="left">
						            	<textarea name="carpark.feeRates" style="width: 270px; height: 100px" id="feeRates">${carpark.feeRates}</textarea>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">地图类型： </td>
						            <td align="left">
						            	<s:select list="#{'1':'微地图','2':'精确入口','3':'其他'}" listKey="key" listValue="value" 
						            	value="carpark.maptype" data-rule="地图类型:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="carpark.maptype"></s:select>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">显示级别： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="showLevel" name="carpark.showLevel" value="${carpark.showLevel}"
						            	 	data-rule="显示级别:required; integer; range[0~22]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">高德经度： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="longitude" name="carpark.longitude" value="${carpark.longitude}"
						            	 	data-rule="高德经度:required; range[-180~180]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">高德纬度： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="latitude" name="carpark.latitude" value="${carpark.latitude}"
						            	 	data-rule="高德纬度:required; range[-180~180] "/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">微地图左下经度： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="leftBottomLon" name="carpark.leftBottomLon" value="${carpark.leftBottomLon}"
						            	 	data-rule="range[-180~180] "/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">微地图左下纬度： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="leftBottomLat" name="carpark.leftBottomLat" value="${carpark.leftBottomLat}"
						            	 	data-rule="range[-180~180]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">微地图右上经度： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="rightTopLon" name="carpark.rightTopLon" value="${carpark.rightTopLon}"
						            	 	data-rule="range[-180~180]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">微地图右上纬度： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="rightTopLat" name="carpark.rightTopLat" value="${carpark.rightTopLat}"
						            	 	data-rule="range[-180~180]"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">高德停车场分类： </td>
						            <td align="left">
						            	<s:select list="#{'1':' 地面车场','2':'地下车场','3':'机械车库','4':'立体车库','5':'路边','6':'其它'}" listKey="key" listValue="value" 
						            	value="carpark.amapCarparkType" data-rule="高德停车场分类:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="carpark.amapCarparkType"></s:select>
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
new PCAS("carpark.province","carpark.city","carpark.county","${carpark.province}","${carpark.city}","${carpark.county}");
$(document).ready(function(){
	$(".m_carpark_l").attr("class","lhover");
})
</script>
</body>
</html>