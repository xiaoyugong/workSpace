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
	<title>余额管理|泊泊停车</title>
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
balance = '${user.balance}';
	function reduce(obj){
			var par = $(obj).parent();
			pointv = par.find("input").val();
			par.find("input").val(Number(pointv)-100);
		}
		function add(obj){
			var par = $(obj).parent();
			pointv = par.find("input").val();
			par.find("input").val(Number(pointv)+100);
			
		}
		
		function submitForm(){
			pointv = $("#user_balance").val();
 				if(isNaN(pointv)){
 					layer.alert("请输入整数!");
 					return false;
 				}
 				if($.trim($("#_description").val())==""){
 					layer.alert("请填写描述!!");
 					return false;
 				}
 				var a_r="";
 				if(Number(pointv)<0){
 					a_r="减少:";
 				}if(Number(pointv)>0){
 					a_r="增加:";
 				}
     			  layer.confirm(a_r+(Number(pointv)-Number(balance))/100+"元",function(f){
					$("#addForm").submit();
     			  });
			
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
					<i>余额</i>
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
							            		<td>余额</td>
							            		<td>车牌号</td>
							            		<td >注册时间</td>
								        	</tr>
							        	
							        			<s:iterator value="listusers" var="u">
							        			<tr>
							        				<td>${u.username}</td>
							        				<td>${u.nickname}</td>
							        				<td>${u.realname}</td>
							        				<td>${u.balance/100}</td>
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
						<form action="<%=path %>/users_balanceSave" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="ids" value="${ids}"/>
							<s:token></s:token>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
					        		<td align="right" width="110px">变化值(分)： </td>
					        		<td class="jia">
					        			<a href="javascript:void(0)" onclick="reduce(this)" >
					        				<img src="<%=path %>/page/images/jian.png" alt="" />
					        			</a>
					        			<input type="text" name="userBalance.amount" style="width:50px" id="user_balance"  data-rule="required; integer;" value="${user.balance}"/>
					        			<a href="javascript:void(0)" onclick="add(this);">
					        				<img src="<%=path %>/page/images/jia.png" alt="" />
					        			</a>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td align="right" width="110px">描述： </td>
					        		<td class="jia">
					        			<input type="text" class="mes_form1" name="description" id="_description"  data-rule="required;"/>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td align="right" width="110px">发送通知:</td>
					        		<td>&nbsp;&nbsp;&nbsp;
					        			是:<input type="radio" name="isSendNotify" value="1" checked="checked"/>
					        			&nbsp;&nbsp;
					        			否:<input type="radio" name="isSendNotify"  value="0"/>
					        		</td>
					        	</tr>
					        	
					        </table>
							<div class="tijiao">
					        	<input type="button" value="确定" class="btn1" onclick="submitForm()"/>
					        	 
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