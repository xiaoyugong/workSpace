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
	<title>APP|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">APP</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>APP添加</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="appVersion_add" method="post" id="addForm" autocomplete="off" enctype="multipart/form-data"
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="add"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">版本号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="versioncode" name="versioncode" 
						            	 	data-rule="版本号:required; integer; range[0~]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">渠道： </td>
						            <td align="left">
						      			<s:select list="appChannels" name="channelid" listKey="channelid" listValue="name"
						      				headerKey="" headerValue="--请选择--" theme="simple" id="channelid"
						      				data-rule="渠道:required; "></s:select>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">强制更新： </td>
						            <td align="left">
						            	<s:radio list="#{'N':'否','Y':'是'}" name="needUpdate" data-rule="checked" id="needUpdate"
						 					listKey="key" listValue="value" theme="simple" value="Y" ></s:radio>
						            </td>
					            </tr>

								<tr>
						            <td align="right" width="110px">APP类型： </td>
						            <td align="left">
						            	<s:radio list="#{'0':'车主APP','1':'物业APP'}" name="category" data-rule="checked" id="category"
						 					listKey="key" listValue="value" theme="simple" value="0" ></s:radio>
						            </td>
					            </tr>

					        	<tr>
						            <td align="right" width="110px">APP： </td>
						            <td align="left">
						            	 <input type="file" name="upload" data-rule="APP:required;" id="file" class="mes_form1"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px" valign="top">更新说明： </td>
						            <td align="left">
										<textarea name="content" style="width: 270px; height: 100px" id="content"></textarea>
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
	$(".m_appVersion_l").attr("class","lhover");
})
</script>
</body>
</html>