<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.parkbobo.utils.*" %>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String clienturl = Configuration.getInstance().getValue("clienturl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>停车场出入口|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/jquery.parser.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
<script>
var host='<%=path %>';
var page;
 function add(){
			  page = $.layer({
			    type: 1,
			    title: "选择停车场",
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
 
 function close(carparkid,name){
	 layer.close(page);
	 $("#_carparkname").val(name);
	$("#_carparkid").val(carparkid);
	 
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场出入口</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>停车场出入口修改</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/carparkEntrancePoint_update" method="post" enctype="multipart/form-data"  id="saveForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input name="carparkEntrancePoint.gid" type="hidden" value="${carparkEntrancePoint.gid}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        <tr>
						            <td align="right" width="110px">停车场： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" name="carparkEntrancePoint.carpark.name" readonly="readonly" data-rule="required;" value="${carparkEntrancePoint.carpark.name}" id="_carparkname" 
						            	 	/>
						            	<input type="hidden" class="mes_form1"  value="${carparkEntrancePoint.carpark.carparkid}" id="_carparkid" name="carparkEntrancePoint.carpark.carparkid"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">名称： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" id="name" name="carparkEntrancePoint.name" value="${carparkEntrancePoint.name}"
						            	 	/>
						            </td>
					            </tr>
					            
					            <tr>
					            	<td align="right" width="110px">楼层ID： </td>
						            <td align="left">
						            	<s:if test="floors.size==0">
						            		<select>
						            			<option value="">---无--</option>
						            		</select>
						            	</s:if>
						            	<s:else>
							            	<s:select list="floors" listValue="name" name="carparkEntrancePoint.floorid" listKey="floorid" theme="simple"></s:select>
						            	</s:else>
						            </td>
					            </tr>
					            <s:set value="#{0:'入口',1:'出口',2:'出入口'}" name="type"></s:set>
					            <tr>
					            	<td align="right" width="110px">类型： </td>
						            <td align="left">
						            	<s:select list="#type" listValue="value" theme="simple" listKey="key" name="carparkEntrancePoint.type"></s:select>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">高德经度： </td>
						            <td align="left">
						            	<input value="${carparkEntrancePoint.gaodeLongitude}"  class="mes_form1" name="carparkEntrancePoint.gaodeLongitude"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">高德纬度： </td>
						            <td align="left">
						            	<input value="${carparkEntrancePoint.gaodeLatitude}"   class="mes_form1" name="carparkEntrancePoint.gaodeLatitude"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">空间经纬度信息： </td>
						            <td align="left">
							            <textarea  rows="2" cols="30" name="carparkEntrancePoint.geometry"></textarea>
						            </td>
					            </tr>
					            
					        	<tr>
						            <td align="right" width="110px">备注： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="memo" name="carparkEntrancePoint.memo" value="${carparkEntrancePoint.memo}"
						            	 	/>
						            </td>
					            </tr>
					        </table>
					        
							<div class="tijiao">
					        	<input type="submit" value="保存" class="btn1" />
					        </div>
					         <div class="tijiao">
					        		  <a href="javascript:history.go(-1);" ><font color="gray">返回</font></a>
					        </div>
						</form>   	
					</div>
					
					
				</div>
			</div>
		</div>
	</div>
</div>
<div>

</div>
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
<div  id="carpark">
<table>
	<tr>
		<td>
		
	<div class="listbt">
		<form action="" method="post" id="searchForm" >
			<div class="l-b-b">停车场：
				 <input type="text" value="" name="carpark.name"/>
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
				<th >停车场</th>
				<th >类型</th>
				
			</tr>
		</thead>
		<tbody id="carparkContent">
			
		</tbody>
	</table>
</div>
<script type="text/javascript">

function search(){
	 $("#searchForm").form('submit',{
		 	url:host+"/carpark_getCarparkJson",
		 	onSubmit:function(){
		 	},
		 	success:function(data){
		 		var carparks = $.parseJSON(data);
		 		var html;
		 		if(carparks.status==false){
		 			html = "没有数据";
		 		}else{
			 		$.each(carparks.data,function(i){
			 			html +="<tr>";
							html +="<td><a href='javascript:close("+carparks.data[i].id+",\""+carparks.data[i].name+"\")' >"+carparks.data[i].name+"</a></td>";
							html +="<td>"+carparks.data[i].type+"</td>";
						html +="</tr>";
			 		});
		 		
		 		}
		 		$("#carparkContent").html(html);
		 	}
		 
	 });
	 
 }
$(document).ready(function(){
	$(".m_carparkEntrancePoint_l").attr("class","lhover");
	});
</script>
</body>
</html>