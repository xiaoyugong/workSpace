<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>

<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>车位订单详情|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
<script type="text/javascript" >
$(function(){
	$(".view_table").attr("border",1);
	$(".view_table td:even").css("background",'#EDF6FA').css("font-size",'13px').css("font-weight","bold");
})
</script>
<style>
				.view_table td{
					height: 30px;
				}
			</style>
</head>
<body >
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
	<div class="content-right" >
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">订单管理</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			
			<div class="fbbox">
				<div class="fb-bt">
					<i>订单详情</i>
				</div>
				<s:set value="#{0:'没有违约',1:'违约处理中',2:'违约处理结束'}"  name="dstatus" ></s:set>
			<s:set value="#{0:'预约开始',1:'预约结束'}"  name="status" ></s:set>
				<div class="fb-nr">
					<div style="margin-left: 10px;">
					   <table class="view_table" style="width: 100%;">
						  <tr>
						    <td width="15%">停车场</td>
						    <td width="35%">${berthOrder.berthShare.carparkname}</td>
						    <td width="15%">车位号</td>
						    <td width="35%">${berthOrder.berthShare.berthnum}</td>
						  </tr>
						  <tr>
						    <td>分享时段</td>
						    <td><s:date name="berthOrder.startTime" format="HH:mm"/>--<s:date name="berthOrder.endTime" format="HH:mm"/></td>
						    <td>违约金</td>
						    <td>${berthOrder.exceedPrice/100}</td>
						  </tr>
						  <tr>
						    <td>首停价格</td>
						    <td>${berthOrder.beforePrice/100}元/${berthOrder.beforeMins}分钟</td>
						    <td>每增价格</td>
						    <td>${berthOrder.afterPrice/100}元/${berthOrder.afterMins}分钟</td>
						  </tr>
						  <tr>
						    <td>重复日期</td>
						    <td>${berthOrder.formatRepeatDate}</td>
						    <td>入场须知</td>
						    <td>${berthOrder.description}</td>
						  </tr>
						  
						</table>
						<table class="view_table" style="width: 100%">
						  <tr>
						    <td width="15%">昵称</td>
						    <td width="35%">${berthOrder.nickname}</td>
						    <td width="15%">手机号</td>
						    <td width="35%">${berthOrder.telephone}</td>
						  </tr>
						  <tr>
						    <td>车牌号</td>
						    <td>${berthOrder.carNumber}</td>
						    <td>订单状态</td>
						    <td><s:iterator value="#status" var="type">
									<s:if test="key==berthOrder.status">
						       			${value}
									</s:if>
								</s:iterator></td>
						  </tr>
						  <tr>
						    <td>预定时段</td>
						    <td><long_date:long_date datelong="${berthOrder.preStartTime}" />--<long_date:long_date datelong="${berthOrder.preEndTime}" /></td>
						    <td>预定时长</td>
						    <td>${(berthOrder.preEndTime-berthOrder.preStartTime)/(1000*60)}分钟</td>
						  </tr>
						   <tr>
						    <td>是否入场</td>
						    <td>${berthOrder.isEnter==1?'是':'否'}</td>
						    <td>入场时间</td>
						    <td><long_date:long_date datelong="${berthOrder.entertime}" /></td>
						  </tr>
						  <tr>
						    <td>是否离场</td>
						    <td>${berthOrder.isLeave==1?'是':'否'}</td>
						    <td>离场时间</td>
						    <td><long_date:long_date datelong="${berthOrder.leavetime}" /></td>
						  </tr>
						  <tr>
						    <td>提交订单时间</td>
						    <td><long_date:long_date datelong="${berthOrder.posttime}" /></td>
						    <td>结束订单时间</td>
						    <td><long_date:long_date datelong="${berthOrder.closetime}" /></td>
						  </tr>
						  <tr>
						    <td>提交订单经纬度</td>
						    <td>${berthOrder.postLon}，${berthOrder.postLat}</td>
						    <td>结束订单经纬度</td>
						    <td>${berthOrder.closeLon}，${berthOrder.closeLat}</td>
						  </tr>
						  <tr>
						    <td>是否评论</td>
						    <td>${berthOrder.isReview==1?'是':'否'}</td>
						    <td>是否违约</td>
						    <td><s:iterator value="#dstatus" var="dtype">
									<s:if test="key==berthOrder.defaultStatus">
										${value}
									</s:if>
								</s:iterator></td>
						  </tr>
						   <tr>
						    <td>预付金额</td>
						    <td>${berthOrder.prepayment/100}</td>
						    <td>车位主收益</td>
						    <td>${berthOrder.ownerRevenue/100}</td>
						  </tr>
						  <tr>
						    <td>物业收益</td>
						    <td>${berthOrder.propertyRevenue/100}</td>
						    <td>平台收益</td>
						    <td>${berthOrder.companyRevenue/100}</td>
						  </tr>
						
						</table>
					           
					        
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

 <script type="text/javascript">
$(".m_berthOrder_l").attr("class","lhover");
</script>
</body>
</html>