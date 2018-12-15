<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
<%@ page language="java" import="com.parkbobo.utils.*" %>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df.format(new Date());
String clienturl = Configuration.getInstance().getValue("clienturl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>违约处理申请|泊泊停车</title>
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
	openPhoto = function(url){
			 $.layer({
			    type: 1,
			    title: false,
			    area: ['420px', '500px'],
			    border: [0], //去掉默认边框
			    shade: [1], //去掉遮罩
			    closeBtn: [0, true], //去掉默认关闭按钮
			    shift: 'left', //从左动画弹出
			    page: {
			        html: '<div style="width:420px; height:500px; padding:20px; border:1px solid #ccc; background-color:#eee;"><p><img src='+url+'  width=420px height=400px/></p></div>'
			    }
			});
		}
</script>
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">违约处理申请</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<s:set value="#{0:'待审',1:'驳回',2:'成功',3:'协商中',4:'撤销'}"  name="status" ></s:set>
			<s:set value="#{0:'车位被恶意入驻',1:'无法进去停车',2:'超期停车',3:'其他'}"  name="dtype" ></s:set>
			<div class="listbox">
				<div class="listnr" >
				
				<div class="listbt">
				
				<%--
					分页表单				
				--%>
				<form action="<%=path %>/defaultApply_list" method="post" id="pageform">
					<input type="hidden" value="${defaultApply.users.username}" name = "defaultApply.users.username" />
					<input type="hidden" value="${defaultApply.type}" name="defaultApply.type"/>
					<input type="hidden" value="${defaultApply.status}" name="defaultApply.status"/>
					
					<input type="hidden" name="page" id="page"/>
				</form>
				<form action="<%=path %>/defaultApply_list" method="post" >
			 
				    	<div class="l-b-b">
				    	
				        <div class="l-b-b">用户名：
				        	<input type="text" value="${defaultApply.users.username}" name="defaultApply.users.username"/>
				        </div>	
				        <div class="l-b-b">状态:</div>	
				        <div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${defaultApply.status}"  name = "defaultApply.status" />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#status" var="ds">
							    		<li rel="${key}">${value}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				        </div>
				        <div class="l-b-b">类型:</div>	
				        <div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${defaultApply.type}"  name = "defaultApply.type" />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#dtype" var="ds">
							    		<li rel="${key}">${value}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				        </div>
				         </div>	
				         <div class="l-b-n" id="xrig">	
								<input type="submit" value="搜 索"  class="btn1"/>
					         </div>
				         </form>
				         </div>
				         
				         <table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
							<sec:authorize ifAnyGranted="defaultApply_delete">
								<th width="108" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">申述用户</td>
			            		<td class="">申述类型</td>
			            		<td class="">申述内容</td>
			            		<td class="">申述附件</td>
			            		<td class="">处理状态</td>
			            		<td>提交时间</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="page.list" var="d" >
		            		<tr class="">
		            			<td class="tbt1"><input type="checkbox" name="checkbox" value="${d.applyid}" id="checkbox_${d.applyid}" class="checkItem"/></td>
			            		<td class="">
			            			
			            		<a href="<%=path %>/users_details?ids=${d.users.userid}">${d.users.username}</a>	
			            			
			            			</td>
			            		<td class="">
			            			<s:iterator value="#dtype" var="dt">
			            				<s:if test="key==type">
			            					${value}
			            				</s:if>
			            			</s:iterator>
			            		
			            		</td>
			            		<td class="">${d.content}</td>
			            		<td class="">
			            			<c:if test="${d.attached!=null&&d.attached!=''}">
			            				<a href="javascript:openPhoto( '<%=clienturl %>${d.attached}' );"  >点击预览</a>
			            			</c:if>
			            		</td>
			            		<td class="">
									<s:iterator value="#status" var="type">
			            				
			            				
			            				<s:if test="key == status&&status==0">
			            					<font color=red>
				            					${value}
			            					</font>
			            				</s:if>
			            				<s:elseif test="key == status">
			            					${value}
			            				</s:elseif>
			            			</s:iterator>
			            			
			            			
			            			</td>
			            		<td> 
			            		<long_date:long_date datelong="${d.posttime}"/>
			            		</td>
			            		<td class="">
			            		<sec:authorize ifAnyGranted="defaultApply_delete">
			            			<a href="javascript:bulkDelete(${d.applyid});">删除</a>
			            		</sec:authorize>
			            		<sec:authorize ifAnyGranted="defaultApply_check">
			            			<a href="<%=path %>/defaultApply_view?defaultApply.applyid=${d.applyid}">审核</a>
			            		</sec:authorize>
			            			<a href="<%=path %>/berthOrder_view?berthOrder.berthorderid=${d.berthorderid}">查看订单</a>
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
				          		<a href="javascript:void(0)" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{page.totalPage > (page.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="page.currentPage-5" />
								   	<s:param name="last" value="page.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{page.currentPage==(#count.index+page.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0)" rel="<s:property/>"><s:property/></a>  
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
									   		<a href="javascript:void(0)" rel="<s:property/>"><s:property/></a>  
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
								   		<a href="javascript:void(0)" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0)" rel="<s:property value="page.totalPage"/>" title="尾页"> &raquo;</a>
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
								   		<a href="javascript:void(0)" rel="<s:property/>"><s:property/></a>  
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
	$(".m_defaultApply_l").attr("class","lhover");
	/**
	 * 分页
	 */
	$(".sabrosus a").each(function(){  
        $(this).click(function(){ 
        	$('#page').val($(this).attr("rel"));
        	$('#pageform').submit();
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
});
function reload(type){
	location.href=the_host+"defaultApply_list?event="+type;
	
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
			ids=ids.substr(0,ids.length-1);
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"defaultApply_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"defaultApply_delete?ids="+ids;
		});
	}
		
}

	
</script>
</body>
</html>