<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
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
	<title>授权蓝牙|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script src="<%=path %>/page/plugins/layer/layer.js"></script>
	<style type="text/css">
		select{height: 28px; line-height: 28px}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		var err='${err}';
		if(err != '')
		{
			layer.msg(err, {icon: 5});
		}
	});
	var _submit = function(){
		$('#addForm').submit();
	}
	</script>
</head>
<body>
			<div class="fbbox">
				<div class="fb-bt">
					<i>授权蓝牙添加</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="accessBluetooth_add" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="add"/>
							<input type="hidden" name="accessBluetooth.groundlock.groundlockid" value="${groundlockmac}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">蓝牙MAC： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="bluetoothmacStr" name="accessBluetooth.bluetoothmacStr" 
						            	 	data-rule="蓝牙MAC:required;mac" data-rule-mac="[/^[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]$/, 'MAC地址格式错误']"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">类型： </td>
						            <td align="left">
						            	<s:radio list="#{'0':'车载','1':'非车载'}" name="accessBluetooth.bluetoothtype" data-rule="checked"
											listKey="key" listValue="value" theme="simple"  value="0"></s:radio>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">是否可用： </td>
						            <td align="left">
						            	<s:radio list="#{'0':'否','1':'是'}" name="accessBluetooth.bluetoothEnable" data-rule="checked"
											listKey="key" listValue="value" theme="simple" value="1"></s:radio>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">车牌号： </td>
						            <td align="left">
										<input type="text" class="mes_form1" id="carNumber" name="accessBluetooth.carNumber"
						            	 	data-rule="车牌号:required; length[~100, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">手机号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="telephone" name="accessBluetooth.telephone" 
						            	 	data-rule="手机号:required; length[~100, true]"/>
						            </td>
					            </tr>
					        	
					        </table>
							<div class="tijiao">
					        	<input type="button" value="提  交" class="btn1" onclick="_submit()"/>
					        </div>
						</form>   	
					</div>
				</div>
			</div>
</body>
</html>