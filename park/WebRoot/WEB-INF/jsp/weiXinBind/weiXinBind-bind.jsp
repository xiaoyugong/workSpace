<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户绑定</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
		#msg{
			text-align: center;
			color: red;
		}
		
    </style>
  </head>
  <body>


   

<div class="container">

      <form class="form-signin" method="post" action="<%=path %>/weiXinBind_bind" >
        <h2 class="form-signin-heading">绑定泊泊用户</h2>
        <label for="inputEmail" class="sr-only">用户名</label>
        <input  id="inputEmail" class="form-control" placeholder="用户名" name="user.username" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="密码" name="user.password" required>
        <input type="hidden" name="user.openId" value="${user.openId}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">绑定</button>
        <div id="msg" >
			<span>${msg}</span>
		 </div>
      </form>

    </div> 

  </body>
</html>