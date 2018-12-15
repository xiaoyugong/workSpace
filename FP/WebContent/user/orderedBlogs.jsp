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
    
    <title>订阅博客</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <!-- the css part of the list  -->
  <style>
    #wrap{font-family: '微软雅黑'; padding: 15px 30px;}
    h3{font-weight: normal;padding: 0px; margin: 0px;}
    table{
      margin-top: 15px;
      border: 1px solid #ccc;
      border-collapse:collapse;
      font-size: 14px;
      width: 500px;
      color: #444;
    }
    #blogId{width:100px;}
    table td{padding:3px;}
    h3{font-size: 16px; margin: 0px; padding: 0px; }
  </style>

  <body>
    <div id="wrap">
  	<s:if test='%{info=="null"}'>
  		<h3>尊敬的用户您好，您还没有订阅相关博客！</h3>
  	</s:if>
  	<s:else>
  	
  	<h3>尊敬的用户您好，您订阅的博客信息如下：</h3>
  	<table border="1">
		<thead>
			<tr>
				<td id="blogId">博客ID</td>
				<td id="blogName">博客名</td>
				<td id="email">邮箱</td>
			</tr>
		</thead>
        <s:iterator value="orderedBlogs" id="map">
					<tr>
                         <td><s:property value="#map.key"/></td>
                         <s:iterator value="#map.value" id="s" >
                         <td><s:property value="s" /></td>
                          </s:iterator>
                    </tr>
        </s:iterator>  
       
    </table>
     </s:else>
     </div>
  </body>
</html>
