<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'welcom.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <style>
  #wrap{
    padding-left: 30px;
    padding-top: 10px;
  }
  #list{
    width: 900px;
    font-family: '微软雅黑';
    color: #333;
    text-indent: 2em
  }
  </style>
  <body>
  <div id="wrap">
   <p id="list">
   	博客推荐系统是推荐用户可能感兴趣的博客的系统。用户注册登录后，
   	可以首先使用Top推荐方式来获得系统推荐给用户的博客列表信息。
   	用户可以根据自己的喜好或者Top推荐方式推荐的博客列表来进行博客订阅。
   	订阅博客后，用户就会有订阅博客信息，
   	系统就可以根据这个信息来对用户使用知识库进行联合推荐。
   	利用知识库进行联合推荐的方式能更加针对用户的喜好，推荐精确度也更加高。
   </p>
   </div>
  </body>
</html>
