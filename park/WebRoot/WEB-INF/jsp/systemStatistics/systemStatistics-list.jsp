<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>用户管理|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<style type="">
	
	#search_auto{border:1px solid #817FB2; position:absolute; width: 180px; height: 100px; left: 767px; top: 83px;}
	#search_auto ul li{height: 20px; position: relative; top: 1px; border:1px solid white;}
	</style>
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/layer1.85/layer.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>


</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">数据统计</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
<!--			 <form action="<%=path %>/users_list" method="post" id="searchForm">-->
<!--				<input type="hidden"  name="page" id="page" value="${page}"/>-->
<!--				<input type="hidden" value="${user.username}" name="user.username"/>-->
<!--				<input type="hidden" value="${user.carNumber}" name="user.carNumber"/>-->
<!--				<input type="hidden" id="loginOrder" value="${loginOrder}" name="loginOrder"/>-->
<!--				<input type="hidden" id="registerOrder" value="${registerOrder}" name="registerOrder"/>-->
<!--				<input type="hidden" id="orderPrecedence" name="orderPrecedence" value="${orderPrecedence}"/>-->
<!--				<input type="hidden" value="${user.invitecode}" name="user.invitecode"/>-->
<!--			</form>-->
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/systemStatistics_list" method="post" id="subForm">
				    	<input type="hidden"  id="path" value="<%=path %>"/>
				    	<div class="l-b-b">起始时间：
				    		<input type="text" name="statistics.startTime" value="<s:date name="statistics.startTime" format="yyyy-MM-dd HH:mm:ss" />" id="startTime"
								onclick="$.calendar({format:'yyyy-MM-dd HH:mm:ss' });" readonly="readonly" style="background:url(page/plugins/lhgcalendar/iconDate.gif) center right no-repeat #f7f7f7;cursor:pointer;padding:2px 2px 2px 2px;vertical-align:bottom;border: 1px solid #CCCCCC;width: 150px;height: 28px;line-height: 28px;"/>
								
<!--				        	<input type="text" id="startTime" value="${statistics.startTime}" name="statistics.startTime"/>-->
				        </div>	
				        <div class="l-b-b">终止时间：
				        <input type="text" name="statistics.endTime" value="<s:date name="statistics.endTime" format="yyyy-MM-dd HH:mm:ss" />" id="endTime"
								onclick="$.calendar({format:'yyyy-MM-dd HH:mm:ss' });" readonly="readonly" style="background:url(page/plugins/lhgcalendar/iconDate.gif) center right no-repeat #f7f7f7;cursor:pointer;padding:2px 2px 2px 2px;vertical-align:bottom;border: 1px solid #CCCCCC;width: 150px;height: 28px;line-height: 28px;"/>
<!--				        		<input type="text" id="endTime" value="${statistics.endTime}" name="statistics.endTime"/>-->
				        </div>
				        <div class="l-b-b">停车场名称：
				        	<input type="text" id="carparkname" value="${statistics.carparkname}" name="statistics.carparkname" style="width: 180px;" autocomplete="off"/>
				        </div>
				        <c:if test="${loginManager.area=='总部'}">
					         <div class="l-b-b">城市：
					        	<input type="text" id="area" value="${statistics.area}" name="statistics.area" style="width: 180px;" />
					        </div>
				        </c:if>
				        <div id="search_auto">
				        	<ul>
				        	</ul>
				        </div>
				         <div class="l-b-n" id="xrig">
							<input type="button" onclick="searchForm()" value="搜 索"  class="btn1"/>
				         </div>
				    </form>     
				</div>
				<div class="listnr" >
					<table width="100%" border="0" style="background: #f0f5f7;">
						<thead>
							<tr align="center">
								<td><font size="4px"><strong>总交易额：${statistics.totalMoney}元</strong></font></td>
								<td><font size="4px"><strong>总订单数：${statistics.totalCarNum}</strong></font></td>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="">停车场ID</td>
			            		<td class="">停车场名字</td>
			            		<td class="">城市</td>
			            		<td class="">总订单数</td>
			            		<td class="">总出租车位</td>
			            		<td class="">总出租小时数</td>
			            		<td class="">总停车费</td>
			            		<td class="">平台收益</td>
			            		<td class="">物业收益</td>
			            		<td class="">车位主收益</td>
			            	</tr>
			            	<s:iterator value="carList" var="c">
		            		<tr class="">
			            		<td class="">${c.carparkid}</td>
			            		<td class="">${c.carparkname}</td>
			            		<td class="">${c.city}</td>
			            		<td class="">${c.carparkTotalOrder}</td>
			            		<td class="">${c.carparkBerthShareNum}</td>
			            		<td class="">${c.carparkTotalTimeHours}</td>
			            		<td class="">${c.carparkMoney}</td>
			            		<td class="">${c.carparkCompanyRevenue}</td>
			            		<td class="">${c.carparkPropertyRevenue}</td>
			            		<td class="">${c.carparkOwnerRevenue}</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
