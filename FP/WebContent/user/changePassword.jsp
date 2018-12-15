<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<base href="<%=basePath%>">
		<title>修改密码</title>
		<meta charset="utf-8">
		<link rel="stylesheet" href="css/user.css">
		<script  type="text/javascript">
		// 	function submitTest(form){
		// 		if(form.newpassword.value!=form.confirmpassword.value){
		// 			alert("密码输入前后不一致！");
		// 			return false;
		// 		}
		// 		return true;
		// 	}
		</script>
	</head>
	
<body>
	<div id="wrap">
		<form id="changepassword" name="form"  action="user!updatePassword"  onsubmit="return submitTest(form);" method="post">
			<div>
				<label for="">当前密码:</label><input type="password" name="currentpassword" class="required">
			</div>
			<div>
				<label for="">新密码:</label><input type="password" name="newpassword" id="password" class="required">
			</div>
			<div>
				<label for="">确认新密码:</label><input type="password" name="confirmpassword" id="confirm" class="required">
			</div>
			<div id="btn-group">
				<button type="reset">重置</button> 
				<button type="submit">确认</button>
			</div>
			<p id="alarm"></p>
			<!-- 修改密码返回信息 -->
			<s:if test='%{info!=null||info!=""}'>
				<s:property value="%{info}"/>
			</s:if>
		</form>
	</div>
	<script type="text/javascript" src="dist/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/changepassword.js"></script> 
</body>
</html>