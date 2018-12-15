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
	<title>绑定车位|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script src="<%=path %>/page/plugins/layer/layer.js"></script>
	<style type="text/css">
		select{height: 28px; line-height: 28px}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		var err='${err}';
		if(err != '')
		{
			if(err == '-1'){
				err="绑定车位失败,车位不存在";
				layer.msg(err, function(){});
			}
		}
	});
	var the_host = "<%=path%>/";
	//加载扩展模块
	layer.config({
	    extend: the_host + 'page/plugins/layer/extend/layer.ext.js'
	});
	var _submit = function(){
		$('#addForm').submit();
	}
	</script>
</head>
<body>
			<div class="fbbox">
				<div class="fb-bt">
					<i>绑定车位</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
					<form action="groundlock_bindCarpark" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="mac" value="${mac}" id="mac"/>
							<input type="hidden" name="method" value="edit" />
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">停车场名称： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="carparkName" name="carparkName" value="${carparkName}" data-rule="停车场名称:required;"/>
						            </td>
						            
					            </tr>
					            <tr>
						            <td align="right" width="110px">车位号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="berthName" name="berthName" value="${berthName}" data-rule="车位号:required;"/>
						            </td>
						            
					            </tr>
					        </table>
							<div class="tijiao">
					        	<input type="button" value="提  交" class="btn1" onclick="_submit()"/>
					        </div>
					        </form>
					</div>
				</div>
			</div>
</body>
</html>