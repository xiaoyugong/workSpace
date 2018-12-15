<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>积分管理|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/layer1.85/layer.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/pcas/PCASClass.js"></script>
	
<script>
host = "<%=path %>/";

</script>
</head>
<body>

<div class="fbbox">
				<div class="fb-bt">
					<i>认证划分</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
					<form action="<%=path %>/driverAuth_updateArea" method="post"  id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="authenticationid" value="${authenticationid}"/>
							<input type="hidden" name="method" value="save"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
					        		<td align="right" width="100px">城市： </td>
					        		<td>
					        			<select name="carpark.province" data-rule="省市区:required; "></select>
						            	<select id="area" name="area" data-rule="省市区:required;"></select>
					        		</td>
					        	</tr>
					        </table>
							<div class="tijiao">
					        	<input type="button" value="确定" onclick="closeWin()"  class="btn1"/>
					        </div>
						</form>  
					</div>
				</div>
</div>
 
<script type="text/javascript">
new PCAS("carpark.province","area","四川省","成都市");
$(".m_driverAuth_l").attr("class","lhover");
function closeWin(){
	var authenticationid = $("input[name='authenticationid']").val();
	var area = $("#area").val();
	if(area==""){
		layer.alert("划分城市不能为空");
		return;
	}
	$.post(host+'carparkAuthentication_updateArea',{'authenticationid':authenticationid,'method':'save','area':area},function(data){
		var data = JSON.parse(data);
		if(data.status){
			layer.alert("操作成功",{icon:2});
			window.setTimeout(function(){
				window.parent.document.location.href=host+"carparkAuthentication_list";
			},500); 
		}else{
			layer.msg("认证划分失败")
		}
		
	});
}
</script>
</body>
</html>