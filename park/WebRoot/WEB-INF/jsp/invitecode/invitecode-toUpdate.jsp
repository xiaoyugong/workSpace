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
	<title>${invitecode==null?'添加':'修改' }邀请码|泊泊停车</title>
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
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">邀请码</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>修改${invitecode==null?'添加':'修改' }</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/invitecode_update" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
					        <table width="100%" border="0" class="adduser_tab">
					        
					        	<tr>
						            <td align="right" width="110px">邀请码： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="name" style="border:0"  readonly="readonly" value="${invitecode.invitecode}"
						            	 	data-rule="邀请码:required; length[~50, true];"/>
						            	<input type="hidden" class="mes_form1" id="name"  name="invitecode.userid"  value="${invitecode.userid}"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px" valign="top">详情： </td>
						            <td align="left">
										<textarea name="invitecode.description"  class="mes_form1" data-rule="length[~300]" style="width: 250px; height: 100px" id="description">${invitecode.description}</textarea>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">备注: </td>
						          <td> <input type="text" class="mes_form1" id="name" name="invitecode.memo"  value="${ invitecode.memo}"
						            	 	data-rule="备注:required; length[~100, true]"/></td>
					            </tr>
					        </table>
							<div class="tijiao">
					        	<input type="submit" value="发送" class="btn1" onclick="return user.selectVal();"/>
					        </div>
						</form>   	
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
$(".m_invitecode_l").attr("class","lhover");
</script>
</body>
</html>