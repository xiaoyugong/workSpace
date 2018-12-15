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
	<title>角色|泊泊停车</title>
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
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">系统管理</a> » <a href="###">角色</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>角色编辑</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="role_edit" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="method" value="edit"/>
							<input type="hidden" name="role.roleId" value="${role.roleId}"/>
							<input type="hidden" name="resourcesIds" id="resourcesIds"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">角色名称： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="name" name="role.name" value="${role.name}"
						            	 	data-rule="角色名称:required; length[~100, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">角色编码： </td>
						            <td align="left">
										<input type="text" class="mes_form1" id="enname" name="role.enname" value="${role.enname}"
						            	 	data-rule="角色编码:required; length[~100, true]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">是否系统核心模块： </td>
						            <td align="left">
						            	<s:radio list="#{'0':'否','1':'是'}" name="role.iscore" data-rule="checked"
						 					listKey="key" listValue="value" theme="simple" value="role.iscore"></s:radio>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">是否可用： </td>
						            <td align="left">
						            	<s:radio list="#{'0':'否','1':'是'}" name="role.enable" data-rule="checked"
						 					listKey="key" listValue="value" theme="simple" value="role.enable"></s:radio>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">角色权限： </td>
						            <td align="left">
						            	<div style="width:270px;height:300px;text-align:left; float: left; overflow: auto; border: 1px solid #ccc;">
											<ul id="resourcesTree" class="ztree"></ul>
										</div>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">添加时间： </td>
						            <td align="left">
						            	<input type="text" name="role.createTimeToDate" value="<s:date name="role.createTimeToDate" format="yyyy-MM-dd HH:mm:ss" />" id="createTimeToDate"
											onclick="$.calendar({format:'yyyy-MM-dd HH:mm:ss' });" readonly="readonly" data-rule="添加时间:required; "
											style="background:url(page/plugins/lhgcalendar/iconDate.gif) center right no-repeat #f7f7f7;cursor:pointer;width:180px;padding:2px 2px 2px 2px;height:18px;line-height:18px;vertical-align:bottom;border: 1px solid #CCCCCC;width: 200px;height: 28px;line-height: 28px;"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">序列： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="orderid" name="role.orderid" value="${role.orderid}"
						            	 	data-rule="序列:required; integer; range[0~]"/>
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
	$(".m_role_l").attr("class","lhover");
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