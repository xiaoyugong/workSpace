<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
 <%@ page language="java" import="com.parkbobo.utils.*" %>
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
	<title>违约处理申请详情|泊泊停车</title>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">违约处理申请</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			
			<div class="fbbox">
				<div class="fb-bt">
					<i>违约处理申请</i>
				</div>
			<s:set value="#{1:'驳回',2:'成功',3:'协商中'}"  name="status" ></s:set>
			<s:set value="#{0:'车位被恶意入驻',1:'无法进去停车',2:'超期停车',3:'其他'}"  name="dtype" ></s:set>
			
				<div class="fb-nr">
					<div >
					        <table width="100%" border="0"  >
					        <tr>
					        	<td  align="center" >
					        		<table class="view_table">
					        			<tr>
								            <td align="right"  >用户名： </td>
								            <td align="center" >
								            	${defaultApply.users.username}
								            </td>
					           			 </tr>
					           
							            <tr>
								            <td align="right"  >申述类型: </td>
								            <td align="center">
								            	<s:iterator value="#dtype" var="dt">
						            				<s:if test="key==defaultApply.type">
						            					${value}
						            				</s:if>
						            			</s:iterator>
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >申述内容: </td>
								            <td align="center">
								            	${defaultApply.content}
								            </td>
							            </tr>
							            <tr>
								            <td align="right" >申述附件: </td>
								            <td align="center">
									            <c:if test="${defaultApply.attached!=null&&defaultApply.attached!=''}">
									            	<a href="<%=clienturl %>${defaultApply.attached}" target="_blank">点击打开</a>	
								           		</c:if>
								            </td>
							            </tr>
							             
							            <tr>
								            <td align="right" >处理状态: </td>
								            <td align="center">
								            	<s:iterator value="#status" var="type">
						            				<s:if test="key==defaultApply.status">
						            					${value}
						            				</s:if>
						            			</s:iterator>
								            </td>
							            </tr>
							            <form action="<%=path %>/defaultApply_update?" method="post">
							            <tr>
								            <td align="right" >审核意见: </td>
								            <td align="center">
								            	
								            	<textarea rows="3" cols="33" name="defaultApply.memo">${defaultApply.memo}</textarea>
								            </td>
							            </tr>
								      <s:if test="type==0">
							           </s:if>
							          <tr>
								            <td align="right" >选择状态: </td>
								            <td align="center">
								            	
								            		<input type="hidden" name="defaultApply.applyid" value="${defaultApply.applyid}"/>
								            		<s:select theme="simple" list="#status" value="key" key="value" name="defaultApply.status"></s:select>
								            		<input type="submit" value="提交"/>
								            </td>
							            </tr>
								            	</form>
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
$(".m_defaultApply_l").attr("class","lhover");
</script>
</body>
</html>