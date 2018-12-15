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

</script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
	<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">车主认证</a></div>
				<div class="z-r-b-r"><%=date %></div>
				
			</div>
			<style>
				.tab2{
					width:400px;
					}	
			</style>
			<div class="fbbox">
				<div class="fb-bt">
					<i>上传附件</i>
				</div>
				<table>
					<tr>
						<td valign="top" >
						<div class="listbox">
							<div class="listnr" >
								<table  class="tab2" id="tab" >
									<tbody >
								        	<tr class="tab2yt">
								        		<td class="">用户名</td>
							            		<td class="">姓名</td>
							            		<td class="">昵称</td>
												<td>车牌号</td>
								        	</tr>
							        			<tr>
							        				<td>${driverAuth.users.username}</td>
							        				<td>${driverAuth.users.realname}</td>
							        				<td>${driverAuth.users.nickname}</td>
							        				<td>${driverAuth.users.carNumber}</td>
								        		</tr>
							        	</tbody>
							        </table>
							        
							</div>
							</div>
						</td>
						
						
						<td >
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/driverAuth_uploadSaveAttachment" method="post" enctype="multipart/form-data" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="driverAuth.userid" value="${driverAuth.userid}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
					        		<td align="right" width="110px">附件： </td>
					        		<td>
					        			<input type="file" name="driverAttachment" value=""/>
					        		</td>
					        	</tr>
					        </table>
							<div class="tijiao">
					        	<input type="submit" value="确定" class="btn1"/>
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
$(".m_driverAuth_l").attr("class","lhover");
</script>
</body>
</html>