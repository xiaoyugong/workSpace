<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String menupath = request.getContextPath();
%>
<div class="content-left" id="left">
	<div class="left1">
		系统菜单
	</div>
		
	<div class="left2">
		<dl class="over">
			<dt>应用管理</dt>
			<sec:authorize ifAnyGranted="users_list,users_delete">
			<dd class="m_users_l"><a href="<%=menupath %>/users_list">用户管理</a></dd>
			</sec:authorize>
			<dd class=""><a href="<%=menupath %>/users_invitationList">用户邀请</a></dd>
			<sec:authorize ifAnyGranted="berthOrder_list">
			<dd class="m_berthOrder_l"><a href="<%=menupath %>/berthOrder_list">订单管理
				<s:if test="#session.driverOrderCqCount!=null">
						<font color='red'>[${sessionScope.driverOrderCqCount}]</font>
				</s:if>
			</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="orderTask_list">
			<dd class="m_orderTask_l"><a href="<%=menupath %>/orderTask_list">预约停车</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="userBalance_list">
			<dd class="m_userBalance_l"><a href="<%=menupath %>/userBalance_list">消费记录</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="withdraw_list">
			<dd class="m_withdraw_l" ><a href="<%=menupath %>/withdraw_list"  id="withdraw_tips">
					提现
					<s:if test="#session.withdrawDispose!=null">
						<font color='red'>[${sessionScope.withdrawDispose}]</font>
					</s:if>
					</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="driverAuth_list">
			<dd class="m_driverAuth_l"><a href="<%=menupath %>/driverAuth_list">
				车主认证
				<s:if test="#session.driverAuthenticationDispose!=null">
						<font color='red'>[${sessionScope.driverAuthenticationDispose}]</font>
				</s:if>
				</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkAuthentication_list">
			<dd class="m_carparkAuthentication_l"><a href="<%=menupath %>/carparkAuthentication_list">
				车位认证
				<s:if test="#session.carparkAuthenticationDispose!=null">
						<font color='red'>[${sessionScope.carparkAuthenticationDispose}]</font>
				</s:if>
				</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="berthShare_list">
			<dd class="m_berthShare_l"><a href="<%=menupath %>/berthShare_list">车位分享</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="systemStatistics_list">
			<dd class="m_systemStatistics_l"><a href="<%=menupath %>/systemStatistics_list">数据统计</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="userPoint_list">
			<dd class="m_userPoint_l"><a href="<%=menupath %>/userPoint_list">积分明细</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="userCredit_list">
			<dd class="m_userCredit_l"><a href="<%=menupath %>/userCredit_list">信誉明细</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="invitecode_list">
			<dd class="m_invitecode_l"><a href="<%=menupath %>/invitecode_list">邀请码</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="appVersion_list,appVersion_delete,appVersion_edit,appVersion_add">
			<dd class="m_appVersion_l"><a href="<%=menupath %>/appVersion_list">APP</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="appChannel_list,appChannel_delete,appChannel_edit,appChannel_add">
			<dd class="m_appChannel_l"><a href="<%=menupath %>/appChannel_list">APP渠道</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="appActivity_list">
			<dd class="m_appActivity_l"><a href="<%=menupath %>/appActivity_list">APP活动</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="notify_list">
			<dd class="m_notify_l"><a href="<%=menupath %>/notify_list">系统通知</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="defaultApply_list">
			<dd class="m_defaultApply_l"><a href="<%=menupath %>/defaultApply_list"  id="defaultApply_tips" >
			违约处理申请
				<s:if test="#session.defaultApplyDispose!=null">
						<font color='red'>[${sessionScope.defaultApplyDispose}]</font>
				</s:if>
					</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="feedback_list,feedback_delete">
			<dd class="m_feedback_l"><a href="<%=menupath %>/feedback_list">意见反馈</a></dd>
			</sec:authorize>
		</dl>
	</div>
	<div class="left2 l2">
		<dl class="over">
			<dt >停车场管理</dt>
			<sec:authorize ifAnyGranted="carpark_list">
			<dd class="m_carpark_l"><a href="<%=menupath %>/carpark_list">停车场</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carpark_list">
			<dd ><a href="<%=menupath %>/carpark_userList">用户新增停车场</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkBerthPolygon_list">
			<dd class="m_carparkBerthPolygon_l"><a href="<%=menupath %>/carparkBerthPolygon_list">停车场车位</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkEntrancePoint_list">
			<dd class="m_carparkEntrancePoint_l"><a href="<%=menupath %>/carparkEntrancePoint_list">停车场出入口</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkMarkerCategory_list">
			<dd class="m_carparkMarkerCategory_l"><a href="<%=menupath %>/carparkMarkerCategory_list">标注分类</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkMarkerPoint_list">
			<dd class="m_carparkMarkerPoint_l"><a href="<%=menupath %>/carparkMarkerPoint_list">停车场标注</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkFuzhuPolygon_list">
			<dd class="m_carparkFuzhuPolygon_l"><a href="<%=menupath %>/carparkFuzhuPolygon_list">停车场辅助图层</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkCityPolyline_list">
			<dd class="m_carparkCityPolyline_l"><a href="<%=menupath %>/carparkCityPolyline_list">停车场外部道路</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkRoadPolyline_list">
			<dd class="m_carparkRoadPolyline_l"><a href="<%=menupath %>/carparkRoadPolyline_list">停车场内部道路</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkNavigationPolyline_list">
			<dd class="m_carparkNavigationPolyline_l"><a href="<%=menupath %>/carparkNavigationPolyline_list">停车场导航路线</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkNavigationPoint_list">
			<dd class="m_carparkNavigationPoint_l"><a href="<%=menupath %>/carparkNavigationPoint_list">跨楼层导航点</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkModelPolygon_list">
			<dd class="m_carparkModelPolygon_l"><a href="<%=menupath %>/carparkModelPolygon_list">停车场高度模型图层</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkBackgroundPolygon_list">
			<dd class="m_carparkBackgroundPolygon_l"><a href="<%=menupath %>/carparkBackgroundPolygon_list">停车场背景底图</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkShopPolygon_list">
			<dd class="m_carparkShopPolygon_l"><a href="<%=menupath %>/carparkShopPolygon_list">停车场商场房间</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkFloor_list">
			<dd class="m_carparkFloor_l"><a href="<%=menupath %>/carparkFloor_list">停车场楼层</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="carparkCategory_list">
			<dd class="m_carparkCategory_l"><a href="<%=menupath %>/carparkCategory_list">停车场分类</a></dd>
			</sec:authorize>
		</dl>
	</div>
	<div class="left2 l3">
		<dl class="over">
			<dt>系统管理</dt>
			<sec:authorize ifAnyGranted="manager_list,manager_delete,manager_edit,manager_add">
			<dd class="m_manager_l"><a href="<%=menupath %>/manager_list">管理用户</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="role_list,role_delete,role_edit,role_add">
			<dd class="m_role_l"><a href="<%=menupath %>/role_list">角色</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="department_list">
			<dd class="m_department_l"><a href="<%=menupath %>/department_list">部门</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="resources_list">
			<dd class="m_resources_l"><a href="<%=menupath %>/resources_list">资源权限</a></dd>
			</sec:authorize>
			<sec:authorize ifAnyGranted="optLogs_list">
			<dd class="m_log_l"><a href="<%=menupath %>/optLogs_list">操作日志</a></dd>
			</sec:authorize>
		</dl>	
	</div>
	<div class="left2 l4">
		<dl class="over">
			<dt>地锁管理</dt>
			<dd class="m_groundlock_l"><a href="<%=menupath %>/groundlock_list">地锁</a></dd>
			<dd class="m_runningRecords_l"><a href="<%=menupath %>/runningRecords_list">运行记录</a></dd>
			<dd class="m_dynamicRecords_l"><a href="<%=menupath %>/dynamicRecords_list">动态记录</a></dd>
			<dd class="m_maintenanceRecords_l"><a href="<%=menupath %>/maintenanceRecords_list">维修申报</a></dd>
		</dl>	
	</div>
</div>

