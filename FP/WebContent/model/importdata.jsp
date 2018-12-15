<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<base href="<%=basePath%>">
		<title>导入数据</title>
		<meta charset="utf-8">
		<link rel="stylesheet" href="css/model.css">
	</head>
<body>
	<div id="wrap">
		<form id="importdata" action="modelAction!upload" enctype="multipart/form-data">
		<input type="file">
		<div>
			<label>服务器文件路径:</label><input type="text" name="ftpPath"  />	
			<select name="uploadDeltaOrAll" id="upId">
			    <option value="delta">增量</option>
				<option value="all">全量</option>
			</select>			
		</div>

		<div id="btn-group">
		
			<button type="reset">重置</button>
			<button type="submit">导入</button>
		</div>

		
		<!-- 显示导入数据返回信息 -->
			<s:if test='%{info!=null||info!=""}'>
				<s:property value="%{info}"/>
			 </s:if>
		
			
		</form>
	</div>
	<script type="text/javascript" src="dist/js/jquery-1.8.3.min.js"></script>
	<!--<script type="text/javascript" src="js/importdata.js"></script>-->
</body>
</html>