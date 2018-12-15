<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String toppath = request.getContextPath();
%>



<div class="topbox">
	<div class="top-left" style="width:40%">
		<div class="logoimg"><a href="<%=toppath %>/main_index"><img src="<%=toppath %>/page/images/toplogo.png" alt=""/></a></div>
		<div class="yonghu">
        
		</div>
	</div>
	<div class="top-right">
		<div class="t-r-y">
			<ul>
				<li class="bz"><a href="javascript:void(0);">帮助</a></li>
				<li><a href="javascript:void(0);">关于</a></li>
				<li><a href="<%=toppath %>/j_spring_security_logout">退出</a></li>   
			</ul>
		</div>			
		<div class="t-r-e" id="boxssa">
			<dl>
			    <dt>
					<em><a href="javascript:void(0);"><img src="<%=toppath %>/page/images/ad.jpg" alt="" /></a></em>
					<i><a href="javascript:void(0);">欢迎你<s:property value="#session.loginManager.username"/></a></i>
				</dt>
			</dl>
		</div>
	</div>
</div>
