<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
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
	<title>积分管理|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/layer1.85/layer.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	
<script>
host = "<%=path %>/";
		function reduce(obj){
			var par = $(obj).parent();
			pointv = par.find("input").val();
			par.find("input").val(Number(pointv)-1);
		}
		function add(obj){
			var par = $(obj).parent();
			pointv = par.find("input").val();
			par.find("input").val(Number(pointv)+1);
		}
</script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">用户管理</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<style>
					.tab2{
					width:400px;
					}
			</style>
			<div class="fbbox">
				<div class="fb-bt">
					<i>停车券</i>
				</div>
				
				<table>
					<tr>
						<td valign="top" >
						<div class="listbox">
							<div class="listnr" >
								<table  class="tab2" id="tab" >
									<tbody >
								        	<tr class="tab2yt">
								        		<td >用户名</td>
							            		<td >昵称</td>
							            		<td >姓名</td>
							            		<td>停车券</td>
							            		<td>车牌号</td>
							            		<td >注册时间</td>
								        	</tr>
							        	
							        			<s:iterator value="listusers" var="u">
							        			<tr>
							        				<td>${u.username}</td>
							        				<td>${u.nickname}</td>
							        				<td>${u.realname}</td>
							        				<td>${u.couponBalnce/100}</td>
							        				<td>${u.carNumber}</td>
							        				<td class=""><fmt:formatDate value="${u.registerTimeToDate}" pattern="yyyy/MM/dd HH:mm"/></td>
								        		</tr>
							        			</s:iterator>
							        	</tbody>
							        </table>
							</div>
							</div>
						</td>
						
						
						<td >
						
							<div class="fb-nr">
								<div class="adduser_form">
									<form action="<%=path %>/users_couponSave" method="post" id="addForm" autocomplete="off" 
										data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
										<input type="hidden" name="ids" value="${ids}"/>
								        <table width="100%" border="0" class="adduser_tab">
								        <tr>
								            </tr>
								        	<tr style="height: 100px;">
								        		<td align="right" width="110px">变化值： </td>
								        		<td class="jia" >
								        			<a href="javascript:void(0)" onclick="reduce(this)" >
								        				<img src="<%=path %>/page/images/jian.png" style="position: relative; top: 1.5px;"  alt="" />
								        			</a>
								        			<input type="text" name="couponvalue" style="width:50px" data-rule="required; integer;" value="${user.couponBalnce}"/>
								        			<a href="javascript:void(0)" onclick="add(this);">
								        				<img src="<%=path %>/page/images/jia.png" style="position: relative; top: 1.5px;" alt="" />
								        			</a>
								        		</td>
								        	</tr>
										<!--<tr>
									            <td align="right" width="110px">变化事由： </td>
									            <td align="left">
									            	<input type="text" class="mes_form1" id="name" name="userPoint.description" 
									            	 	data-rule="变化事由:required; length[~100, true]"/>
									            </td>
								            </tr>
								        	<tr>
								        		<td align="right" width="110px">发送通知:</td>
								        		<td>&nbsp;&nbsp;&nbsp;
								        			是:<input type="radio" name="isSendNotify" value="1" checked="checked"/>
								        			&nbsp;&nbsp;
								        			否:<input type="radio" name="isSendNotify"  value="0"/>
								        		</td>
								        	</tr>-->
								        	
								        </table>
										<div class="tijiao">
								        	<input type="submit" value="确定" class="btn1" />
								        	 
								        </div>
										<div  class="tijiao">
											<a href="javascript:history.go(-1);" ><font color="gray">返回</font></a>
										</div>
									</form>  
								</div>
							</div>
					</td>
						
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>



 
<script type="text/javascript">
$(".m_users_l").attr("class","lhover");
</script>
</body>
</html>