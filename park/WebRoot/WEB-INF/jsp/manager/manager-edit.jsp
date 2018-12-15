<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
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
	<title>管理用户|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<link rel="stylesheet" href="<%=path %>/page/plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
	<script type="text/javascript" src="<%=path %>/page/plugins/ztree/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<style type="text/css">
		select{height: 28px; line-height: 28px}
	</style>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">系统管理</a> » <a href="###">管理用户</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>管理用户编辑</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="manager_edit" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="edit"/>
							<input type="hidden" name="resourcesIds" id="resourcesIds"/>
							<input type="hidden" name="manager.userId" value="${manager.userId}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">用户名： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1"  value="${manager.username}" name="manager.username"
						            	 	readonly="readonly"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">密码： </td>
						            <td align="left">
										<input type="text" class="mes_form1" id="password" name="manager.password" 
						            	 	data-rule="length[~100, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">姓名： </td>
						            <td align="left">
										<input type="text" class="mes_form1" id="realname" name="manager.realname" value="${manager.realname}"
						            	 	data-rule="姓名:required; length[~50, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">邮箱： </td>
						            <td align="left">
										<input type="text" class="mes_form1" id="email" name="manager.email" value="${manager.email}"
						            	 	data-rule="邮箱:required;email;length[~100, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">qq： </td>
						            <td align="left">
										<input type="text" class="mes_form1" id="qq" name="manager.qq" value="${manager.qq}"
						            	 	data-rule="length[~20, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">手机： </td>
						            <td align="left">
										<input type="text" class="mes_form1" id="mobile" name="manager.mobile" value="${manager.mobile}"
						            	 	data-rule="mobile;length[~100, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">部门： </td>
						            <td align="left">
					            		<s:select theme="simple" list="departments" name="manager.department.departmentid" 
					            			data-rule="部门:required; " value="manager.department.departmentid"
       										listKey="departmentid" listValue="name" headerKey="" headerValue="-请选择-"></s:select>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">角色： </td>
						            <td align="left">
										<s:iterator value="roles" var="r">
											<s:if test="!managerRoleMap[#r.roleId] eq ''">
											<label><input type="checkbox" name="roleIds" value="${r.roleId}" style="vertical-align: middle;" checked="checked"/> ${r.name}</label>&nbsp;&nbsp;
											</s:if>
											<s:else>
											<label><input type="checkbox" name="roleIds" value="${r.roleId}" style="vertical-align: middle;" /> ${r.name}</label>&nbsp;&nbsp;
											</s:else>
										</s:iterator>
						            </td>
					            </tr>
					            
					        	
					        	<tr>
						            <td align="right" width="110px" valign="top">用户权限： </td>
						            <td align="left">
						            	<div style="width:270px;height:300px;text-align:left; float: left; overflow: auto; border: 1px solid #ccc;">
											<ul id="resourcesTree" class="ztree"></ul>
										</div>
						            </td>
					            </tr>
					        </table>
							<div class="tijiao">
					        	<input type="button" value="提  交" class="btn1" onclick="_submit()"/>
					        </div>
						</form>   	
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};
var zNodes =[${resourcesJson}];
$(function() {
	$(".m_manager_l").attr("class","lhover");
	$.fn.zTree.init($("#resourcesTree"), setting, zNodes);
	zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
});
var _submit = function(){
	var resourcesIds = "";
	var zTree = $.fn.zTree.getZTreeObj("resourcesTree");
	var nodes = zTree.getCheckedNodes(true);
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].pId != null && nodes[i].pId != '0' && nodes[i].id.substring(0,2) != 'm_'){
			resourcesIds+=nodes[i].id+",";
		}
	}
	$("#resourcesIds").val(resourcesIds);
	$('#addForm').submit();
}
</script>
</body>
</html>