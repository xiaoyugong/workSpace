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
	<title>停车场导航线路|泊泊停车</title>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场导航线路</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>停车场导航线路${carparkNavigationPolyline==null?'添加':'修改'}</i>
				</div>
					<s:set value="#{0:'人车',1:'人',2:'车'}" var="roadType"></s:set>
					<s:set value="#{'':'--请选择--',0:'单行道',1:'双行道'}" var="direction"></s:set>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/carparkNavigationPolyline_update" method="post" enctype="multipart/form-data"  id="saveForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="carparkNavigationPolyline.gid" value="${carparkNavigationPolyline.gid}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        <tr>
						            <td align="right" width="110px">停车场： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" readonly="readonly" name="carparkNavigationPolyline.carpark.name" value="${carparkNavigationPolyline.carpark.name}"
						            	 	/>
						            	 	<input type="hidden" value="${carparkNavigationPolyline.carpark.carparkid}"  id="_carparkid" name="carparkNavigationPolyline.carpark.carparkid"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">名称： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" id="name" name="carparkNavigationPolyline.name" value="${carparkNavigationPolyline.name}"
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
							            	<s:select list="floors" listValue="name" name="carparkNavigationPolyline.floorid" listKey="floorid" theme="simple"></s:select>
						            	</s:else>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">导航类型： </td>
						            <td align="left">
						            	<s:select list="#roadType" listKey="key" listValue="value" theme="simple" name="carparkNavigationPolyline.roadType"></s:select>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">单双行： </td>
						            <td align="left">
						            	<s:select list="#direction" listKey="key" listValue="value"  theme="simple" name="carparkNavigationPolyline.direction"></s:select>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">背景色： </td>
						            <td align="left">
						            	<input value="${carparkNavigationPolyline.color}" class="mes_form1" data-rule="required,integer;" name="carparkNavigationPolyline.color"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">边框色： </td>
						            <td align="left">
						            	<input value="${carparkNavigationPolyline.bordercolor}" data-rule="integer;" class="mes_form1" name="carparkNavigationPolyline.bordercolor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体颜色： </td>
						            <td align="left">
						            	<input value="${carparkNavigationPolyline.fontColor}" data-rule="integer;" class="mes_form1" name="carparkNavigationPolyline.fontColor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体大小： </td>
						            <td align="left">
						            	<input value="${carparkNavigationPolyline.fontSize}"   class="mes_form1" name="carparkNavigationPolyline.fontSize"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体加粗： </td>
						            <td align="left">
						            	<input value="${carparkNavigationPolyline.fontWeight}"data-rule="integer;"  class="mes_form1" name="carparkNavigationPolyline.fontWeight"/>
						            </td>
					            </tr>
					            <%--<tr>
					            	<td align="right" width="110px">图元几何中心点： </td>
						            <td align="left">
						            	<input value="${carparkNavigationPolyline.geometryCentroid}" class="mes_form1" name="carparkNavigationPolyline.geometryCentroid"/>
						            </td>
					            </tr>
					            --%>
					            
					            <tr>
					            	<td align="right" width="110px">字体倾斜： </td>
						            <td align="left">
						            	<input value="${carparkNavigationPolyline.fontItalic}" data-rule="integer;" class="mes_form1" name="carparkNavigationPolyline.fontItalic"/>
						            </td>
					            </tr>
					            
					        	<tr>
						            <td align="right" width="110px">备注： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="memo" name="carparkNavigationPolyline.memo" value="${carparkNavigationPolyline.memo}"
						            	 	/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">空间经纬度信息： </td>
						            <td align="left">
							            <textarea  rows="7" cols="40"  name="carparkNavigationPolyline.geometry"></textarea>
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
	$(".m_carparkNavigationPolyline_l").attr("class","lhover");
	});
</script>
</body>
</html>