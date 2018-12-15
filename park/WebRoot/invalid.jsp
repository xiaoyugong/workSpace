<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Mimic Internet Explorer 7 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<title>登录超时--系统提示</title>
<style type="text/css">
*{padding:0;margin:0;font:12px/20px Arial,Sans-serif,"宋体";}
a{color:#006ad0;text-decoration:none;}
</style>
<script type="text/javascript">
	
</script>
</head>

<body onload="offTime();">
<table width="100%" style="height: 100%;" cellpadding="0"
	cellspacing="0">
	<tr align="left" valign="middle">
		<td style="padding-top: 10px;">
			<center>
				<span style="color:red;font-weight:bold">你不能重复提交页面
				<div id='view' style="color:red;"></div>
				<div id="j"></div>
			</center>
		</td>
	</tr>
</table>

</body>
</html>

