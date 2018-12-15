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
	<title>停车场车位|泊泊停车</title>
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
 

 
 
</script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场车位</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>停车场车位添加</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/carparkBerthPolygon_add" method="post" enctype="multipart/form-data"  id="saveForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
					        <table width="100%" border="0" class="adduser_tab">
					        <tr>
						            <td align="right" width="110px">停车场： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" readonly="readonly" data-rule="required;" id="_carparkname" 
						            	 	/>
						            	<input type="hidden" class="mes_form1"   id="_carparkid" name="carparkBerthPolygon.carpark.carparkid"/>
						            	 <a href="javascript:void(0)" onclick="add();" style="position: relative;top:10px">
					        				<img src="<%=path %>/page/images/jia.png" height="20px" alt="" />
					        			 </a>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px">名称： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" id="name" name="carparkBerthPolygon.name" value="${carparkBerthPolygon.name}"
						            	 	/>
						            </td>
					            </tr>
					            
					            <tr>
					            	<td align="right" width="110px">楼层ID： </td>
						            <td align="left">
						            	<select name="carparkBerthPolygon.floorid" id="_floorid">
						            		<option>---请先选择一个停车场--</option>
						            	</select>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">背景色： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.color}"  data-rule="required,integer;" class="mes_form1" name="carparkBerthPolygon.color"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">边框色： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.bordercolor}" data-rule="required,integer;" class="mes_form1" name="carparkBerthPolygon.bordercolor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">点击后背景色： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.clickColor}" data-rule="required,integer;" class="mes_form1" name="carparkBerthPolygon.clickColor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">点击后边框色： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.clickBordercolor}"data-rule="required,integer;" class="mes_form1" name="carparkBerthPolygon.clickBordercolor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">宽度： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.width}"   class="mes_form1" name="carparkBerthPolygon.width"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">高度： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.height}" class="mes_form1"  name="carparkBerthPolygon.height"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体颜色： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.fontColor}" data-rule="integer;" class="mes_form1" name="carparkBerthPolygon.fontColor"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体大小： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.fontSize}"    class="mes_form1" name="carparkBerthPolygon.fontSize"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体加粗： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.fontWeight}" data-rule="integer;" class="mes_form1" name="carparkBerthPolygon.fontWeight"/>
						            </td>
					            </tr>
					            <%--<tr>
					            	<td align="right" width="110px">图元几何中心点： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.geometryCentroid}" class="mes_form1" name="carparkBerthPolygon.geometryCentroid"/>
						            </td>
					            </tr>
					            --%><tr>
					            	<td align="right" width="110px">空间经纬度信息： </td>
						            <td align="left">
							            <textarea  rows="7" cols="40" name="carparkBerthPolygon.geometry">${carparkBerthPolygon.geometry}</textarea>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">字体倾斜： </td>
						            <td align="left">
						            	<input value="${carparkBerthPolygon.fontItalic}" data-rule="integer;" class="mes_form1" name="carparkBerthPolygon.fontItalic"/>
						            </td>
					            </tr>
					            
					        	<tr>
						            <td align="right" width="110px">备注： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="memo" name="carparkBerthPolygon.memo" value="${carparkBerthPolygon.memo}"
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
		<form action="<%=path %>/carpark_getCarparkJson" method="post" id="searchForm" >
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
 function close(carparkid,name){
	 layer.close(page);
	 $("#_carparkname").val(name);
	$("#_carparkid").val(carparkid);
	 getFlooridByCarparkId(carparkid);
 }

//通过停车场id获得楼层
function getFlooridByCarparkId(carparkid){
	$.ajax({
		url:host+"/carparkFloor_getFloorByCarparkId",
		data:{"carparkFloor.carpark.carparkid":carparkid},
		type:"post",
		success:function(data){
			var floor = $.parseJSON(data);
			var html="";
			if(floor.length==0){
				$("#_floorid").html("<option value='' >---无---</option>");
			}else{
				$.each(floor,function(i){
					html+="<option value="+floor[i].id+">"+floor[i].name+"</option>";
				});
				$("#_floorid").html(html);
			}
		}
		
	})
}

//搜索停车场数据

function search(){
	 $("#searchForm").form('submit',{
		 	url:host+"/carpark_getCarparkJson",
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
	$(".m_carparkBerthPolygon_l").attr("class","lhover");
	});
</script>
</body>
</html>