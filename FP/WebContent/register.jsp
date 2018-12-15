<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'register.jsp' starting page</title>
    <meta charset="utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/index.css">

  </head>
  
  <body>
  	<div id="wrap">
  		<div id="center">
			<h1 >博客推荐系统注册界面</h1>
			<form action="user!register" method="post" >
	  			<div><label for="">用户名:</label><input type="text" name="username" /></div>
	 			 <div><label for="">密码:</label><input type="password" name="password" /></div>
			<button type="submit">注册</button>
			<button type="reset">重置</button>
			</form>

			<div id="message"><span id="alarm">
			<!-- 显示注册返回信息 -->
			<s:if test='%{info!=null||info!=""}'>
					<s:property value="%{info}"/>
					 <s:if test='%{info=="注册成功"}'>
					 	<a href="index.jsp">登录</a>
					 </s:if>
			</s:if>
			</span></div> 			
  		</div>
  	</div>
	

	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/validate.js"></script>
  </body>
</html>
