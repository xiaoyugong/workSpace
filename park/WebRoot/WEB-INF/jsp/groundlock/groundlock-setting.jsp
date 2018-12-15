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
	<title>地锁|泊泊停车</title>
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
	<script type="text/javascript" src="<%=path %>/page/plugins/layer/layer.js"></script>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">地锁管理</a> » <a href="###">地锁配置</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>地锁配置</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="groundlock_setting" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="edit"/>
							<input type="hidden" name="groundlock.groundlockid" value="${groundlock.groundlockid}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">地锁MAC： </td>
						            <td align="left">
						            	${groundlock.groundlockMac}  <a href="javascript:void(0);" onclick="upLock('${groundlock.groundlockid}')">升锁</a> | 
			            			<a href="javascript:void(0);" onclick="downLock('${groundlock.groundlockid}')">降锁</a> 
						            </td>
						            <td align="right" width="110px">SIM卡号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="simNum" name="groundlock.simNum" value="${groundlock.simNum}" data-rule="SIM卡号:mobile;"/>
						            	<a href="javascript:void(0);" onclick="changeGroundPhoneNumber('${groundlock.groundlockid}')">更新</a>
						            </td>
					            </tr>
					            <tr>
					             	<td align="right" width="110px">停车场： </td>
						            <td align="left">
						            	${groundlock.carparkname}
						            </td>
						            <td align="right" width="110px">车位号： </td>
						            <td align="left">
						            	${groundlock.berthname}
						            </td>
					            </tr>
					              <tr>
						            <td align="right" width="110px">地锁类型： </td>
						            <td align="left">
						            	<select name="groundlock.locked_type">
										  <option value="1" <c:if test="${groundlock.locked_type == 1}">selected="selected"</c:if>>纯联网</option>
										  <option value="2" <c:if test="${groundlock.locked_type == 2}">selected="selected"</c:if>>纯蓝牙</option>
										  <option value="3" <c:if test="${groundlock.locked_type == 3}">selected="selected"</c:if>>联网蓝牙</option>
										</select>
						            </td>
						            <td align="right" width="110px">地锁品牌： </td>
						            <td align="left">
						            	<select name="groundlock.brand">
										  <option value="1" <c:if test="${groundlock.brand == 1}">selected="selected"</c:if>>泊泊-BC</option>
										  <option value="2" <c:if test="${groundlock.brand == 2}">selected="selected"</c:if>>泊泊-BX</option>
										</select>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">SIM信号强度： </td>
						            <td align="left">
						            	${groundlock.simIntensity} dBm   <a href="javascript:void(0);" onclick="sendGetSignal('${groundlock.groundlockid}')">刷新</a>
						            </td>
						            <td align="right" width="110px">电池电量： </td>
						            <td align="left">
						            	${groundlock.batteryPower}% <a href="javascript:void(0);" onclick="sendGetEnergy('${groundlock.groundlockid}')">刷新</a>
						            </td>
					            </tr>
					            
					            <tr>
						            <td align="right" width="110px">固件版本号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.firmwareVersion" value="${groundlock.firmwareVersion}"/>
						            </td>
						            <td align="right" width="110px">软件版本号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.softVersion" value="${groundlock.softVersion}"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">厂家ID： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.constructId" value="${groundlock.constructId}"/>
						            </td>
						            <td align="right" width="110px">出厂时间： </td>
						            <td align="left">
						           		<input type="text" name="groundlock.constructTimeToDate" value="<s:date name="groundlock.constructTimeToDate" format="yyyy-MM-dd" />" id="constructTimeToDate"
											onclick="$.calendar({format:'yyyy-MM-dd' });" readonly="readonly" data-rule="出厂时间:required; "
											style="background:url(page/plugins/lhgcalendar/iconDate.gif) center right no-repeat #f7f7f7;
											cursor:pointer;width:180px;padding:2px 2px 2px 2px;height:18px;line-height:18px;
											vertical-align:bottom;border: 1px solid #CCCCCC;width: 200px;height: 28px;line-height: 28px;"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">电池型号： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.batteryModel" value="${groundlock.batteryModel}"/>
						            </td>
						            <td align="right" width="110px">保护电量： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="protectionPower" name="groundlock.protectionPower" value="${groundlock.protectionPower}"  ="保护电量:required; integer; range[1~99]"/>
						            	<a href="javascript:void(0);" onclick="changeProtectEnergy('${groundlock.groundlockid}')">更新</a>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">地锁编码： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" name="groundlock.groundlockNumber" value="${groundlock.groundlockNumber}"/>
						            </td>
						            <td align="right" width="110px">蓝牙口令： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="bluetoothPassword" name="groundlock.bluetoothPassword" value="${groundlock.bluetoothPassword}"/> 
						            	<a href="javascript:void(0);" onclick="changeBluetoothPassword('${groundlock.groundlockid}')">更新</a>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">自动唤醒分钟数： </td>
						            <td align="left" colspan="3">
						            	<input type="text" class="mes_form1" id="autoWeakupTime" name="groundlock.autoWeakupTime" value="${groundlock.autoWeakupTime}" data-rule="自动唤醒分钟数:required; integer; "/>
						            	<a href="javascript:void(0);" onclick="changeAutoWeakupTime('${groundlock.groundlockid}')">更新</a>
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
					layer.msg('升锁成功', {icon: 6});
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
					layer.msg('降锁成功', {icon: 6});
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
					layer.msg('电量获取成功', {icon: 6});
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
 * 
 * 修改地锁SIM卡号
 */
