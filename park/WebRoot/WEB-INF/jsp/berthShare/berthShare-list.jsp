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
	<title>车位分享|泊泊停车</title>
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
		    iframe: {src: host+"berthReview_list?id="+id}
		}); 
				
		}
	
	
	function order(ordercol,d_a){
		//$("#registerOrder,#loginOrder").val("");
		$("#orderPrecedence").val(ordercol);
		$("#_page").val(0);
		$("#"+ordercol).val(d_a);
		$("#searchForm").submit();
	}
</script>
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">车位分享</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			
			<form action="<%=path %>/berthShare_list" method="post" id="searchForm">
				<input type="hidden" value="${users.username}" name="users.username"/>
				<input type="hidden" value="${berthShare.sharetype}" name="berthShare.sharetype"  />
				<input type="hidden" value="${berthShare.isAuthentication}" name="berthShare.isAuthentication"  />
				<input type="hidden" value="${berthShare.carparkname}" name="berthShare.carparkname"/>
				<input type="hidden" value="${berthShare.berthnum}" name="berthShare.berthnum"/>
				<input type="hidden" name="page" id="_page"/>
				<input type="hidden" id="publishOrder" value="${publishOrder}" name="publishOrder"/>
				<input type="hidden" id="updateOrder" value="${updateOrder}" name="updateOrder"/>
				<input type="hidden" id="orderPrecedence" name="orderPrecedence" value="${orderPrecedence}"/>
			</form>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/berthShare_list" method="post" >
				   		 <div class="l-b-b">停车场：
				        	<input type="text" value="${berthShare.carparkname}" name="berthShare.carparkname"/>
				        </div>
				         <div class="l-b-b">车位号：
				        	<input type="text" value="${berthShare.berthnum}" name="berthShare.berthnum" style="width:70px" />
				        </div>
				    	<div class="l-b-b">用户名：
				        	<input type="text" value="${users.username}" name="users.username" style="width:70px"/>
				        </div>
				         <s:set value="#{0:'微地图车位',1:'无微地图车位'}" name="caype"></s:set>
				          <s:set value="#{0:'未审核',1:'审核中',2:'通过认证',3:'未通过认证'}" name="statusType"></s:set>
				          
				          <div class="l-b-b">类型：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${berthShare.sharetype}" name="berthShare.sharetype" />
								
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#caype" var="ac">
							    	<li rel="${key}">${value}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				         </div>	
				            <div class="l-b-b">认证状态：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${berthShare.isAuthentication}" name = "berthShare.isAuthentication" />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="9">全部</li>
							    	<s:iterator value="#statusType" var="ac">
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
								<sec:authorize ifAnyGranted="berthShare_delete">
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
			            		<td class="">停车场</td>
			            		<td class="">车位号</td>
			            		<td class="">类型</td>
			            		<td class="">数量</td>
			            		<td class="">分享时段</td>
			            		<td class="">重复日期</td>
			            		<td class="">关闭</td>
			            		<td class="">状态</td>
			            		<td class="">发布时间
			            			<s:if test="publishOrder=='desc'">
				            			<a href="#" onclick="order('publishOrder','asc')" title="点击升序">
					            			↑
				            			</a>
			            			</s:if>
			            			<s:if test="publishOrder=='asc'" >
			            				<a href="#" onclick="order('publishOrder','desc')" title="点击降序">
					            			↓
				            			</a>
			            			</s:if>
			            		</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:set value="#{0:'微地图',1:'无微地图'}" name="carparkType"></s:set>
			            	<s:set value="#{0:'未审核',1:'审核中',2:'通过认证',3:'未通过认证'}" name="carparkautu"></s:set>
			            	<s:iterator value="berthSharePage.list" var="u">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${u.shareid}" id="checkbox_${u.shareid}" class="checkItem"/></td>
			            		<td class="">${u.users.username}</td>
			            		<td class="">${u.users.realname}</td>
			            		<td class="">${u.users.nickname}</td>
			            		<td class="">${u.carparkname}</td>
			            		<td class="">${u.berthnum}</td>
			            		<td class="">
									<s:iterator value="#carparkType" var="type">
			            			<s:if test="key==sharetype">
			            				${type.value}
			            			</s:if>
			            		</s:iterator>
								</td>
								<td class="">${u.sharenum}</td>
								<td class=""><s:date name="#u.startTime" format="HH:mm"/> - <s:date name="#u.endTime" format="HH:mm"/></td>
								<td class="">${u.formatRepeatDate}</td>
								<td class="">${u.isClose==1?'是':'否'}</td>
								<td class="">
									<s:iterator value="#statusType" var="autu">
										<s:if test="key==isAuthentication">
											${autu.value}
										</s:if>
									</s:iterator>
								</td>
								<td>
									<long_date:long_date datelong="${u.sharetime}"/>
								</td>
								
			            		<td class="">
			            			<sec:authorize ifAnyGranted="berthShare_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${u.shareid}');">删除</a> | 
			            			</sec:authorize>
			            			<a href="javascript:void(0)" onclick="confirmClose('${u.shareid}','${u.isClose}')">关闭</a> |
			            			<a href="javascript:void(0)" onclick="confirmOpen('${u.shareid}','${u.isClose}')">开启</a> |
			            			<a href="<%=path %>/berthShare_check?berthShare.shareid=${u.shareid}" >详情</a> |
			            			<a href="javascript:openReview('${u.shareid}','${u.carparkname}${u.berthnum}号');" >评论</a>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${berthSharePage.allRow}</i>条记录，当前第 ${berthSharePage.currentPage}/${berthSharePage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{berthSharePage.totalPage > 10}">
				          	<s:if test="%{berthSharePage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{berthSharePage.totalPage > (berthSharePage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="berthSharePage.currentPage-5" />
								   	<s:param name="last" value="berthSharePage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{berthSharePage.currentPage==(#count.index+berthSharePage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="berthSharePage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="berthSharePage.currentPage-5" />
								   	<s:param name="last" value="berthSharePage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{berthSharePage.currentPage==(#count.index+berthSharePage.currentPage-5)}">
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
								   	<s:if test="%{berthSharePage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="berthSharePage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{berthSharePage.totalPage>1&&10>=berthSharePage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="berthSharePage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{berthSharePage.currentPage==(#count.index+1)}">
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
	$(".m_berthShare_l").attr("class","lhover");
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

function confirmClose(id,isClose){
	if(isClose==1){
		layer.alert('已关闭，请勿重复操作！', 8, !1);
	}else{
		layer.confirm('确定要关闭该分享吗？',function(index){
			window.location.href=the_host+"berthShare_close?berthShare.shareid="+id;
		});
	}
}
function confirmOpen(id,isClose){
	if(isClose==0){
		layer.alert('已分享，请勿重复操作！', 8, !1);
	}else{
	layer.confirm('确定要开启该分享吗？',function(index){
				window.location.href=the_host+"berthShare_open?berthShare.shareid="+id;
			});
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
			ids=ids.substr(0,ids.length-1);
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"berthShare_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
		
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"berthShare_delete?ids="+ids;
		});
	}}
 
		
host = "<%=path %>/";
		function user(){};
		
		var user = new user();
		
			
		
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
		
</script>

</body>
</html>