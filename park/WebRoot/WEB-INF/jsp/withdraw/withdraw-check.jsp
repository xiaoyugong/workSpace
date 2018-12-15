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
	<title>提现记录|泊泊停车</title>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">提现</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>提现记录</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr align="left" >
						            <td >用户名： </td>
						            <td  >
						            	${withdrawLog.users.username}
						            </td>
					            </tr>
					             <tr>
					            <td class="">账户余额：</td>
					            <td>${withdrawLog.users.balance/100}</td>
					             </tr>
					            <tr>
					            <td class="">提现金额：</td>
					            <td>${withdrawLog.amount/100}</td>
					             </tr><tr>
			            		<td>开户名：</td>
			            		<td>${withdrawLog.name}</td>
			            		 </tr><tr>
			            		<td>账号：</td>
			            		<td>${withdrawLog.cardNum}</td>
			            		 </tr>
			            		 <tr>
			            		<td class="">记录时间</td>
			            		<td><long_date:long_date datelong="${withdrawLog.posttime}"/></td>
			            		 </tr>
			            		 <tr>
			            		<td>当前状态：</td>
			            		<s:set var="wltype" value="#{-1:'失败',0:'未处理',1:'处理中',2:'成功'}"></s:set>
			            		<td>
									<s:iterator value="#wltype" var="type">
			            				<s:if test="key == withdrawLog.status">
			            					${type.value}
			            				</s:if>
			            			</s:iterator>
								</td>
			            		 </tr>
								<form action="<%=path %>/withdraw_update" method="post">
									<input type="hidden" name="withdrawLog.withdrawid" value="${withdrawLog.withdrawid}"/>
									<tr>
						            <td   width="110px" valign="top">选择状态:</td>
						            <td align="left"  >
										<select name="withdrawLog.status">
												<option value="-1">失败</option>
												<option value="1">处理中</option>
												<option value="2">成功</option>
											</select>
											
						        	</td>
						        		
						        	</tr>
						        	<tr>
						        		<td>
						        			描述：
						        		</td>
						        		<td>
						        			<textarea rows="4" cols="23" name="withdrawLog.memo">${withdrawLog.memo}</textarea>
						        		</td>
						        	</tr>
						        	
						        	<tr>
						        		<td>
						        		<div>
						        		<s:if test="withdrawLog.status!=2&&withdrawLog.status!=-1">
								        	<input type="submit" value="提交" class="btn1" />
						        		</s:if>
								        </div></td>
						        		<td align="left">
						        		</td>
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
$(".m_withdraw_l").attr("class","lhover");
</script>
</body>
</html>