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
	<title>用户列表|泊泊停车</title>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">系统通知</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			 <form action="<%=path %>/notify_selectUser" method="post" id="searchForm">
				<input type="hidden"  name="page" id="page"/>
				<input type="hidden" value="${user.username}" name="user.username"/>
				<input type="hidden" name="notify.title" value="${notify.title}" />
				<textarea style="display: none" name="notify.content" >${notify.content}</textarea>
				<input type="hidden" name="notifyType" value="${notifyType}"/>
				<input type="hidden" name="notifysendMode" value="${notifysendMode}"/>
			</form>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/notify_selectUser" method="post" >
				    	<div class="l-b-b">用户名：
				        	<input type="text" value="${user.username}" name="user.username"/>
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
							<th width="108" class=""><a href="javascript:void(0);" onclick="bulkadd(0);">添加到列表</a></th>
								<th class="tabx "><i></i></th>
								<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">用户名</td>
			            		<td class="">昵称</td>
			            		<td class="">姓名</td>
			            		<td class="">车牌号</td>
			            		<td class="">注册时间</td>
			            	</tr>
			            	<s:iterator value="usersPage.list" var="u">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${u.userid}" id="checkbox_${u.userid}" class="checkItem"/></td>
			            		<td class="">${u.username}</td>
			            		<td class="">${u.nickname}</td>
			            		<td class="">${u.realname}</td>
			            		<td class="">${u.carNumber}</td>
			            		<td class=""><fmt:formatDate value="${u.registerTimeToDate}" pattern="yyyy/MM/dd HH:mm"/></td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${usersPage.allRow}</i>条记录，当前第 ${usersPage.currentPage}/${usersPage.totalPage }页</div>
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
	<form action="<%=path %>/${notifysendMode==0?'notify_add':'notify_toSms'}" method="post" id="tempData">
		<input type="hidden" name="method" value="add_usersession"/>
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