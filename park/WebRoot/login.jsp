<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>泊泊停车|成都市微泊科技有限公司</title>
<link href="<%=path %>/page/login/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
<style type="text/css">
body{
	background-color: #fff
}
</style>
<script type="text/javascript">

if(window.parent!=window){
	window.parent.location.reload();
	
}
$(function() {
	$("#captcha").bind("click", function() {
		var timenow = new Date().getTime();
		$("#captcha").attr("src","<%=path %>/captcha?w=65&h=22&f=17&d=" + timenow);
	});
	$("#yzm").bind("click", function() {
		var timenow = new Date().getTime();
		$("#captcha").attr("src","<%=path %>/captcha?w=65&h=22&f=17&d=" + timenow);
		return false;
	});
});


</script>
</head>

<body>
<form action="j_spring_security_check" method="post">
<div class="log_wrap">
<div class="log_bg">
<div class="log">
	<div class="log_t">泊泊停车后台管理</div>
    <div class="log_tab">
    	
        <table width="100%" border="0">
         <tr>
        	 <td colspan="2" height="32px"><span style="color:red">${session.SPRING_SECURITY_LAST_EXCEPTION.message }</span></td>
        	</tr>
         <tr>
            <td colspan="2"><span>用户名：</span><input type="text" class="log_form1" name="username" id="username"/></td>
          </tr>
          <tr>
            <td colspan="2"><span>密&nbsp;&nbsp;码：</span><input type="password" class="log_form1" name="password" /> </td>
          </tr>
          <tr>
            <td colspan="2"><span>验证码：</span><input type="text" style="width: 63px;" class="log_form1" name="validateCode" id="validateCode"/>
            	<img src="<%=path %>/captcha?w=65&h=22&f=17" style="vertical-align: middle;margin-left: 1px;margin-right: 3px;border: 1px solid #A6CAFF;cursor: pointer;" 
            	title="看不清?换一张" alt="看不清?，换一张" id="captcha"/><a href="javascript:void(0);" id="yzm">看不清?</a></td>
          </tr>
          <tr>
            <td height="38px">&nbsp;&nbsp;</td>
            <td><input type="submit" class="log_form3" value="登 陆" /></td>
          </tr>
        </table>
    </div>
</div>
</div>
</div>
</form>
</body>
</html>