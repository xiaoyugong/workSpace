<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>博客订阅与退订</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/blog.css">
	<script>
	// 	function subscribe(){
	// 		var blogId = document.getElementById("blogIdAdd").value; 
	// 	//	checkInput(username,password); // 空输入验证
			
	// 		window.location.href="blog!subscribe?blogIdAdd="+blogId;
	// 	}
		
	// 	function unsubscribe(){
	// 		var blogId = document.getElementById("blogIdDel").value; 
	// 	//	checkInput(username,password); // 空输入验证
			
	// 		window.location.href="blog!unsubscribe?blogIdDel="+blogId;
	// 	}
	// 	function checkInput(){}
	// </script>
  </head>
  
  <body>
  	<div id="wrap" class="ordercancelblog">
  		<div>
	    	<form action="blog!subscribe" method="get">  		
	    		<label for="">订阅博客ID:</label>
	    		<input type="text" name="blogIdAdd" id="blogIdAdd" />
	    		<button  type="submit">订阅</button>
	    	</form>
    	</div>
    	<div>
	 		<form action="blog!unsubscribe"  method="get">
	 			<label for="">退订博客ID:</label>
	 			<input type="text" name="blogIdDel" id="blogIdDel" />
		  		<button  type="submit">退订</button>
	  		</form>
  		</div>
  		<p id="alarm"></p>
  		<!-- 显示博客订阅与退订返回信息 -->
		<s:if test='%{info!=null||info!=""}'>
			<s:property value="%{info}"/>
		</s:if>
	</div>
	<script type="text/javascript" src="dist/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/validate.js"></script>
</body>
</html>
