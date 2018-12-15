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
	<title>地锁添加|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script src="<%=path %>/page/plugins/layer/layer.js"></script>
	<style type="text/css">
	.fieldset {
		border: 1px solid #CCCCCC;
	}
	.fieldset legend {
		color: #7F9DB9;
		padding: 3px 8px;
		font-weight: bold;
		border: 0px;
	}
	</style>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">地锁管理</a> » <a href="###">地锁添加</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>地锁添加</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="groundlock_add" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="add"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">地锁MAC： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="groundlockMac" name="groundlock.groundlockMac" data-rule="MAC:required;mac" data-rule-mac="[/^[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]$/, 'MAC地址格式错误']"/>
						            </td>
						            <td align="right" width="110px">SIM卡号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="simNum" name="groundlock.simNum"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">固件版本号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.firmwareVersion" />
						            </td>
						            <td align="right" width="110px">软件版本号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.softVersion" />
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">厂家ID： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.constructId" />
						            </td>
						            <td align="right" width="110px">出厂时间： </td>
						            <td align="left">
						           		<input type="text" name="groundlock.constructTimeToDate" value="2015-07-28" id="constructTimeToDate"
											onclick="$.calendar({format:'yyyy-MM-dd' });" readonly="readonly" data-rule="出厂时间:required; "
											style="background:url(page/plugins/lhgcalendar/iconDate.gif) center right no-repeat #f7f7f7;
											cursor:pointer;width:180px;padding:2px 2px 2px 2px;height:18px;line-height:18px;
											vertical-align:bottom;border: 1px solid #CCCCCC;width: 200px;height: 28px;line-height: 28px;"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">电池型号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.batteryModel" />
						            </td>
						            <td align="right" width="110px">保护电量： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="protectionPower" name="groundlock.protectionPower" value="20"  data-rule="保护电量:required; integer; range[1~99]"/>
						            </td>
					            </tr>
					              <tr>
						            <td align="right" width="110px">地锁类型： </td>
						            <td align="left">
						            	<s:select list="#{'1':'纯联网','2':'纯蓝牙','3':'联网蓝牙'}" listKey="key" listValue="value" data-rule="蓝牙类型:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="groundlock.locked_type"></s:select>
						            </td>
						            <td align="right" width="110px">地锁品牌： </td>
						            <td align="left">
						            	<s:select list="#{'1':'泊泊-BC','2':'泊享-BX'}" listKey="key" listValue="value" data-rule="蓝牙品牌:required; "
						           		headerKey="" headerValue="--请选择--" theme="simple" name="groundlock.brand"></s:select>
						            </td>
					            </tr>
					             <tr>
						            <td align="right" width="110px">蓝牙口令： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="bluetoothPassword" name="groundlock.bluetoothPassword" value="" />
						            </td>
						            <td align="right" width="110px">地锁编码： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="groundlockNumber" name="groundlock.groundlockNumber" value="" />
						            </td>
					            </tr>
					              <tr>
						            <td align="right" width="110px">停车场名称： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="carparkName" name="carparkName" value="" />
						            </td>
						            <td align="right" width="110px">车位号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="berthName" name="berthName" value="" />
						            	<input type="hidden" id="err value="${err}" />
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
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
		var err='${err}';
		if(err != '')
		{
			if(err == '0'){
				err="绑定车位成功";
				layer.msg(err, {icon: 6});
			}else{
				err="绑定车位失败,车位不存在";
				layer.msg(err, function(){});
			}
		}
});
var the_host = "<%=path%>/";
$(function() {
	$(".m_groundlock_l").attr("class","lhover");
});
var _submit = function(){
	$('#addForm').submit();
}
/**
 * 升锁
 * @param {Object} groundlockid
 */
function upLock(groundlockid)
{
	 layer.confirm('确定要升锁吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_up" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('升锁成功', {icon: 1});
					setTimeout(function(){
					    location.reload() ;
					}, 500);
				}
				else
				{
					if(data.errorcode == 45){
						layer.msg('升锁失败', function(){});
					}
					else if(data.errorcode == 100){
						layer.msg('升锁失败，请求过期', function(){});
					}
					else if(data.errorcode == 44){
						layer.msg('升锁失败，地锁休眠真正唤醒...', function(){});
					}
					else{
						layer.msg('升锁失败,未知错误', function(){});
					}
				}
			},
			error: function (XMLResponse) {
				layer.close(index);
			}
		});
	});
}
 /**
  * 降锁
  * @param {Object} groundlockid
  */
