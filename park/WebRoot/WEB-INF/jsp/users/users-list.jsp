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
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>

<script type="text/javascript">
	function order(ordercol,d_a){
		//$("#registerOrder,#loginOrder").val("");
		$("#orderPrecedence").val(ordercol);
		$("#page").val(0);
		$("#"+ordercol).val(d_a);
		$("#searchForm").submit();
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">用户管理</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			 <form action="<%=path %>/users_list" method="post" id="searchForm">
				<input type="hidden"  name="page" id="page" value="${page}"/>
				<input type="hidden" value="${user.username}" name="user.username"/>
				<input type="hidden" value="${user.carNumber}" name="user.carNumber"/>
				<input type="hidden" id="loginOrder" value="${loginOrder}" name="loginOrder"/>
				<input type="hidden" id="registerOrder" value="${registerOrder}" name="registerOrder"/>
				<input type="hidden" id="orderPrecedence" name="orderPrecedence" value="${orderPrecedence}"/>
				<input type="hidden" value="${user.invitecode}" name="user.invitecode"/>
			</form>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/users_list" method="post" >
				    	<div class="l-b-b">用户名：
				        	<input type="text" value="${user.username}" name="user.username"/>
				        </div>	
				        <div class="l-b-b">车牌：
				        	<input type="text" value="${user.carNumber}" name="user.carNumber"/>
				        </div>
				        <div class="l-b-b">邀请码：
				        	<input type="text" value="${user.invitecode}" name="user.invitecode"/>
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
								<sec:authorize ifAnyGranted="users_delete">
								<th width="108" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="users_toUpdateBalance">
								<th width="108" class="tbj2"><a href="javascript:void(0);" onclick="bulkBalance(0);">修改余额</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="users_toUpdate">
								<th width="108" class="tbj2"><a href="javascript:void(0);" onclick="bulkCredit(0);">修改信誉</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="users_toUpdate">
								<th width="108" class="tbj2"><a href="javascript:void(0);" onclick="bulkPoint(0);">修改积分</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="users_toUpdateCoupon">
								<th width="108" class="tbj2"><a href="javascript:void(0);" onclick="bulkCoupon(0);">修改停车券</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<th class="tbnonebj" style="_width:900px;">&nbsp;<font>今日注册数:<font color=red> ${todayRegCount}</font></font></th>
								
								
								
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">渠道</td>
			            		<td class="">用户名</td>
			            		<td class="">昵称</td>
			            		<td class="">姓名</td>
			            		<td class="">车牌号</td>
			            		<td class="">信誉</td>
			            		<td class="">贡献</td>
			            		<td class="">积分</td>
			            		<td class="">余额</td>
			            		<td class="">停车券</td>
			            		<td >注册时间
			            			<s:if test="registerOrder=='desc'">
				            			<a href="#" onclick="order('registerOrder','asc')" title="点击升序">
					            			↑
				            			</a>
			            			</s:if>
			            			<s:if test="registerOrder=='asc'" >
			            				<a href="#" onclick="order('registerOrder','desc')" title="点击降序">
					            			↓
				            			</a>
			            			</s:if>
			            		</td>
			            		<td class="">
			            			登陆时间
			            			<s:if test="loginOrder=='desc'" >
				            			<a href="#" onclick="order('loginOrder','asc')" title="点击升序">
					            			↑
				            			</a>
			            			</s:if>
			            			<s:if test="loginOrder=='asc'" >
			            				<a href="#" onclick="order('loginOrder','desc')" title="点击降序">
					            			↓
				            			</a>
			            			</s:if>
			            			</td>
			            		<td class="">邀请码</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="usersPage.list" var="u">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${u.userid}" id="checkbox_${u.userid}" class="checkItem"/></td>
			            		<td class="">${u.appChannel.name}</td>
			            		<td class="">${u.username}</td>
			            		<td class="">${u.nickname}</td>
			            		<td class="">${u.realname}</td>
			            		<td class="">${u.carNumber}</td>
			            		<td class=""><a href="<%=path %>/userCredit_list?userCredit.users.username=${u.username}">${u.credit}</a>
			            		<%--<a href="<%=path %>/users_updateCredit?ids=${u.userid}"><img src="<%=path %>/page/images/edit.gif"/></a>--%>
			            		</td>
			            		<td class="">${u.shareCredit}</td>
			            		<td class=""><a href="<%=path %>/userPoint_list?userPoint.users.username=${u.username}">${u.point}</a>
			            		
			            		<%--<a href="<%=path %>/users_updatePoint?ids=${u.userid}"><img src="<%=path %>/page/images/edit.gif"/></a>--%>
			            		</td>
			            		<td class="">${u.balance/100}
			            		<%--<a href="<%=path %>/users_updateBalance?ids=${u.userid}"><img src="<%=path %>/page/images/edit.gif"/></a>--%>
			            		</td>
			            		<td class="">${u.couponBalnce/100}
			            		</td>
			            		<td>
			            		<long_date:long_date datelong="${u.registerTime}"/>
			            			
			            		</td>
			            		<td class=""><long_date:long_date datelong="${u.loginTime}"/></td>
			            		<td class="">${u.invitecode}</td>
			            		<td class="">
			            			<sec:authorize ifAnyGranted="users_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${u.userid}');">删除</a> | 
			            			</sec:authorize>
			            			<a href="<%=path %>/users_details?ids=${u.userid}">查看</a> 
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i><s:property value="usersPage.allRow"/></i>条记录，当前第<s:property value="usersPage.currentPage"/>/<s:property value="usersPage.totalPage"/>页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{usersPage.totalPage > 10}">
				          	<s:if test="%{usersPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{usersPage.totalPage > (usersPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="usersPage.currentPage-5" />
								   	<s:param name="last" value="usersPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{usersPage.currentPage==(#count.index+usersPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="usersPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="usersPage.currentPage-5" />
								   	<s:param name="last" value="usersPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{usersPage.currentPage==(#count.index+usersPage.currentPage-5)}">
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
								   	<s:if test="%{usersPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="usersPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{usersPage.totalPage>1&&10>=usersPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="usersPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{usersPage.currentPage==(#count.index+1)}">
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
function bulkCoupon(){
	var ids = getSelectId();
	if(ids!=null){
		var url = the_host+"users_updateCoupon?ids="+ids;
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
</script>
</body>
</html>