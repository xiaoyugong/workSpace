<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>维修申报受理|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
</head>
<body>
<form action="<%=path %>/maintenanceRecords_accept" method="get" id="addForm" autocomplete="off" 
	data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
	<input type="hidden" name="method" id="method" value="add"/>
	<input type="hidden" name="recordid" id="recordid" value="${recordid}"/>
    <table width="100%" border="0" class="adduser_tab">
      	<tr>
           <td align="right" width="110px">维修人员： </td>
           <td align="left">
           	<input type="text" class="mes_form1" id="repairman" name="repairman" value=""
           	 	data-rule="维修人员:required; length[~100, true]"/>
           </td>
        </tr>
        <tr>
        	<td>&nbsp;</td>
        	<td><input type="button" value="提  交" class="btn1" onclick="_submit()"/></td>
        </tr>
	</table>
</form>
<script type="text/javascript">
var _submit = function(){
	var index = parent.layer.getFrameIndex(window.name);//先得到当前iframe层的索引
	var repairman = $("#repairman").val();
	var recordid = $("#recordid").val();
	var method = $("#method").val();
	//alert(method);
	//$("addForm").submit();
	$.post("/park/maintenanceRecords_accept", { 'repairman': repairman, 'recordid': recordid,'method':method},
	function(data){
		//alert(data.status);
		if(status==true){
			//parent.layer.close(index);
			window.location.href="/park/maintenanceRecords_list";
		}
	});
	//window.location.reload();
	//alert(recordid);
	//window.location.href="/park/maintenanceRecords_accept?repairman="+repairman+"&recordid="+recordid+"&method="+method;
	parent.layer.close(index); //再执行关闭 
}
</script>
</body>
</html>