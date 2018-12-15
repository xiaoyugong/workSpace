<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html> 
<head> 
	<base href="<%=basePath%>">
    <meta charset="utf-8"/> 
    <style>
	#wrap{font-family: '微软雅黑'; padding: 15px 30px;}
    h3{font-weight: normal;padding: 0px; margin: 0px;}
    table{
      margin-top: 15px;
      border: 1px solid #ccc;
      border-collapse:collapse;
      font-size: 14px;
      width: 1000px;
      color: #444;
    }
    table td{padding:3px;}
     h3{font-size: 16px; margin: 0px; padding: 0px; }
    </style>
</head>
<body>
	<div id="wrap">
	    <h3>Job任务监控界面</h3>
	    <table border="1">
	    	<thead>
	    		<tr>
					<td id="taskname">JobName</td>
					<td id="map">Map Progress</td>
					<td id="reduce">Reduce Progress</td>
					<td>Status</td>
					<td>StartTime</td>
					<td>FinishTime</td>
	    		</tr>
	    		<tbody>
	    			
	    			<s:iterator id="list" value="jobInfosList">
	            		<tr>
	                		<td><s:property value="#list.jobName" /></td>
	               			<td><s:property value="#list.mapProgress" /></td>
	                		<td><s:property value="#list.redProgress" /></td>
	               			<td><s:property value="#list.runState" /></td>
	               			<td><s:property value="#list.jobstartTime" /></td>
	               			<td><s:property value="#list.jobfinishTime" /></td>
	            		</tr>
	       			</s:iterator>
	    	</tbody>
	    </table>
	 
    </div>
   
     <% 
    	boolean flag=Boolean.parseBoolean(request.getAttribute("flag").toString());
    	%>
    	<%
    	if(!flag){
    	%>
		<script type="text/javascript">
			delayURL("monitor",1500);
			function delayURL(url, time) {
   				  setTimeout("location.href='" + url + "'", time);
				}
  		 </script>
    <%
    }else{
    %>
    	<h3 id="finishword">全部任务已经完成</h3>
    <%
    }
     %>
</body>
</html>