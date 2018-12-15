<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<base href="<%=basePath%>">

		<title>首页</title>
		<meta charset="utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!-- the part of css -->
		<link rel="stylesheet" href="dist/css/bootstrap.min.css">
		<link rel="stylesheet" href="css/home.css">
	</head>

	<body>
		<div id="wrap">
			<!-- the header -->
			<div id="hd">
				<span id="logo">Blog</span>
				<ul id="sidenav">
					<li>
						<span class="listname"><span class="glyphicon glyphicon-user"></span>用户信息<span class="triggle"></span></span>
						<ul>
							<li><a href="user/changePassword.jsp">修改密码</a></li>
							<li><a href="user!subscribe">订阅查看</a></li>
						</ul>
					</li>
					<li>
						<span class="listname"><span class="glyphicon glyphicon-book"></span>建立知识库<span class="triggle"></span></span>
						<ul>
							<li><a href="model/importdata.jsp">导入数据</a></li>
							<li><a href="model/runFP.jsp">运行FP云算法</a></li>
						</ul>
					</li>
					<li>
						<span class="listname"><span class="glyphicon glyphicon-bold"></span>博客管理<span class="triggle"></span></span>
						<ul>
							<li><a href="blog/orderCancelBlog.jsp">博客订购与退订</a></li>
							<li><a href="blog/blogRecommend.jsp">博客推荐</a></li>
						</ul>
					</li>
				</ul>
			</div>

			<!-- the body -->
			<div id="bd">
				<div id="infonav">
					<h2 id="title"><a href="home.jsp">博客推荐系统</a></h2>
					<!-- <span id="login" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-arrow-right"></span><span>登录</span></span> -->
				</div>

				<!-- content -->
				<div id="maincontent">
					<p id="crumbs"><span id="firstcrumbs">欢迎界面</span><span>></span><span id="seccrumbs"></span></p>
					<iframe src="welcom.jsp" frameborder="0"></iframe>
				</div>
			</div>
		</div>

		<!-- the modal -->
		<!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <form class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		        <h4 class="modal-title" id="myModalLabel">登录系统</h4>
		      </div>
		      <div class="modal-body">
		        <div><label for="">用户名：</label><input type="text" class="required"></div>
		        <div><label for="">密码：</label><input type="password" class="required"></div>
		      </div>
		      <div class="modal-footer">
		      	<span id="alarm"></span>
		        <input type="button" class="btn btn-default" value="重置">
		        <button type="submit" class="btn">登录</button>
		      </div>
		    </form>/.modal-content
		    <h5>用户名：admin</h5>
		  </div>
		</div> -->

	<!-- the part of script -->
	<script type="text/javascript" src="dist/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/home.js"></script>
	</body>
</html>
