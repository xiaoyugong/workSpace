<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.parkbobo.utils.*" %>
<%@include file="../common/taglibs.jsp" %>
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
	<title>车主认证|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/simple/ymPrompt.css"/>
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/ymPrompt.js"></script>
</head>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 
<script type="text/javascript">
	host = "<%=path %>/";
		function user(){};
		var user = new user();
		$(function(){
		
			user.openlayer=function(userid){ 
				$(".user_page [type=hidden]").val(userid);
			$.layer({
			    type: 1,
			    title: false,
			    area: ['300px', '100px'],
			    border: [0], //去掉默认边框
			   
			    closeBtn: [0, true], //去掉默认关闭按钮
			    shift: 'left', //从左动画弹出
			    page: {
			       dom:".user_page"
			    }
			    
			    	}
			);
		}
		user.page = function(page){
			
			var username = $("#users_username").val()==undefined?"":$("#users_username").val();
			var approveType = $("#_driverStatus").val()==undefined?"4":$("#_driverStatus").val();
			location.href = host+"driverAuth_list?driverAuth.users.username="+username+"&driverAuth.status="+approveType+"&page="+page;
		}
		user.openWindow=function (id){
			 var path = host+"driverAuth_check?users.userid="+id+"";
			$.layer({
			    type: 2,
			    shadeClose: true,
			    title: false,
			    closeBtn: [0, false],
			    shade: [0.8, '#000'],
			    border: [0],
			    offset: ['20px',''],
			    area: ['700px', ($(window).height() - 50) +'px'],
			    iframe: {src: path}
			}); 
		
		}
		})

</script>
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">车主认证</a></div>
				<div class="z-r-b-r"><%=date %></div>
				
			</div>
			 <s:set value="#{0:'未处理',1:'通过',2:'未通过'}"  name="approveType" ></s:set>
			  
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/driverAuth_list" method="post" id="searchForm">
				    	<div class="l-b-b">用户名：
				        	<input type="text" value="${driverAuth.users.username}" name="driverAuth.users.username"/>
				        	<input type="hidden" value="${driverAuth.users.username}" id=" users_username"/>
				        </div>	
				        
				         <div class="l-b-b">审核状态:</div>
				         <div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${driverAuth.status}" id="_driverStatus"/>
								<input type="hidden" value="${driverAuth.status}" name="driverAuth.status"  />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#approveType" var="ac">
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
							<sec:authorize ifAnyGranted="driverAuth_delete">
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
			            		<td class="">昵称</td>
								<td>车牌号</td>
			            		<td class="">车主附件</td>
			            		<td class="">审核状态</td>
			            		<td>提交时间</td>
			            		<td>所属城市</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="pageBean.list" var="u">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${u.userid}" id="checkbox_${u.userid}" class="checkItem"/></td>
			            		<td class="">
			            			<a href="<%=path %>/users_details?ids=${u.users.userid}">${u.users.username}</a>
			            		</td>
			            		<td>${u.users.realname}</td>
			            		<td>${u.users.nickname}</td>
			            		<td>
			            			${u.users.carNumber}
			            		</td>
			            		<td class="">
			            			<c:if test="${u.attached!=null&&u.attached!=''}">
			            				<c:choose>
											<c:when test="${fn:substring(u.attached,0,4) eq 'http'}">
												<a href="${u.attached}" target="_blank">点击打开</a>
											</c:when>
											<c:otherwise>
												<a href="<%=path %>/${u.attached}" target="_blank">点击打开</a>
											</c:otherwise>
										</c:choose>
			            			</c:if>
			            		</td>
			            		<td class="">
			            		<s:iterator value="#approveType" var="type">
			            			<s:if test="key==status&&status==0">
			            				<font color=red>${type.value}</font>
			            			</s:if>
			            			<s:elseif test="key==status">
			            				${type.value}
			            			</s:elseif>
			            		</s:iterator>
			            		</td>
			            		<td><long_date:long_date datelong="${u.posttime }"/> </td>
			            		<td>${users.area}</td>
			            		<td class="">
			            		<sec:authorize ifAnyGranted="driverAuth_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${u.userid}');">删除</a> | 
			            		</sec:authorize>
			            		<sec:authorize ifAnyGranted="driverAuth_uploadAttachment">
			            			<a href="<%=path %>/driverAuth_uploadAttachment?driverAuth.userid=${u.userid}" >上传附件</a> |
			            		</sec:authorize>
			            		<sec:authorize ifAnyGranted="driverAuth_check">
			            			<a href="<%=path %>/driverAuth_check?driverAuth.userid=${u.userid}" >审核</a> | 
			            			<a href="javascript:void(0);" onclick="openWindow('${u.userid}')" >认证划分</a>
			            		</sec:authorize>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${pageBean.allRow}</i>条记录，当前第 ${pageBean.currentPage}/${pageBean.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{pageBean.totalPage > 10}">
				          	<s:if test="%{pageBean.currentPage > 6}">
				          		<a href="javascript:user.page(1);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{pageBean.totalPage > (pageBean.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="pageBean.currentPage-5" />
								   	<s:param name="last" value="pageBean.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{pageBean.currentPage==(#count.index+pageBean.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:user.page(<s:property/>);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:user.page(${pageBean.totalPage});" rel="<s:property value="pageBean.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="pageBean.currentPage-5" />
								   	<s:param name="last" value="pageBean.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{pageBean.currentPage==(#count.index+pageBean.currentPage-5)}">
									   		<span class="current"><s:property/></span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:user.page(<s:property/>);" rel="<s:property/>"><s:property/></a>  
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
								   	<s:if test="%{pageBean.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:user.page(<s:property/>);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="pageBean.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{pageBean.totalPage>1&&10>=pageBean.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="pageBean.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{pageBean.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:user.page(<s:property/>);" rel="<s:property/>"><s:property/></a>  
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
	$(".m_driverAuth_l").attr("class","lhover");
	/**
	 * 分页
	 */
	$(".sabrosus a").each(function(){  
        $(this).click(function(){ 
        	//$('#page').val($(this).attr("rel"));
        //	$('#searchForm').submit();
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
				window.location.href=the_host+"driverAuth_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"driverAuth_delete?ids="+ids;
		});
	}}
	function openWindow(userid){
		$.layer({
	    type: 2,
	    shadeClose: true,
	    title: false,
	    closeBtn: [0, true],
	    shade: [0.8, '#000'],
	    border: [0],
	    offset: ['150px',''],
	    area: ['800px', ($(window).height() - 300) +'px'],
	    iframe: {src: the_host+'driverAuth_updateArea?userid='+userid}
	});
	
}
		

</script>
</body>
</html>