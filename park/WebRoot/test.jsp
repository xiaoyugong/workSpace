<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>QQ JS省市区三级联动</title>
<!-- 直接使用QQ的省市区数据 -->

<script type="text/javascript" src="page/plugins/address/geo.js"></script>
</head>
<body >
        <form>
           <select name="province" id="province"><option value="">请选择..</option></select>
          <select name="province" id="city"></select>
          <select name="district" id="district"></select>
          <input type="submit" value="提交" />
        </form>
        <script type="text/javascript" >
window.onload = function(){
var residenceAddress = new addressSelector(document.getElementById("province"),document.getElementById("city"),document.getElementById("district"));
}
</script>
</body>
</html>