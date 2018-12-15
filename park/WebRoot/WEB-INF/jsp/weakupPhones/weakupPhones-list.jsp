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
	<title>授权唤醒电话|泊泊停车</title>
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
			if(err == '0'){
				err="修改授权电话号码失败";
				layer.msg(err, function(){});
			}else{
				err="修改授权电话号码成功";
				layer.msg(err, {icon: 6});
			}
			
		}
	});
	var _submit = function(){
		$('#addForm').submit();
	}
	</script>
</head>
<body>
			<div class="fbbox">
				<div class="fb-bt">
					<i>授权唤醒电话</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="weakupPhones_add" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="groundlockid" value="${groundlockid}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">授权电话1： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="telephoneOne" name="telephoneOne" value="${telephoneOne}"
						            	 	/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">授权电话2： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="telephoneTwo" name="telephoneTwo" value="${telephoneTwo}"
						            	 	/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">授权电话3： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="telephoneThree" name="telephoneThree" value="${telephoneThree}"
						            	 	/>
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