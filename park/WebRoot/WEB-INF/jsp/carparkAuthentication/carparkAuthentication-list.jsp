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
	<title>车位认证|泊泊停车</title>
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
	host = "<%=path %>/";
		function user(){};
		
		var user = new user();
		$(function(){
		
			
	
		
		user.openPhoto = function(url){
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
			
		})

</script>
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">车位认证</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			
			<form action="<%=path %>/carparkAuthentication_list" method="post" id="searchForm">
					<input type="hidden" value="${users.username}" id="users_username" name="users.username"/>
				        	<input type="hidden" value="${carparkAuthentication.authenticationtype}"  id="_authenticationtype" name="carparkAuthentication.authenticationtype"/>
				     		<input type="hidden" value="${carparkAuthentication.status}"  id="_status" name="carparkAuthentication.status"/>
				     		<input type="hidden" value="${carparkAuthentication.carparkname}" id="_carparkname" name="carparkAuthentication.carparkname" />
				     		<input type="hidden" value="" id="_page" name="page" />
			</form>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/carparkAuthentication_list" method="post" >
				    	<div class="l-b-b">用户名：
				        	<input type="text" value="${users.username}" name="users.username"/>
				        	
				        </div>	
				        <div class="l-b-b">停车场：
				        	<input type="text" value="${carparkAuthentication.carparkname}" name="carparkAuthentication.carparkname"/>
				        	</div>
				         <s:set value="#{0:'停车场',1:'微地图车位',2:'无微地图车位'}" name="caype"></s:set>
				          <s:set value="#{0:'待审',1:'通过',2:'未通过'}" name="statusType"></s:set>
				          
				          <div class="l-b-b">类型：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${carparkAuthentication.authenticationtype}" name="carparkAuthentication.authenticationtype"  />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="3">全部</li>
							    	<s:iterator value="#caype" var="ac">
							    	<li rel="${key}">${value}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				         </div>	
				            <div class="l-b-b">状态：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${carparkAuthentication.status}" name = "carparkAuthentication.status" />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="3">全部</li>
							    	<s:iterator value="#statusType" var="ac">
							    	<li rel="${key}">${value}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				         </div>	
				          
							<%--<div class="l-b-b">
								<input type="hidden" value="${carparkAuthentication.authenticationtype}" id="carparkAuthentication.authenticationtype"/>
								类型:<s:select list="#caype" listKey="key" listValue="value" theme="simple"  name="carparkAuthentication.authenticationtype"></s:select>
				        	</div>
				        	<div class="l-b-b">
								状态:<s:select list="#statusType" listKey="key" listValue="value" theme="simple"  name="carparkAuthentication.status"></s:select>
								<input type="hidden" value="${carparkAuthentication.status}" id="carparkAuthentication.status"/>
				        	</div>
				         --%><div class="l-b-n" id="xrig">	
							<input type="submit" value="搜 索"  class="btn1"/>
				         </div>
						
				    </form>     
				</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<sec:authorize ifAnyGranted="carparkAuthentication_delete">
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
			            		<td class="">停车场名称</td>
			            		<td class="">车位号</td>
			            		<td class="">认证类型</td>
			            		<td class="">提交认证时间</td>
			            		<td class="">认证状态</td>
			            		<td class="">认证材料</td>
			            		<td class="">所属城市</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:set value="#{0:'停车场',1:'微地图车位','2':'无微地图车位'}" name="carparkType"></s:set>
			            	<s:iterator value="carparkAuthenticationPage.list" var="u">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${u.authenticationid}" id="checkbox_${u.authenticationid}" class="checkItem"/></td>
			            		<td class="">
			            			${u.users.username}
			            		</td>
			            		<td class="">${u.users.realname}</td>
			            		<td class="">${u.users.nickname}</td>
			            		<td class="">${u.carparkname}</td>
			            		<td class="">${u.berthnum}</td>
			            		<td class="">
									<s:iterator value="#carparkType" var="type">
			            			<s:if test="key==authenticationtype">
			            				${type.value}
			            			</s:if>
			            		</s:iterator>
			            		
								</td>
								<td>
								<long_date:long_date datelong="${u.posttime }"/>
								</td>
								<td>
								<s:iterator value="#statusType" var="stype">
			            			<s:if test="key==status&&status==0">
			            				<font color=red>${stype.value}</font>
			            			</s:if>
			            			<s:elseif test="key==status">
			            				${stype.value}
			            			</s:elseif>
			            		</s:iterator>
								</td>
			            		<td class="">
			            			<c:if test="${u.attached!=null&&u.attached!=''}">
			            				<c:choose>
												<c:when test="${fn:substring(u.attached,0,4) eq 'http'}">
													<a href="javascript:user.openPhoto( '${u.attached}' );"  >点击预览</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:user.openPhoto( 'http://app1.parkbobo.com/${u.attached}' );"  >点击预览</a>
												</c:otherwise>
											</c:choose>
			            			</c:if>
			            		</td>
			            		<td>${users.area}</td>
			            		<td class="">
			            		<sec:authorize ifAnyGranted="carparkAuthentication_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${u.authenticationid}');">删除</a> | 
			            		</sec:authorize>
			            		<sec:authorize ifAnyGranted="carparkAuthentication_check">
				            		<a href="<%=path %>/carparkAuthentication_check?carparkAuthentication.authenticationid=${u.authenticationid}" >审核</a> |
				            		<a href="javascript:void(0);" onclick="openWin('${u.authenticationid}')">认证划分</a>
				            		
			            		</sec:authorize>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${carparkAuthenticationPage.allRow}</i>条记录，当前第 ${carparkAuthenticationPage.currentPage}/${carparkAuthenticationPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{carparkAuthenticationPage.totalPage > 10}">
				          	<s:if test="%{carparkAuthenticationPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{carparkAuthenticationPage.totalPage > (carparkAuthenticationPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="carparkAuthenticationPage.currentPage-5" />
								   	<s:param name="last" value="carparkAuthenticationPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{carparkAuthenticationPage.currentPage==(#count.index+carparkAuthenticationPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0)" rel="<s:property value="carparkAuthenticationPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="carparkAuthenticationPage.currentPage-5" />
								   	<s:param name="last" value="carparkAuthenticationPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{carparkAuthenticationPage.currentPage==(#count.index+carparkAuthenticationPage.currentPage-5)}">
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
								   	<s:if test="%{carparkAuthenticationPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0)" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="carparkAuthenticationPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{carparkAuthenticationPage.totalPage>1&&10>=carparkAuthenticationPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="carparkAuthenticationPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{carparkAuthenticationPage.currentPage==(#count.index+1)}">
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
	$(".m_carparkAuthentication_l").attr("class","lhover");
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
				window.location.href=the_host+"carparkAuthentication_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
		
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"carparkAuthentication_delete?ids="+ids;
		});
	}}
 
function openWin(authenticationid){
	$.layer({
	    type: 2,
	    shadeClose: true,
	    title: false,
	    closeBtn: [0, true],
	    shade: [0.8, '#000'],
	    border: [0],
	    offset: ['150px',''],
	    area: ['800px', ($(window).height() - 300) +'px'],
	    iframe: {src: the_host+'carparkAuthentication_updateArea?authenticationid='+authenticationid}
	 });

}

		

</script>
</body>
</html>