var the_host = "<%=path%>/";
$(document).ready(function(){
	$("#search_auto").hide();
	var liEle = $("#subForm ul li");
	liEle.hover(
	  function () {
	    $(this).css("backgroundColor","#C9C9C9");
	  },
	  function () {
	    $(this).css("backgroundColor","white");
	  }
	);
		
        $("#carparkname").keyup(function(){
        	var path = $("#path").val();
   			var inputCarparkname = $("#carparkname").val();
   			$.post(path+"/systemStatistics_carparkname",{'inputCarparkname':inputCarparkname},function(data){
   				data = JSON.parse(data);
   				if(data!="null" && data!=""){
	   				var c = "";
	   				for(var i=0;i<data.length;i++){
	   					c += "<li>"+data[i]+"</li>";
	   				}
	   				$("#subForm ul").html(c);
	   				$("#search_auto").show();
		   			liEle = $("#subForm ul li");
					liEle.hover(
					  function () {
					    $(this).css("backgroundColor","#C9C9C9");
					  },
					  function () {
					    $(this).css("backgroundColor","white");
					  }
					);
					liEle.click(function(){
			              $("#carparkname").val($(this).html());
			              $("#search_auto").hide();
			        });
   				}
   				
   			});
		});
		liEle.click(function(){
              $("#carparkname").val($(this).html());
              $("#search_auto").hide();
        });
	
	$(".m_users_l").attr("class","lhover");
	/**
	 * 分页
	 */
	$(".sabrosus a").each(function(){  
        $(this).click(function(){ 
        	$('#page').val($(this).attr("rel"));
        	$('#searchForm').submit();
        });  
    });
	/**
	 * 全选操作
	 */
	$("#checkAll").click(function() {
		$('input[name="checkbox"]').attr("checked",this.checked); 
	});
    $("input[name='checkbox']").click(function(){
		$("#checkAll").attr("checked",$("input[name='checkbox']").length == $("input[name='checkbox']:checked").length ? true : false);
    });
});

function bulkBalance(){
	var ids = getSelectId();
	if(ids!=null){
		var url = the_host+"users_updateBalance?ids="+ids;
		location.href=url;
	}
}
function bulkPoint(){
	var ids = getSelectId();
	if(ids!=null){
		var url = the_host+"users_updatePoint?ids="+ids;
		location.href=url;
	}
}
function bulkCredit(){
	var ids = getSelectId();
	if(ids!=null){
		var url = the_host+"users_updateCredit?ids="+ids;
		location.href=url;
	}
}


function getSelectId(){
	var ids = "";
		$("[name='checkbox']:checked").each(function(){
			ids += $(this).val()+",";
		});
		if(ids.length>0){
			return ids;
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
			return null;
		}
	
}
/**
 * 删除
 */
function bulkDelete(ids){
	if(ids == 0){
		var ids = "";
		$("[name='checkbox']:checked").each(function(){
			ids += $(this).val()+",";
		});
		if(ids.length>0){
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"users_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"users_delete?ids="+ids;
		});
	}
		
}


	function order(ordercol,d_a){
		//$("#registerOrder,#loginOrder").val("");
		$("#orderPrecedence").val(ordercol);
		$("#page").val(0);
		$("#"+ordercol).val(d_a);
		$("#searchForm").submit();
	}
	
	function searchForm(){
		$("#subForm").submit();
	}
</script>
<script type="text/javascript">

</script>
</body>
</html>