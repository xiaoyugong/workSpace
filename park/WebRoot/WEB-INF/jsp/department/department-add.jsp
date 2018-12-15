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
	<title>部门|泊泊停车</title>
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
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">系统管理</a> » <a href="###">部门</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>部门添加</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="department_add" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="add"/>
							<input type="hidden" name="department.isleaf" value="1"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">部门名称： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="name" name="department.name" 
						            	 	data-rule="部门名称:required; length[~100, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px" valign="top">描述： </td>
						            <td align="left">
										<textarea name="department.description" data-rule="length[~200]" style="width: 270px; height: 100px" id="description"></textarea>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">添加时间： </td>
						            <td align="left">
						            	<input type="text" name="department.createTimeToDate" value="<%=today %>" id="createTimeToDate"
											onclick="$.calendar({format:'yyyy-MM-dd HH:mm:ss' });" readonly="readonly" data-rule="添加时间:required; "
											style="background:url(page/plugins/lhgcalendar/iconDate.gif) center right no-repeat #f7f7f7;cursor:pointer;width:180px;padding:2px 2px 2px 2px;height:18px;line-height:18px;vertical-align:bottom;border: 1px solid #CCCCCC;width: 200px;height: 28px;line-height: 28px;"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">序列： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="orderid" name="department.orderid" value="999"
						            	 	data-rule="序列:required; integer; range[0~]"/>
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
$(document).ready(function(){
	$(".m_department_l").attr("class","lhover");
})
</script>
</body>
</html>