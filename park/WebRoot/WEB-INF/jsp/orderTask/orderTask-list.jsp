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
	<title>预约停车泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">预约停车</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<form action="<%=path %>/orderTask_list" method="post" id="searchForm">
				<input name="page" id="_page" type="hidden"/>
				<input name="orderTask.carparkname" type="hidden" value="${orderTask.carparkname}"/>
				<input type="hidden" value="${orderTask.tasktype}" name="orderTask.tasktype"  />
				<input type="hidden" value="${orderTask.taskStatus}" name="orderTask.taskStatus"  />
				<input name="orderTask.users.username" type="hidden" value="${orderTask.users.username}"/>
			</form>
			<s:set name="tasktype" value="#{ 0:'面向停车场预约',1:'面向车位预约'}" />
			<s:set name="taskStatus" value="#{-1:'已取消',0:'待预约',1:'成功',2:'失败'}" />
			<div class="listbox">
			<div class="listbt">
				<form action="<%=path %>/orderTask_list" method="post" >
				<div class="l-b-b">停车场名称：
					<input name="orderTask.carparkname" type="text" value="${orderTask.carparkname}"/>
				</div>
				<div class="l-b-b">用户名：
					<input name="orderTask.users.username" type="text" value="${orderTask.users.username}"/>
				</div>
				 <div class="l-b-b">预约类型:</div>
				         <div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${orderTask.tasktype}" name="orderTask.tasktype"  />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#tasktype" var="ac">
							    	<li rel="${key}">${value}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				         </div>	
				          <div class="l-b-b">预约状态:</div>
				         <div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${orderTask.taskStatus}" name="orderTask.taskStatus"  />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#taskStatus" var="ac">
							    	<li rel="${key}">${value}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				         </div>	
					         <div class="l-b-n" id="xrig">	
								<input type="submit" value="搜 索"  class="btn1"/>
					         </div>
				</form>
			</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
							<sec:authorize ifAnyGranted="orderTask_delete">
								<th width="88" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
							</sec:authorize>
							<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            	<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td>用户名</td>
			            		<td class="">停车场名称</td>
			            		<td class="">车位号</td>
			            		<td>预约类型</td>
			            		<td class="">车牌号</td>
			            		<td class="">手机号</td>
			            		<td class="">预约状态</td>
			            		<td class="">起始时间</td>
			            		<td class="">结束时间</td>
			            		<td class="">预约提交时间</td>
			            		<td class="">预约结束时间</td>
			            		
			            		<td align="center">操作</td>
			            	</tr>
			            	<s:iterator value="page.list" var="d">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${d.taskid}" id="checkbox_${d.taskid}" class="checkItem"/></td>
			            		<td class="">
				            		<a href="<%=path %>/users_details?ids=${d.users.userid}">
				            			${d.users.username}
				            		</a>
			            		</td>
			            		<td >${d.carparkname}</td>
				            	<td >
			            			<s:if test="berthShare!=null">
				            			<a href="<%=path %>/berthShare_check?berthShare.shareid=${d.berthShare.shareid}" >
				            				 ${d.berthShare.berthnum}
				            			</a>
			            			</s:if>
				            	</td>
			            		<td class="">
			            			<s:iterator value="#tasktype" var="t">
			            				<s:if test="tasktype==key">
			            					${t.value}
			            				</s:if>
			            			</s:iterator>
			            		</td>
			            		
			            		<td class="">
			            			${d.carNumber}
			            		</td>
			            		<td class="">
			            			${d.telephone}
			            		</td>
			            		<td class="">
			            		<s:iterator value="#taskStatus" var="t">
			            				<s:if test="taskStatus==key">
			            					${t.value}
			            				</s:if>
			            			</s:iterator>
			            		</td>
			            		<td class="">
			            			<long_date:long_date datelong="${d.startTime}"/>
			            		</td>
			            		<td class="">
			            			<long_date:long_date datelong="${d.endTime}"/>
			            		</td>
			            		<td class="">
			            			<long_date:long_date datelong="${d.posttime}"/>
			            		</td>
			            		<td class="">
			            			<long_date:long_date datelong="${d.finishtime}"/>
			            		</td>
			            		<td align="center">
			            		<sec:authorize ifAnyGranted="orderTask_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${d.taskid}');">删除</a>
			            		</sec:authorize>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i><s:property value="page.allRow"/></i>条记录，当前第 <s:property value="page.currentPage"/>/<s:property value="page.totalPage"/>页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{page.totalPage > 10}">
				          	<s:if test="%{page.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{page.totalPage > (page.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="page.currentPage-5" />
								   	<s:param name="last" value="page.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{page.currentPage==(#count.index+page.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="page.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="page.currentPage-5" />
								   	<s:param name="last" value="page.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{page.currentPage==(#count.index+page.currentPage-5)}">
									   		<span class="current"><s:property/></span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
				          		</s:else>
				          	</s:if>
				          	<s:else>
				          		<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="10" />
							   	<s:iterator status="count">
								   	<s:if test="%{page.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="page.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{page.totalPage>1&&10>=page.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="page.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{page.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
							</s:bean>
				        </s:elseif>
			       		</div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var the_host = "<%=path%>/";


$(document).ready(function(){
	$(".m_orderTask_l").attr("class","lhover");
	/**
	 * 分页
	 */
	$(".sabrosus a").each(function(){  
        $(this).click(function(){ 
        	$('#_page').val($(this).attr("rel"));
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
			ids=ids.substr(0,ids.length-1);
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"orderTask_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"orderTask_delete?ids="+ids;
		});
	}
		
}
  /*** 自定义select***/
    $('.son_ul').hide(); //初始ul隐藏
	$('.select_box span').hover(function(){ //鼠标移动函数
		$(this).parent().find('ul.son_ul').slideDown();  //找到ul.son_ul显示
		$(this).parent().find('li').hover(function(){$(this).addClass('hover')},function(){$(this).removeClass('hover')}); //li的hover效果
		$(this).parent().hover(function(){},
			function(){
				$(this).parent().find("ul.son_ul").slideUp(); 
			}
		);
		},function(){}
	);
	$('ul.son_ul li').click(function(){
		$(this).parents('li').find('span').html($(this).html());
		if($(this).attr("rel") == ""){
			$(this).parents('li').find('input').val("");
		}else{
			$(this).parents('li').find('input').val($(this).attr("rel"));
		}
		$(this).parents('li').find('ul').slideUp();
	});
	$('ul.son_ul li').each(function(){
		if($(this).parents('li').find('input').val() == $(this).attr("rel")){
			$(this).parents('li').find('span').html($(this).html());
		}
	});
</script>
</body>
</html>