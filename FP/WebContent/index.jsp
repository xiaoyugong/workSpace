<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">

<title>登录</title>
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
			<h1>博客推荐系统</h1>
			<form action="user!login" method="post">
				<div>
					<label for="">用户名:</label><input type="text" name="username"
						id="username" />
				</div>
				<div>
					<label for="">密码:</label><input type="password" name="password"
						id="password" />
				</div>
				<button type="submit">登录</button>
				<a href="register.jsp"><button type="button">注册</button></a>
			</form>
			<!-- 登录失败返回信息 -->
			<s:if test='%{info!=null||info!=""}'>
				<s:property value="%{info}" />
			</s:if>
			<div id="message">
				<span id="alarm"></span>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/validate.js"></script>
</body>
</html>
