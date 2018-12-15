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
	<title>充值记录|泊泊停车</title>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">充值记录</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<s:set value="#{1:'充值到余额',2:'从余额提现',3:'从余额支付',4:'退款到余额'}"  name="balanceType" ></s:set>
			
			<form action="<%=path %>/userBalance_list" method="post" id="searchForm">
				<input type="hidden" value="${userBalance.event}" name = "userBalance.event" />
				<input type="hidden" name="page" id="_page"/>
				<input type="hidden" name="userBalance.users.username" value="${userBalance.users.username}"/>
			</form>
			
			<div class="listbox">
		
				         
				<div class="listnr" >
				<div class="listbt">
				<form action="<%=path %>/userBalance_list" method="post" >
				<div class="l-b-b">用户名：
					<input type="text" name="userBalance.users.username" value="${ userBalance.users.username}"/>
				</div>
			 <div class="l-b-b">状态：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${userBalance.event}" name = "userBalance.event" />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#balanceType" var="ac">
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
								<sec:authorize ifAnyGranted="userBalance_delete">
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
			            		<td class="">用户名</td>
			            		<td class="">姓名</td>
			            		<td class="">用户昵称</td>
			            		<td class="">金额</td>
			            		<td class="">当前余额</td>
			            		<td>支付宝流水号</td>
			            		<td>类型</td>
			            		<td>描述</td>
			            		<td class="">记录时间</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="userBalancePage.list" var="d">
		            		<tr class="">
		            			<td class="tbt1"><input type="checkbox" name="checkbox" value="${d.logid}" id="checkbox_${d.logid}" class="checkItem"/></td>
			            		<td class="">${d.users.username}</td>
			            		<td class="">${d.users.realname}</td>
			            		<td class="">${d.users.nickname}</td>
			            		<td class="">${d.amount/100}</td>
			            		<td class="">${d.amountLog/100}</td>
			            		<td class="">${d.tradeNo}</td>
			            		<td>
			            			<s:iterator value="#balanceType" var="type">
			            				<s:if test="key==event">
			            					${value}
			            				</s:if>
			            			</s:iterator>
			            		
			            		</td>
			            		<td class="" style="width: 400px">${d.intro}</td>
			            		<td class=""><long_date:long_date datelong="${d.posttime}"></long_date:long_date> </td>
			            		
			            		<td>
			            		<sec:authorize ifAnyGranted="userBalance_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${d.logid}');">删除</a> | 
			            		</sec:authorize>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${userBalancePage.allRow}</i>条记录，当前第 ${userBalancePage.currentPage}/${userBalancePage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{userBalancePage.totalPage > 10}">
				          	<s:if test="%{userBalancePage.currentPage > 6}">
				          		<a href="javascript:void(0)" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{userBalancePage.totalPage > (userBalancePage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="userBalancePage.currentPage-5" />
								   	<s:param name="last" value="userBalancePage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{userBalancePage.currentPage==(#count.index+userBalancePage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0)" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="<%=path %>/userBalance_list?event=${event}&page=${userBalancePage.totalPage}" rel="<s:property value="userBalancePage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="userBalancePage.currentPage-5" />
								   	<s:param name="last" value="userBalancePage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{userBalancePage.currentPage==(#count.index+userBalancePage.currentPage-5)}">
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
								   	<s:if test="%{userBalancePage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0)" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0)" rel="<s:property value="userBalancePage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{userBalancePage.totalPage>1&&10>=userBalancePage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="userBalancePage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{userBalancePage.currentPage==(#count.index+1)}">
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
var event = '${event}';

 

$(document).ready(function(){
	$(".m_userBalance_l").attr("class","lhover");
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
	location.href=the_host+"userBalance_list?event="+type;
	
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
			ids += ids.substr(0,ids.length-1);
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"userBalance_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"userBalance_delete?ids="+ids;
		});
	}
		
}

	
</script>
</body>
</html>