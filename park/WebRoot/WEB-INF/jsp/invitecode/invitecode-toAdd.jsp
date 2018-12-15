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
	<title>添加邀请码|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/layer1.85/layer.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
		<script type="text/javascript" src="<%=path%>/page/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/jquery.parser.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<style>
	#carpark{
		font-size: 15px;
		display: none;
	}
	#carpark thead{
		background:#EDF6FA;
		
	}
	#carpark thead th{
		font-weight:bold;
		width:210px;
		 height:30px;
		 text-align: center;
	}
	#carpark tbody td{
		 text-align: center;
	}
</style>
<script>
var host='<%=path %>';
var page;
 function add(){
			  page = $.layer({
			    type: 1,
			    title: "选择用户",
			    area: ['420px', '400px'],
			    border: [0], //去掉默认边框
			    shade: [1], //去掉遮罩
			    closeBtn: [0, true], //去掉默认关闭按钮
			    shift: 'left', //从左动画弹出
			    page: {
			        dom:'#carpark'
			    }
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">邀请码</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>添加</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/invitecode_add" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">选择用户： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" readonly="readonly" data-rule="required;" id="_carparkname" />
						            	<input type="hidden" class="mes_form1"   id="_carparkid" name="invitecode.users.userid"/>
						            	 <a href="javascript:void(0)" onclick="add();" style="position: relative;top:10px">
					        				<img src="<%=path %>/page/images/jia.png" height="20px" alt="" />
					        			 </a>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">邀请码： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="name" name="invitecode.invitecode"  value="${invitecode.invitecode}"
						            	 	data-rule="邀请码:required; length[~50, true];remote[<%=path %>/invitecode_checkcode]"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px" valign="top">详情： </td>
						            <td align="left">
										<textarea name="invitecode.description"  class="mes_form1" data-rule="详情:required;length[~300]" style="width: 250px; height: 100px" id="description">${invitecode.description}</textarea>
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
<div  id="carpark">
<table>
	<tr>
		<td>
		
	<div class="listbt">
		<form action="" method="post" id="searchForm" >
			<div class="l-b-b">用户名：
				 <input type="text" value="" name="user.username"/>
			</div>	
			 <div class="l-b-n" id="xrig">	
				<input type="button" onclick="search()" value="搜 索"  class="btn1"/>
			</div>
		</form>
	</div>
	</td>
	</tr>
</table>

 	<table  width="100%" border="0" >
		<thead >
			<tr style="" >
				<th >用户名</th>
				<th >昵称</th>
				<th >姓名</th>
				
			</tr>
		</thead>
		<tbody id="carparkContent">
			
		</tbody>
	</table>
</div>
<script type="text/javascript">
 function close(carparkid,name){
	 layer.close(page);
	 $("#_carparkname").val(name);
	$("#_carparkid").val(carparkid);
 }


//搜索停车场数据

function search(){
	
	 $("#searchForm").form('submit',{
		 	url:host+"/users_getUserJson",
		 	onSubmit:function(){
		 	},
		 	success:function(data){
		 		var carparks = $.parseJSON(data);
		 		var html="";
		 		if(carparks.status==false){
		 			html = "没有数据";
		 		}else{
			 		$.each(carparks.data,function(i){
			 			html +="<tr>";
							html +="<td><a href='javascript:close(\""+carparks.data[i].id+"\",\""+carparks.data[i].name+"\")' >"+carparks.data[i].name+"</a></td>";
							html +="<td>"+carparks.data[i].nick+"</td>";
							html +="<td>"+carparks.data[i].real+"</td>";
						html +="</tr>";
			 		});
		 		
		 		}
		 		$("#carparkContent").html(html);
		 	}
		 
	 });
	 
 }
</script>

 
</body>
</html>