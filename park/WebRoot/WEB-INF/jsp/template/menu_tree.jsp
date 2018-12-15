<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String menupath = request.getContextPath();
%>
<link type="text/css" rel="stylesheet" href="<%=menupath %>/page/plugins/jquery-treeview/jquery.treeview.css" />
<script type="text/javascript" src="<%=menupath %>/page/plugins/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=menupath %>/page/plugins/jquery-treeview/lib/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=menupath %>/page/plugins/jquery-treeview/jquery.treeview.min.js"></script>

<script type="text/javascript">
$(function(){
	
	$("#red").treeview({
		animated: "fast",
		collapsed: true,
		unique: true,
		persist: "cookie",
		toggle: function() {
			
		}
	});
})
</script>
<div class="content-left" id="left">
	<div class="left1">
		系统菜单
	</div>
		<ul id="red" class="treeview-red">
			<li><span>停车场</span>
				<ul>
					<li><a href="<%=menupath %>/carpark_list">停车场管理</a></li>
					<li><a href="?">车位认证</a></li>
					<li><a href="?">车位分享</a></li>
				</ul>
			</li>
			<li><span>用户管理</span>
				<ul>
					<li><a href="?">用户管理</a></li>
					<li><a href="?">订单管理</a></li>
					<li><a href="?">车主认证</a></li>
					<li><a href="?">充值记录</a></li>
					<li><a href="?">提现</a></li>
				</ul>
			</li>
			<li><span>APP</span>
				<ul>
					<li><a href="?">APP</a></li>
					<li><a href="?">APP渠道</a></li>
					<li><a href="?">APP活动</a></li>
					<li><a href="?">邀请码</a></li>
				</ul>
			</li>
			<li><span>其它</span>
				<ul>
					<li><a href="?">系统通知</a></li>
					<li><a href="?">意见反馈</a></li>
				</ul>
			</li>
		</ul>
	<div class="left2 l2">
		<dl>
			<dt>应用管理</dt>
			<dd class="m_carpark_l"><a href="<%=menupath %>/carpark_list">停车场</a></dd>
			<sec:authorize ifAnyGranted="users_list,users_delete">
			<dd class="m_users_l"><a href="<%=menupath %>/users_list">用户管理</a></dd>
			</sec:authorize>
			<dd class="m_berthOrder_l"><a href="<%=menupath %>/berthOrder_list">订单管理</a></dd>
			<dd class="m_userBalance_l"><a href="<%=menupath %>/userBalance_list">充值记录</a></dd>
			<dd class="m_withdraw_l"><a href="<%=menupath %>/withdraw_list">提现</a></dd>
			<dd class="m_userApprove_l"><a href="<%=menupath %>/userApprove_list">车主认证</a></dd>
			<dd class="m_carparkAuthentication_l"><a href="<%=menupath %>/carparkAuthentication_list">车位认证</a></dd>
			<dd class="m_berthShare_l"><a href="<%=menupath %>/berthShare_list">车位分享</a></dd>
			
			<dd class="m_invitecode_l"><a href="<%=menupath %>/invitecode_list">邀请码</a></dd>
			
			<sec:authorize ifAnyGranted="appVersion_list,appVersion_delete,appVersion_edit,appVersion_add">
			<dd class="m_appVersion_l"><a href="<%=menupath %>/appVersion_list">APP</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="appChannel_list,appChannel_delete,appChannel_edit,appChannel_add">
			<dd class="m_appChannel_l"><a href="<%=menupath %>/appChannel_list">APP渠道</a></dd>
			</sec:authorize>
			<dd class="m_appActivity_l"><a href="<%=menupath %>/appActivity_list">APP活动</a></dd>
			<dd class="m_notify_l"><a href="<%=menupath %>/notify_list">系统通知</a></dd>
			<sec:authorize ifAnyGranted="feedback_list,feedback_delete">
			<dd class="m_feedback_l"><a href="<%=menupath %>/feedback_list">意见反馈</a></dd>
			</sec:authorize>
		</dl>	
	</div>
	<div class="left2 l2">
		<dl>
			<dt>系统管理</dt>
			<sec:authorize ifAnyGranted="manager_list,manager_delete,manager_edit,manager_add">
			<dd class="m_manager_l"><a href="<%=menupath %>/manager_list">管理用户</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="role_list,role_delete,role_edit,role_add">
			<dd class="m_role_l"><a href="<%=menupath %>/role_list">角色</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="department_list,department_delete,department_edit,department_add">
			<dd class="m_department_l"><a href="<%=menupath %>/department_list">部门</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="resources_list,resources_delete,resources_edit,resources_add">
			<dd class="m_resources_l"><a href="<%=menupath %>/resources_list">资源权限</a></dd>
			</sec:authorize>
			<dd><a href="javascript:alert('开发中...');">枚举参数</a></dd>
			<dd><a href="javascript:alert('开发中...');">系统日志</a></dd>
		</dl>	
	</div>
</div>