function changeGroundPhoneNumber(groundlockid)
{
	var reg = /^[0-9]*$/;
	 var simNum = $("#simNum").val();
	 layer.confirm('确定要修改地锁SIM卡卡号吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_changeGroundPhoneNumber" ,
			data: {
				'groundlockid': encodeURI(groundlockid),
				'telephone': encodeURI(simNum)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('地锁SIM卡卡号更新成功', {icon: 6});
				}
				else
				{
					layer.msg('地锁SIM卡卡号更新失败', function(){});
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
					layer.msg('信号获取成功', {icon: 6});
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
 * 修改地锁自动唤醒时间
 * 
 */
function changeAutoWeakupTime(groundlockid)
{
	 var reg = /^[0-9]*$/;
	 var autoWeakupTime = $("#autoWeakupTime").val();
	 layer.confirm('确定要修改自动唤醒分钟数吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		if(reg.test(autoWeakupTime) && autoWeakupTime >=0)
		{
			$.ajax({
				type: 'POST',
				url: the_host + "groundlockControl_changeAutoWeakupTime" ,
				data: {
					'groundlockid': encodeURI(groundlockid),
					'autoWeakupTime': encodeURI(autoWeakupTime)
				},
				dataType: 'json',
				success: function (data) {
					layer.close(index);
					if(data.status == "true")
					{
						layer.msg('地锁自动唤醒分钟数更新成功', {icon: 6});
					}
					else
					{
						layer.msg('地锁自动唤醒分钟数更新失败', function(){});
					}
				},
				error: function (XMLResponse) {
					layer.close(index);
				}
			});
		}
		else
		{
			layer.msg('地锁自动唤醒分钟数格式错误', function(){});
		}
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
					'protectEnergy': encodeURI(protectEnergy)
				},
				dataType: 'json',
				success: function (data) {
					layer.close(index);
					if(data.status == "true")
					{
						layer.msg('地锁保护电量更新成功', {icon: 6});
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
					'bluetoothPassword': encodeURI(bluetoothPassword)
				},
				dataType: 'json',
				success: function (data) {
					layer.close(index);
					if(data.status == "true")
					{
						layer.msg('地锁蓝牙口令更新成功', {icon: 6});
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