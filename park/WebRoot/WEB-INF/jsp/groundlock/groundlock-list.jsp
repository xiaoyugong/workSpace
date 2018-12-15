<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>地锁|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script src="<%=path %>/page/plugins/layer/layer.js"></script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">地锁管理</a> » <a href="###">地锁</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/groundlock_list" method="post" id="searchForm" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
				    	<table>
				    		<tr >
				    			<td>
					    			<div class="l-b-b">地锁MAC：
							        	<input type="text" value="${mac}" name="mac"  />
							        </div>
				    			</td>
				    			<td>
				    				<div class="l-b-b">停车场：
							        	<input type="text" value="${carparkName}" name="carparkName"  />
							        </div>
				    			</td>
				    			<td>
				    				<div class="l-b-b">车位号：
							        	<input type="text" value="${berthName}" name="berthName"  />
							        </div>
				    			</td>
				    			<td>	
				    				<div class="l-b-b">SIM卡：
							        	<input type="text" value="${telephone}" name="telephone"  />
							        </div>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td height="60px">
				    				<div class="l-b-b">状态：</div>
							    	<div class="l-b-b">
							    		<ul>
										  <li class="select_box" style="width:100px;">
										    <span>请选择...</span>
											<input type="hidden" name="onlineStatus" value="${onlineStatus}"/>
										    <ul class="son_ul" style="width:130px;">
										    	<li rel="">请选择...</li>
										    	<li rel="0">在线</li>
										    	<li rel="1">掉线</li>
										    	<li rel="2">休眠</li>
										    </ul>
										  </li>
										</ul>
							         </div>	
				    			</td>
				    			<td colspan="3">
				    				<div class="l-b-n" id="xrig">	
							         	<input type="hidden" value="${page}" name="page" id="page"/>
										<input type="submit" value="搜 索"  class="btn1"/>
							         </div>
				    			</td>
				    		</tr>
				    	</table>
				    </form>     
				</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<th width="88" class=""><a href="<%=path %>/groundlock_add">添加</a></th>
								<th class="tabx"><i></i></th>
								<th width="108" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
								<th class="tabx "><i></i></th>
								<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">网络状态</td>
			            		<td class="">地锁状态</td>
			            		<td class="">地锁类型</td>
			            		<td class="">地锁品牌</td>
			            		<td class="">MAC地址</td>
			            		<td class="">停车场</td>
			            		<td class="">车位号</td>
			            		<td class="">SIM卡号</td>
			            		<td class="">电量(%)</td>
			            		<td class="">信号(dBm)</td>
			            		<td class="">最后一次通讯时间</td>
			            		<td>操作</td>
			            	</tr>
			            	<s:iterator value="groundlockPage.list" var="g">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${g.groundlockid}" id="checkbox_${g.groundlockid}" class="checkItem"/></td>
			            		<td class="">
			            		<s:if test="%{#g.onlineStatus == 0}">在线</s:if>
			            		<s:elseif test="%{#g.onlineStatus == 1}"><span style="color:#ff0000">掉线</span></s:elseif>
			            		<s:elseif test="%{#g.onlineStatus == 2}"><span style="color:#0000ff">休眠</span></s:elseif>
			            		</td>
			            		<td>
			            			<s:if test="%{#g.groundlockStatus == 0}">升起</s:if>
			            			<s:else>降下</s:else>
			            		</td>
			            		<td>
			            			<s:if test="%{#g.locked_type == 1}">纯联网</s:if>
			            			<s:elseif test="%{#g.locked_type == 2}"><span style="color:#ff0000">纯蓝牙</span></s:elseif>
			            			<s:elseif test="%{#g.locked_type == 3}"><span style="color:#0000ff">联网蓝牙</span></s:elseif>
			            		</td>
			            		<td>
			            			<s:if test="%{#g.brand == 1}">泊泊-BC</s:if>
			            			<s:elseif test="%{#g.brand == 2}">泊泊-BX</s:elseif>
			            		</td>
			            		<td class=""><a href="<%=path %>/runningRecords_list?mac=${g.groundlockMac}">${g.groundlockMac}</a></td>
			            		<td class="">${g.carparkname}</td>
			            		<td class="">${g.berthname}</td>
			            		<td class="">${g.simNum}</td>
			            		<td class="">
			            		<s:if test="%{#g.protectionPower >= #g.batteryPower }"><span style="color:#ff0000">${g.batteryPower}</span></s:if>
			            		<s:else>${g.batteryPower}</s:else>
			            		</td>
			            		<td class="">${g.simIntensity}</td>
			            		<td class=""><long_date:long_date datelong="${g.lastConnectTime}" /></td>
			            		
			            		<td style="width: 300px;" align="center">
			            			<div style="width: 300px;text-align: center; ">
			            			<a href="javascript:void(0);" onclick="upLock('${g.groundlockid}')">升锁</a> | 
			            			<a href="javascript:void(0);" onclick="downLock('${g.groundlockid}')">降锁</a> | 
			            			<a href="javascript:void(0);" onclick="sendGetEnergy('${g.groundlockid}')" >电量</a> |
			            			<a href="javascript:void(0);" onclick="sendGetSignal('${g.groundlockid}')" >信号</a> |
			            			<a href="javascript:void(0);" onclick="smsWeakup('${g.groundlockid}')" >唤醒</a> |
			            			<a href="javascript:void(0);" onclick="sendSleep('${g.groundlockid}')" >休眠</a> |
			            			<a href="javascript:void(0);" onclick="changeBluetoothList('${g.groundlockid}')">蓝牙</a> |
			            			<a href="javascript:void(0);" onclick="changeWeakupPhoneNumberList('${g.groundlockid}')">电话</a> |
			            			<a href="javascript:void(0);" onclick="changeServiceIPList('${g.groundlockid}')">IP</a> |
			            			<a href="javascript:void(0);" onclick="bindUser('${g.groundlockid}')">绑定用户</a> |
			            			<a href="javascript:void(0);" onclick="bindBerth('${g.groundlockid}')">绑定车位</a> |
			            			<a href="javascript:void(0);" onclick="foreceUpLock('${g.groundlockid}')">强制升锁</a> | 
			            			<a href="javascript:void(0);" onclick="foreceDownLock('${g.groundlockid}')">强制降锁</a> | 
			            			<a href="<%=path %>/groundlock_setting?mac=${g.groundlockid}">配置</a>
			            			<a href="<%=path %>/dynamicRecords_list?mac=${g.groundlockMac}" >动态记录</a> | 
			            			</div>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${groundlockPage.allRow}</i>条记录，当前第 ${groundlockPage.currentPage}/${groundlockPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{groundlockPage.totalPage > 10}">
				          	<s:if test="%{groundlockPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{groundlockPage.totalPage > (groundlockPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="groundlockPage.currentPage-5" />
								   	<s:param name="last" value="groundlockPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{groundlockPage.currentPage==(#count.index+groundlockPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="groundlockPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="groundlockPage.currentPage-5" />
								   	<s:param name="last" value="groundlockPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{groundlockPage.currentPage==(#count.index+groundlockPage.currentPage-5)}">
									   		<span class="current"><s:property/></span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
				          		</s:else>
				          	</s:if>
				          	<s:else>
				          		<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="10" />
							   	<s:iterator status="count">
								   	<s:if test="%{groundlockPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="groundlockPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{groundlockPage.totalPage>1&&10>=groundlockPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="groundlockPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{groundlockPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
							</s:bean>
				        </s:elseif>
			       		</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var the_host = "<%=path%>/";


$(document).ready(function(){
	$(".m_groundlock_l").attr("class","lhover");
	/**
	 * 分页
	 */
	$(".sabrosus a").each(function(){  
        $(this).click(function(){ 
        	$('#page').val($(this).attr("rel"));
        	$('#searchForm').submit();
        });  
    });
	/**
	 * 全选操作
	 */
	$("#checkAll").click(function() {
		$('input[name="checkbox"]').attr("checked",this.checked); 
	});
    $("input[name='checkbox']").click(function(){
		$("#checkAll").attr("checked",$("input[name='checkbox']").length == $("input[name='checkbox']:checked").length ? true : false);
    });
    /*** 自定义select***/
    $('.son_ul').hide(); //初始ul隐藏
	$('.select_box span').hover(function(){ //鼠标移动函数
		$(this).parent().find('ul.son_ul').slideDown();  //找到ul.son_ul显示
		$(this).parent().find('li').hover(function(){$(this).addClass('hover')},function(){$(this).removeClass('hover')}); //li的hover效果
		$(this).parent().hover(function(){},
			function(){
				$(this).parent().find("ul.son_ul").slideUp(); 
			}
		);
		},function(){}
	);
	$('ul.son_ul li').click(function(){
		$(this).parents('li').find('span').html($(this).html());
		if($(this).attr("rel") == ""){
			$(this).parents('li').find('input').val("");
		}else{
			$(this).parents('li').find('input').val($(this).attr("rel"));
		}
		$(this).parents('li').find('ul').slideUp();
	});
	$('ul.son_ul li').each(function(){
		if($(this).parents('li').find('input').val() == $(this).attr("rel")){
			$(this).parents('li').find('span').html($(this).html());
		}
	});
	/*** 自定义select END***/
	
	//加载扩展模块
	layer.config({
	    extend: the_host + 'page/plugins/layer/extend/layer.ext.js'
	});
});
/**
 * 强制升锁
 * @param {Object} groundlockid
 */
function foreceUpLock(groundlockid)
{
	 layer.confirm('确定要强制升锁吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_foreceUp" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('强制升锁成功', {icon: 6});
					setTimeout(function(){
					    $('#searchForm').submit();
					}, 500);
				}
				else
				{
					if(data.errorcode == 45){
						layer.msg('强制升锁失败', function(){});
					}
					else if(data.errorcode == 100){
						layer.msg('强制升锁失败，请求过期', function(){});
					}
					else if(data.errorcode == 44){
						layer.msg('强制升锁失败，地锁休眠正在唤醒...', function(){});
					}
					else{
						layer.msg('强制升锁失败,未知错误', function(){});
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
  * 强制降锁
  * @param {Object} groundlockid
  */
function foreceDownLock(groundlockid)
{
	layer.confirm('确定要强制降锁吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_foreceDown" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('强制降锁成功', {icon: 6});
					setTimeout(function(){
					    $('#searchForm').submit();
					}, 500);
				}
				else
				{
					if(data.errorcode == 45){
						layer.msg('强制降锁失败', function(){});
					}
					else if(data.errorcode == 100){
						layer.msg('强制降锁失败，请求过期', function(){});
					}
					else if(data.errorcode == 44){
						layer.msg('强制降锁失败，地锁休眠正在唤醒...', function(){});
					}
					else{
						layer.msg('强制降锁失败,未知错误', function(){});
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
					    $('#searchForm').submit();
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
						layer.msg('升锁失败，地锁休眠正在唤醒...', function(){});
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
					    $('#searchForm').submit();
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
						layer.msg('降锁失败，地锁休眠正在唤醒...', function(){});
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
  * 短信唤醒
  * @param {Object} groundlockid
  */
function smsWeakup(groundlockid)
{
	layer.confirm('确定要短信唤醒地锁吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_smsWeakup" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('短信发送成功', {icon: 6});
				}
				else
				{
					layer.msg('短信发送失败', function(){});
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
					    $('#searchForm').submit();
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
					layer.msg('信号获取成功', {icon: 6});
					setTimeout(function(){
					    $('#searchForm').submit();
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
 * 
 * 休眠地锁
 */ 
function sendSleep(groundlockid)
{
	layer.confirm('确定要休眠地锁吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_sendSleep" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('休眠地锁成功', {icon: 6});
					setTimeout(function(){
					    $('#searchForm').submit();
					}, 500);
				}
				else
				{
					layer.msg('休眠地锁失败', function(){});
				}
			},
			error: function (XMLResponse) {
				layer.close(index);
			}
		});
	});
}
/**
 * 修改可唤醒地锁电话列表
 * 
 */
function changeWeakupPhoneNumberList(groundlockid)
{
	layer.open({
	    type: 2,
	    title: '授权电话列表',
	    shadeClose: true,
	    shade: 0.5,
	    area: ['900px', '500px'],
	    content:the_host + 'weakupPhones_list?groundlockid=' + groundlockid //iframe的url
	}); 
}
/**
 * 修改服务器IP列表
 * 
 */
function changeServiceIPList(groundlockid)
{
	layer.open({
	    type: 2,
	    title: '修改服务器IP列表',
	    shadeClose: true,
	    shade: 0.5,
	    area: ['900px', '500px'],
	    content:the_host + 'serviceIps_list?groundlockid=' + groundlockid //iframe的url
	}); 
}

 /**
  * 
  * 修改收取蓝牙列表
  * @param {Object} groundlockid
  */
function changeBluetoothList(groundlockid)
{
	layer.open({
	    type: 2,
	    title: '授权蓝牙列表',
	    shadeClose: true,
	    shade: 0.5,
	    area: ['900px', '500px'],
	    content:the_host + 'accessBluetooth_list?groundlockmac=' + groundlockid //iframe的url
	}); 
}
/**
 * 绑定用户
 */
function bindUser(groundlockid)
{
	layer.open({
	    type: 2,
	    title: '绑定用户',
	    shadeClose: true,
	    shade: 0.5,
	    area: ['900px', '500px'],
	    content:the_host + 'groundlock_bindUser?mac=' + groundlockid //iframe的url
	});
}
/**
 * 绑定车位
 */
function bindBerth(groundlockid)
{
	layer.open({
	    type: 2,
	    title: '绑定车位',
	    shadeClose: true,
	    shade: 0.5,
	    area: ['900px', '500px'],
	    content:the_host + 'groundlock_bindCarpark?mac=' + groundlockid //iframe的url
	});
}

/**
 * 删除地锁
 */
function bulkDelete(ids){
	if(ids == 0){
		var ids = "";
		$("[name='checkbox']:checked").each(function(){
			ids += $(this).val()+",";
		});
		if(ids.length>0){
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"groundlock_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"groundlock_delete?ids="+ids;
		});
	}
		
}
</script>
</body>
</html>