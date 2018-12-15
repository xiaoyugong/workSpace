<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.parkbobo.utils.*" %>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String clienturl = Configuration.getInstance().getValue("clienturl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>用户详情|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
<script type="text/javascript" >
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">用户管理</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			
			<div class="fbbox">
				<div class="fb-bt">
					<i>用户详情</i>
				</div>
				<s:set value="#{0:'未认证',1:'待审核',2:'通过',3:'未通过'}"  name="driverStatus" ></s:set>
			<s:set value="#{0:'正常',1:'锁定'}"  name="status" ></s:set>
				<div class="fb-nr">
					<div >
					        <table width="100%" border="0"  >
					        <tr>
					        	<td  align="right" >
					        		<table class="view_table">
					        		<tr >
								            <td align="right"  rowspan="2">用户头像:</td>
								            <td align="center"  rowspan="2" ><img src="<%=clienturl %>${userhead}" height="60px" width="100%"/></td>
						           		</tr>
						           			<tr>
					           			</tr>
					        			<tr>
								            <td align="right"  >用户昵称:</td>
								            <td align="center" >
								            	${user.nickname}
								            </td>
					           			 </tr>
					           
							            <tr>
								            <td align="right"  >用户姓名:</td>
								            <td align="center">
								            	${user.realname}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >用户名: </td>
								            <td align="center">
								            	${user.username}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >邮箱: </td>
								            <td align="center">
								            	${user.email}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >车牌号: </td>
								            <td align="center">
								            	${user.carNumber}
								            </td>
							            </tr>
							            	 <tr>
								            <td align="right" >信誉: </td>
								            <td align="center">
								            	 ${user.credit}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >分享信誉: </td>
								            <td align="center">
								            	${user.shareCredit}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >积分: </td>
								            <td align="center">
								            	 ${user.point}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >平台暂扣保证金: </td>
								            <td align="center">
								            	${user.deposit/100}
								            </td>
							            </tr>
							              <tr>
								            <td align="right" >登录地点: </td>
								            <td align="center">
								            	${user.area}
								            </td>
							            </tr>
							            
					        		</table>
					        	</td><%--
					        	
					        	右边
					        	--%><td  valign="top">
					        		<table class="view_table">
					        		<tr>
								            <td align="right" >积分: </td>
								            <td align="center">
								            	 ${user.point}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >平台暂扣保证金: </td>
								            <td align="center">
								            	${user.deposit/100}
								            </td>
							            </tr>
					        		<tr>
								            <td align="right" >车主认证状态: </td>
								            <td align="center">
								            
								            	<s:iterator value="#driverStatus" var="d">
								            		<s:if test="user.driverStatus==key">
								            			${value}
								            		</s:if>
								            	</s:iterator>
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >账号状态: </td>
								            <td align="center">
								            	<s:iterator value="#status" var="d">
								            		<s:if test="user.status==key">
								            			${value}
								            		</s:if>
								            	</s:iterator>
								            </td>
							            </tr>
							           
							             <tr>
								            <td align="right" >注册时间: </td>
								            <td align="center">
								            
								            <s:date   name="user.registerTimeToDate" format="yyyy-MM-dd HH:mm:ss" />	
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >最后登录时间: </td>
								            <td align="center">
								            <s:date   name="user.loginTimeToDate" format="yyyy-MM-dd HH:mm:ss" />
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >用户来源渠道: </td>
								            <td align="center">
								            	${user.appChannel.name}
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >注册地经度: </td>
								            <td align="center">
								            	${user.registerLon}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >注册地纬度: </td>
								            <td align="center">
								            	${user.registerLat}
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >邀请码: </td>
								            <td align="center">
								            	${user.invitecode}
								            </td>
							            </tr>
							             <tr>
								            <td align="right" >备注: </td>
								            <td align="center">
								            	${user.memo}
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
</script>
</body>
</html>