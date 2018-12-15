<%@ page language="java" import="java.util.*,java.io.*,java.sql.*,java.text.*,java.net.URLDecoder.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
Connection con = null;
try {
    Class.forName("org.postgresql.Driver");
    con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bobopark", "bobopark", "bobopark");
}catch (ClassNotFoundException e) {
    e.printStackTrace();
}catch (SQLException e) {
    e.printStackTrace();
}
Statement stmt=con.createStatement();
String sql = "select online_status,sim_num,battery_power,sim_intensity from lq_groundlock where groundlockid = '32311277329152'";
ResultSet rs = stmt.executeQuery(sql);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>地锁|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script src="<%=path %>/page/plugins/layer/layer.js"></script>
</head>
<body>
<div class="listnr" >
	<br/><br/><br/><br/><br/><br/><br/>
					<table width="80%" class="tab2" id="tab" >
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="">网络状态</td>
			            		<td class="">SIM卡号</td>
			            		<td class="">电量(%)</td>
			            		<td class="">信号(dBm)</td>
			            		<td>操作</td>
			            	</tr>
		            		<tr class="">
		            			<%
						  		while(rs.next()){
						  		%>
						  			<td>
						  			<%
						  			int s = rs.getInt("online_status");
						  			if(s==0)
						  			{
						  				out.print("在线");
						  			}
						  			else if(s==1)
						  			{
						  				out.print("掉线");
						  			}
						  			else if(s==2)
						  			{
						  				out.print("休眠");
						  			}
						  			 %>
						  			</td>
						  			<td><%=rs.getString("sim_num") %></td>
						  			<td><%=rs.getInt("battery_power") %></td>
						  			<td><%=rs.getString("sim_intensity") %></td>
						  			<td class="">
			            			<a href="javascript:void(0);" onclick="sendGetSignal('32311277329152')" >刷新信号</a> 
			            		</td>
						  		<%
						  		}
						  		%>
			            		
			            	</tr>
			            </tbody>
					</table>
					</div>
<script type="text/javascript">
var the_host = "<%=path%>/";


$(document).ready(function(){
	//加载扩展模块
	layer.config({
	    extend: the_host + 'page/plugins/layer/extend/layer.ext.js'
	});
});


  
 /**
  * 获取SIM卡信号强度
  */
function sendGetSignal(groundlockid)
{
	layer.confirm('确定要获取SIM卡信号强度吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "groundlockControl_sendGetSignal" ,
			data: {
				'groundlockid': encodeURI(groundlockid)
			},
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('信号获取成功', {icon: 6});
					setTimeout(function(){
					    location.reload();
					}, 500);
				}
				else
				{
					layer.msg('信号获取失败', function(){});
				}
			},
			error: function (XMLResponse) {
				layer.close(index);
			}
		});
	});
}

</script>
</body>
</html>