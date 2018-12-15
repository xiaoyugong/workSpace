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
	<title>维修申报|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/layer/layer.js"></script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">地锁管理</a> » <a href="###">维修申报</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/maintenanceRecords_list" method="post" id="searchForm" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
				    	<div class="l-b-b">地锁MAC：
				        	<input type="text" value="${mac}" name="mac"  />
				        </div>
				         <div class="l-b-n" id="xrig">	
				         	<input type="hidden" value="${page}" name="page" id="page"/>
							<input type="submit" value="搜 索"  class="btn1"/>
				         </div>
				    </form>     
				</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<th width="108" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
								<th class="tabx "><i></i></th>
								<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">是否接报</td>
			            		<td class="">维修人员</td>
			            		<td class="">MAC地址</td>
			            		<td class="">停车场</td>
			            		<td class="">车位号</td>
			            		<td class="">故障描述</td>
			            		<td class="">申报时间</td>
			            		<td>操作</td>
			            	</tr>
			            	<s:iterator value="maintenanceRecordsPage.list" var="r">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${r.recordid}" id="checkbox_${r.recordid}" class="checkItem"/></td>
			            		<td class="">
			            		<s:if test="%{#r.isOperate==0}"><span style="color:#ff0000">否</span></s:if>
			            		<s:elseif test="%{#r.isOperate==1}">是</s:elseif>
			            		<s:else></s:else>
			            		</td>
								<td class="">${r.repairman}</td>
			            		<td class="">${r.groundlock.groundlockMac}</td>
			            		<td class="">${r.groundlock.carparkname}</td>
			            		<td class="">${r.groundlock.berthname}</td>
			            		<td class="">${r.faultCause}</td>
			            		<td class=""><long_date:long_date datelong="${r.posttime}" /></td>
			            		<td class="">
			            			<s:if test="%{#r.recordstatus==0}">
			            			<a href="javascript:void(0);" onclick="accept('${r.recordid}')">受理丨</a>
			            			</s:if>
			            			<s:elseif test="%{#r.recordstatus==1}">
			            			<a href="javascript:void(0);" onclick="accept_c('${r.recordid}',1)">已维修丨</a>
			            			</s:elseif>
			            			<s:else></s:else>
									<%--<a href="javascript:layer.msg('玩命开发中..', function(){});">删除</a>--%>
			            			<a href="javascript:void(0);" onclick="bulkDelete(${r.recordid});">删除</a> 
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${maintenanceRecordsPage.allRow}</i>条记录，当前第 ${maintenanceRecordsPage.currentPage}/${maintenanceRecordsPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{maintenanceRecordsPage.totalPage > 10}">
				          	<s:if test="%{maintenanceRecordsPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{maintenanceRecordsPage.totalPage > (maintenanceRecordsPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="maintenanceRecordsPage.currentPage-5" />
								   	<s:param name="last" value="maintenanceRecordsPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{maintenanceRecordsPage.currentPage==(#count.index+maintenanceRecordsPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="maintenanceRecordsPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="maintenanceRecordsPage.currentPage-5" />
								   	<s:param name="last" value="maintenanceRecordsPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{maintenanceRecordsPage.currentPage==(#count.index+maintenanceRecordsPage.currentPage-5)}">
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
								   	<s:if test="%{maintenanceRecordsPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="maintenanceRecordsPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{maintenanceRecordsPage.totalPage>1&&10>=maintenanceRecordsPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="maintenanceRecordsPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{maintenanceRecordsPage.currentPage==(#count.index+1)}">
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
<script type="text/javascript">
var the_host = "<%=path%>/";


$(document).ready(function(){
	$(".m_maintenanceRecords_l").attr("class","lhover");
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
	/*** 自定义select END***/
	
	//加载扩展模块
	layer.config({
	    extend: the_host + 'page/plugins/layer/extend/layer.ext.js'
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
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"maintenanceRecords_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"maintenanceRecords_delete?ids="+ids;
		});
	}
		
}
function accept(obj)
{
	layer.open({
	    type: 2,
	    title: '受理维修',
	    shadeClose: true,
	    shade: 0.5,
	    area: ['550px', '200px'],
	    content: the_host + 'maintenanceRecords_accept?recordid='+obj
	});
}
function accept_c(obj,num){
	$.post(the_host+'maintenanceRecords_accept',{'recordid':obj,'num':num},function(data){
		if(data.status==true){
			window.location.href=the_host+"maintenanceRecords_list";
		}
	});
}
</script>
</body>
</html>