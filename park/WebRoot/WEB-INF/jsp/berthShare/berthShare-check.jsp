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
	<title>车位分享|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
<script>
$(function(){
	$(".view_table").attr("border",1);
	$(".view_table td:even").css("background",'#EDF6FA').css("font-size",'13px');
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">车位分享</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>车位详情</i>
				</div>
				<div class="fb-nr">
					<div>
					        <table  width="100%"   border="0" >
					        <tr>
					        	<td  align="right">
					        		<table class="view_table">
					        			<tr>
								            <td align="right" >用户名： </td>
								            <td align="center" >
								            	${berthShare.users.username}
								            </td>
					           			 </tr>
					           
							            <tr>
								            <td align="right" >停车场名称: </td>
								            <td align="center">
								            	${berthShare.carparkname}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >车位号: </td>
								            <td align="center">
								            	${berthShare.berthnum}
								            </td>
							            </tr>
							            <s:set value="#{0:'微地图',1:'无微地图'}" name="carparkType"></s:set>
							            <tr>
								            <td align="right" >认证类型: </td>
								            <td align="center">
								            	<s:iterator value="#carparkType" var="type">
							            			<s:if test="key==berthShare.sharetype">
							            				${type.value}
							            			</s:if>
					            				</s:iterator>
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >分享数量: </td>
								            <td align="center">
								            	${berthShare.sharenum}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >剩余数量: </td>
								            <td align="center">
								            	${berthShare.emptynum}
								            </td>
							            </tr>
							            	 <tr>
								            <td align="right" >起始时间: </td>
								            <td align="center">
								            	<s:date name="berthShare.startTime" format="HH:mm"/>
								            	
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >结束时间: </td>
								            <td align="center">
								            <s:date name="berthShare.endTime" format="HH:mm"/>
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >首停分钟: </td>
								            <td align="center">
								            	${berthShare.beforeMins}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >首停价格: </td>
								            <td align="center">
								            	${berthShare.beforePrice/100}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >每增分钟: </td>
								            <td align="center">
								            	${berthShare.afterMins}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >每增价格: </td>
								            <td align="center">
								            	${berthShare.afterPrice/100}
								            </td>
							            </tr>
							               
					        		</table>
					        	</td><%--
					        	
					        	右边
					        	--%><td valign="top">
					        		<table class="view_table">
							            <tr>
								            <td align="right" >可停保证金: </td>
								            <td align="center">
								            	${berthShare.enstopDeposit/100}
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >违约保证金: </td>
								            <td align="center">
								            	${berthShare.defaultDeposit/100}
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >超期停车价格: </td>
								            <td align="center">
								            	${berthShare.exceedPrice/100}
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >重复日期: </td>
								            <td align="center">
								            	${berthShare.formatRepeatDate}
								            </td>
							            </tr>
							             <s:set value="#{0:'未审核',1:'审核中',2:'通过认证',3:'未通过认证'}" name="statusType"></s:set>
							            <tr>
								            <td align="right" >认证状态: </td>
								            <td align="center">
								            	<s:iterator value="#statusType" var="autu">
													<s:if test="key==berthShare.isAuthentication">
														${autu.value}
													</s:if>
												</s:iterator>
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >是否被预约: </td>
								            <td align="center">
								            	${berthShare.isReserve==1?'是':'否'}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >是否关闭: </td>
								            <td align="center">
								            	${berthShare.isClose==1?'是':'否'}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >包含出入管理费： </td>
								            <td align="center">
								            	${berthShare.sfbhglf==1?'是':'否'}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >入场须知： </td>
								            <td align="center">
								            	${berthShare.description}
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >提交时间: </td>
								            <td align="center">
								            <long_date:long_date datelong="${berthShare.sharetime}"></long_date:long_date>
								            	
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >备注: </td>
								            <td align="center">
								            	${berthShare.memo}
								            </td>
							            </tr>
					        		</table>
					        	</td>
					        </tr>
					        	
					            <tr>
					        	<td colspan="2" align="center">
					        		  <a href="javascript:history.go(-1);" ><font color="gray">返回</font></a>
					        	</td>
					        </tr>
					           
					       
					        </table>
					           
					        
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

 <script type="text/javascript">
$(".m_berthShare_l").attr("class","lhover");
</script>
</body>
</html>