<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.parkbobo.utils.*" %>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String clienturl = Configuration.getInstance().getValue("clienturl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>停车场内部道路|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
<script>

</script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场内部道路</a></div>
				<div class="z-r-b-r"><%=date %></div>
				
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>停车场内部道路${carparkRoadPolyline==null?'添加':'修改'}</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/carparkRoadPolyline_update" method="post" enctype="multipart/form-data"  id="saveForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="carparkRoadPolyline.gid" value="${carparkRoadPolyline.gid}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        <tr>
						            <td align="right" width="110px">停车场： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" readonly="readonly" name="carparkRoadPolyline.carpark.name" value="${carparkRoadPolyline.carpark.name}"
						            	 	/>
						            	 	<input type="hidden" value="${carparkRoadPolyline.carpark.carparkid}"  id="_carparkid" name="carparkRoadPolyline.carpark.carparkid"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">名称： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" id="name" name="carparkRoadPolyline.name" value="${carparkRoadPolyline.name}"
						            	 	/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">楼层ID： </td>
						            <td align="left">
						            	<s:if test="floors.size==0">
						            		<select>
						            			<option value="">---无--</option>
						            		</select>
						            	</s:if>
						            	<s:else>
							            	<s:select list="floors" listValue="name" name="carparkRoadPolyline.floorid" listKey="floorid" theme="simple"></s:select>
						            	</s:else>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">背景色： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.color}" class="mes_form1" data-rule="required,integer;" name="carparkRoadPolyline.color"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">边框色： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.bordercolor}" data-rule="integer;" class="mes_form1" name="carparkRoadPolyline.bordercolor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体颜色： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.fontColor}" data-rule="integer;" class="mes_form1" name="carparkRoadPolyline.fontColor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体大小： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.fontSize}"   class="mes_form1" name="carparkRoadPolyline.fontSize"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体加粗： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.fontWeight}"data-rule="integer;"  class="mes_form1" name="carparkRoadPolyline.fontWeight"/>
						            </td>
					            </tr>
					             <tr>
					            	<td align="right" width="110px">宽度： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.width}"  class="mes_form1" name="carparkRoadPolyline.width"/>
						            </td>
					            </tr>
					            <%--<tr>
					            	<td align="right" width="110px">图元几何中心点： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.geometryCentroid}" class="mes_form1" name="carparkRoadPolyline.geometryCentroid"/>
						            </td>
					            </tr>
					            --%>
					            
					            <tr>
					            	<td align="right" width="110px">字体倾斜： </td>
						            <td align="left">
						            	<input value="${carparkRoadPolyline.fontItalic}" data-rule="integer;" class="mes_form1" name="carparkRoadPolyline.fontItalic"/>
						            </td>
					            </tr>
					            
					        	<tr>
						            <td align="right" width="110px">备注： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="memo" name="carparkRoadPolyline.memo" value="${carparkRoadPolyline.memo}"
						            	 	/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">空间经纬度信息： </td>
						            <td align="left">
							            <textarea  rows="7" cols="40"  name="carparkRoadPolyline.geometry"></textarea>
						            </td>
					            </tr>
					        </table>
					        
							<div class="tijiao">
					        	<input type="submit" value="保存" class="btn1" />
					        </div>
					         <div class="tijiao">
					        		  <a href="javascript:history.go(-1);" ><font color="gray">返回</font></a>
					        </div>
						</form>   	
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

 
</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".m_carparkRoadPolyline_l").attr("class","lhover");
	});
</script>
</body>
</html>