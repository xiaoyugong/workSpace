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
	<title>车位认证|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
<script>

</script>
</head>
<body >
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
	<div class="content-right" >
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">车位认证</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>车位认证</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">用户名： </td>
						            <td align="left" >
						            	${carparkAuthentication.users.username}
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">提交时间: </td>
						            <td align="left">
						            	<long_date:long_date datelong="${carparkAuthentication.posttime }"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">停车场名称: </td>
						            <td align="left">
						            	${carparkAuthentication.carparkname}
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">车位号: </td>
						            <td align="left">
						            	${carparkAuthentication.berthnum}
						            </td>
					            </tr>
					            <s:set value="#{0:'停车场',1:'微地图车位',2:'无微地图车位'}" name="carparkType"></s:set>
					            <tr>
						            <td align="right" width="110px">认证类型: </td>
						            <td align="left">
						            	<s:iterator value="#carparkType" var="type">
					            			<s:if test="key==carparkAuthentication.authenticationtype">
					            				${type.value}
					            			</s:if>
			            				</s:iterator>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px" valign="top">附件: </td>
						            <td align="left"  class="z-r-b-l">
						            	<c:if test="${carparkAuthentication.attached!=null}">
						            		<c:choose>
												<c:when test="${fn:substring(carparkAuthentication.attached,0,4) eq 'http'}">
													<a href="${carparkAuthentication.attached}" target="_blank">点击打开</a>
												</c:when>
												<c:otherwise>
													<a href="http://app1.parkbobo.com/${carparkAuthentication.attached}" target="_blank">点击打开</a>
												</c:otherwise>
											</c:choose>
						            	</c:if>
						            	<c:if test="${carparkAuthentication.attached==null }">
						            		车主没有上传附件
						            	</c:if>
						            </td>
					            </tr>
								<form action="<%=path %>/carparkAuthentication_update" method="post">
									<input type="hidden" name="carparkAuthentication.authenticationid" value="${carparkAuthentication.authenticationid}"/>
									<s:token></s:token>
									<s:if test="carparkAuthentication.status==0">
				            			</s:if>
									<tr>
							            <td align="right" width="110px" valign="top">选择状态:</td>
							            <td align="left"  >
											<select name="carparkAuthentication.status">
													<option value="1">通过</option>
													<option value="2">未通过</option>
												</select>
							        	
							        	</td>
						        	</tr>
						        	<tr>
						        		 <td align="right" width="110px" valign="top">审核意见:</td>
						        		<td><textarea   cols="30" rows="3" name="carparkAuthentication.memo" >${carparkAuthentication.memo}</textarea></td>
						        	</tr>
						        	<tr>
						        		<td></td>
						        		<td>
						        		<div class="tijiao">
						        		
								        	<input type="submit" value="提交" class="btn1" />
								        </div></td>
						        	</tr>
						        	</form>
					        </table>
					           
					        
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

 
<script type="text/javascript">
$(".m_carparkAuthentication_l").attr("class","lhover");
</script>
</body>
</html>