function downLock(groundlockid)
{
	layer.confirm('确定要降锁吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_down" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('降锁成功', {icon: 1});
					setTimeout(function(){
					    location.reload() ;
					}, 500);
				}
				else
				{
					if(data.errorcode == 45){
						layer.msg('降锁失败', function(){});
					}
					else if(data.errorcode == 100){
						layer.msg('降锁失败，请求过期', function(){});
					}
					else if(data.errorcode == 44){
						layer.msg('降锁失败，地锁休眠真正唤醒...', function(){});
					}
					else{
						layer.msg('降锁失败,未知错误', function(){});
					}
				}
			},
			error: function (XMLResponse) {
				layer.close(index);
			}
		});
	});
}
  /**
 * 获取剩余电量
 */
function sendGetEnergy(groundlockid)
{
	layer.confirm('确定要获取剩余电量吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_sendGetEnergy" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('电量获取成功', {icon: 1});
					setTimeout(function(){
					   location.reload() ;
					}, 500);
				}
				else
				{
					layer.msg('电量获取失败', function(){});
				}
			},
			error: function (XMLResponse) {
				layer.close(index);
			}
		});
	});
}
  
 /**
  * 获取SIM卡信号强度
  */
function sendGetSignal(groundlockid)
{
	layer.confirm('确定要获取SIM卡信号强度吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_sendGetSignal" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('信号获取成功', {icon: 1});
					setTimeout(function(){
					   location.reload() ;
					}, 500);
				}
				else
				{
					layer.msg('信号获取失败', function(){});
				}
			},
			error: function (XMLResponse) {
				layer.close(index);
			}
		});
	});
}
/**
 * 修改地锁保护电量
 * @param {Object} groundlockid
 */
function changeProtectEnergy(groundlockid)
{
	 var reg = /^[0-9]*$/;
	 var protectEnergy = $("#protectionPower").val();
	 layer.confirm('确定要修改地锁保护电量吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		if(reg.test(protectEnergy) && protectEnergy >=1 && protectEnergy <=99)
		{
			$.ajax({
				type: 'POST',
				url: the_host + "groundlockControl_changeProtectEnergy" ,
				data: {
					'groundlockid': encodeURI(groundlockid),
					'protectEnergy': encodeURI(protectEnergy),
				},
				dataType: 'json',
				success: function (data) {
					layer.close(index);
					if(data.status == "true")
					{
						layer.msg('地锁保护电量更新成功', {icon: 1});
					}
					else
					{
						layer.msg('地锁保护电量更新失败', function(){});
					}
				},
				error: function (XMLResponse) {
					layer.close(index);
				}
			});
		}
		else
		{
			layer.msg('地锁保护电量格式错误', function(){});
		}
	});
}
/**
 * 修改地锁蓝牙口令
 * @param {Object} groundlockid
 */
function changeBluetoothPassword(groundlockid)
{
	 var bluetoothPassword = $("#bluetoothPassword").val();
	 layer.confirm('确定要修改地锁蓝牙口令吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		if(bluetoothPassword.length==6)
		{
			$.ajax({
				type: 'POST',
				url: the_host + "groundlockControl_changeBluetoothPassword" ,
				data: {
					'groundlockid': encodeURI(groundlockid),
					'bluetoothPassword': encodeURI(bluetoothPassword),
				},
				dataType: 'json',
				success: function (data) {
					layer.close(index);
					if(data.status == "true")
					{
						layer.msg('地锁蓝牙口令更新成功', {icon: 1});
					}
					else
					{
						layer.msg('地锁蓝牙口令更新失败', function(){});
					}
				},
				error: function (XMLResponse) {
					layer.close(index);
				}
			});
		}
		else
		{
			layer.msg('地锁蓝牙口令格式错误', function(){});
		}
	});
}
 
</script>
</body>
</html>