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
	<title>停车场|泊泊停车</title>
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


<script type="text/javascript">

var host = '<%=path%>/';
		
		function openReview (id,title){
		$.layer({
		    type: 2,
		    shadeClose: true,
		    title: title+"评论",
		    closeBtn: [0, true],
		    shade: [0.8, '#000'],
		    border: [0],
		    offset: ['20px',''],
		    area: ['600px', ($(window).height() - 50) +'px'],
		    iframe: {src: host+"carparkReview_list?id="+id}
		}); 
				
		}
		
	

</script>
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">系统通知</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			 <form action="<%=path %>/notify_selectCarpark" method="post" id="searchForm">
				<input type="hidden" value="${carpark.name}" name="carpark.name"/>
				<input type="hidden" value="" name="page" id="page"/>
				<input type="hidden" name="notify.title" value="${notify.title}" />
				<textarea style="display: none" name="notify.content" >${notify.content}</textarea>
				<input type="hidden" name="notifyType" value="${notifyType}"/>
				<input type="hidden" name="notifysendMode" value="${notifysendMode}"/>
			</form>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/notify_selectCarpark" method="post" >
				    	<div class="l-b-b">名称：
				        	<input type="text" value="${carpark.name}" name="carpark.name"/>
					        <input type="hidden" name="notify.title" value="${notify.title}" />
							<textarea style="display: none" name="notify.content" >${notify.content}</textarea>
							<input type="hidden" name="notifyType" value="${notifyType}"/>
							<input type="hidden" name="notifysendMode" value="${notifysendMode}"/>
				        </div>	
				         <div class="l-b-n" id="xrig">	
				         	
							<input type="submit" value="搜 索"  class="btn1"/>
							<input type="button" value="返回发送通知" onclick="toAddNotify()"  class="btn1"/>
				         </div>
				    </form>     
				</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<th width="88" class=""><a href="javascript:bulkadd(0);" >添加</a></th>
								<th class="tabx"><i></i></th>
								<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">名称</td>
			            		<td class="">类型</td>
			            		<td class="">车位数</td>
			            		<td class="">市</td>
			            		<td class="">区</td>
			            		<td class="">地址</td>
			            	</tr>
			            	<s:iterator value="carparkPage.list" var="c">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${c.carparkid}" id="checkbox_${c.carparkid}" class="checkItem"/></td>
			            		<td class="">${c.name}</td>
			            		<td class="">${c.carparkCategory.name}</td>
			            		<td class="">${c.totalBerth}</td>
			            		<td class="">${c.city}</td>
			            		<td class="">${c.county}</td>
			            		<td class="">${c.address}</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${carparkPage.allRow}</i>条记录，当前第 ${carparkPage.currentPage}/${carparkPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{carparkPage.totalPage > 10}">
				          	<s:if test="%{carparkPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{carparkPage.totalPage > (carparkPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="carparkPage.currentPage-5" />
								   	<s:param name="last" value="carparkPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{carparkPage.currentPage==(#count.index+carparkPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="carparkPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="carparkPage.currentPage-5" />
								   	<s:param name="last" value="carparkPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{carparkPage.currentPage==(#count.index+carparkPage.currentPage-5)}">
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
								   	<s:if test="%{carparkPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="carparkPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{carparkPage.totalPage>1&&10>=carparkPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="carparkPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{carparkPage.currentPage==(#count.index+1)}">
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
	
	<form action="<%=path %>/${notifysendMode==0?'notify_add':'notify_toSms'}" method="post" id="tempData">
		<input type="hidden" name="method" value="add_carparksession"/>
		<input type="hidden" name="ids" id="boxid" value=""/>
		<input type="hidden" name="notify.title" value="${notify.title}" />
		<textarea style="display: none" name="notify.content" >${notify.content}</textarea>
		<input type="hidden" name="notifyType" value="${notifyType}"/>
		<input type="hidden" name="notifysendMode" value="${notifysendMode}"/>
	</form>
</div>
<script type="text/javascript">
var the_host = "<%=path%>/";
$(document).ready(function(){
	$(".m_notify_l").attr("class","lhover");
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
});

function bulkadd(ids){
	
	if(ids == 0){
		var ids = "";
		$("[name='checkbox']:checked").each(function(){
			ids += $(this).val()+",";
		});
		if(ids.length>0){
			ids=ids.substr(0,ids.length-1);
			$("#boxid").val(ids);
			$("#tempData").submit();
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}
	
}
function toAddNotify(){
	$("#tempData").submit();
}
</script>
</body>
</html>