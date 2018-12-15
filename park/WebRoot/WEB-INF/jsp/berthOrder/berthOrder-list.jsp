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
	<title>车位订单列表|泊泊停车</title>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">订单管理</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<s:set value="#{3:'全部',0:'没有违约',1:'违约处理中',2:'违约处理结束'}"  name="dstatus" ></s:set>
			<s:set value="#{0:'订单开始',1:'订单结束',2:'待支付',3:'取消支付'}"  name="status" ></s:set>
			<div class="listbox">
		
				         
				<div class="listnr" >
				
				<div class="listbt">
				
				<%--
					分页表单				
				--%>
				<form action="<%=path %>/berthOrder_list" method="post" id="pageform">
					<input type="hidden" name="userid" id="userid" value="${userid}"/>
					<input type="hidden" value="${berthOrder.defaultStatus}" name = "berthOrder.defaultStatus" />
					<input type="hidden" value="${berthOrder.telephone}" name="berthOrder.telephone"/>
					<input type="hidden" value="${berthOrder.carNumber}" name="berthOrder.carNumber"/>
					<input type="hidden" style="width:90px;" value="${berthOrder.berthShare.carparkname}" name="berthOrder.berthShare.carparkname"/>
					<input type="hidden" name="page" id="page"/>
				</form>
				<form action="<%=path %>/berthOrder_list" method="post" >
			 
				    	<div class="l-b-b">
				    	<div class="l-b-b">用户电话：
				        	<input type="text" style="width:90px;" value="${berthOrder.telephone}" name="berthOrder.telephone"/>
				        </div>	
				        <div class="l-b-b">停车场：
				        	<input type="text" style="width:90px;" value="${berthOrder.berthShare.carparkname}" name="berthOrder.berthShare.carparkname"/>
				        </div>
				        <div class="l-b-b">车牌号码：
				        	<input type="text" style="width:80px;" value="${berthOrder.carNumber}" name="berthOrder.carNumber"/>
				        </div>	
				        <div class="l-b-b">状态:</div>	
				        <div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" value="${berthOrder.defaultStatus}" id="_defaultStatus" name = "berthOrder.defaultStatus" />
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="3">请选择...</li>
							    	<s:iterator value="#dstatus" var="ds">
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
								<sec:authorize ifAnyGranted="berthOrder_delete">
								<th width="108" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<th class="tbnonebj" style="_width:900px;">&nbsp;&nbsp;<font>今日订单数:<font color=red>${todayOrderCount}</font></font></th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td>用户电话</td>
			            		<td>姓名</td>
			            		<td>昵称</td>
			            		<td>停车场</td>
			            		<td>车位号</td>
			            		<td>用户车牌号</td>
			            		<td>订单状态</td>
			            		<td class="">欠费</td>
			            		<td>超期停车</td>
			            		<td class="">违约状态</td>
			            		<td class="">违约保证金</td>
			            		<td class="">提交订单时间</td>
			            		<td class="">结束订单时间</td>
			            		<td class="">车位主</td>
			            		<td class="">平台</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="page.list" var="d" >
		            		<tr class="">
		            			<td class="tbt1"><input type="checkbox" name="checkbox" value="${d.berthorderid}" id="checkbox_${d.berthorderid}" class="checkItem"/></td>
			            		<td class="">
			            			<a href="<%=path %>/users_details?ids=${d.userid}">${d.telephone}</a>	
			            		</td>
			            		<td>${d.realname}</td>
			            		<td>${d.nickname}</td>
			            		<td>${d.berthShare.carparkname}</td>
			            		<td>${d.berthShare.berthnum}</td>
			            		<td class="">${d.carNumber}</td>
			            		<td class="">
									<s:iterator value="#status" var="type">
			            				<s:if test="key==status">
			            					${value}
			            				</s:if>
			            			</s:iterator>
			            			</td>
			            		<td class="">${d.isArrearage==1?'是':'否'}</td>
			            		<td>
			            			${d.iscq=="是" ? '<font color=red >是</font>':d.iscq }
			            		</td>
			            		<td class="">
			            			<s:iterator value="#dstatus" var="dtype">
			            				<s:if test="key==defaultStatus">
			            					${value}
			            				</s:if>
			            			</s:iterator>
			            		
			            		</td>
			            		<td class="">${d.exceedPrice/100 }</td>
			            		<td class="">
			            			<long_date:long_date datelong="${d.posttime}" />
			            		</td>
			            		<td class="">
			            			<long_date:long_date datelong="${d.closetime}" />
			            		</td>
			            		<td class="">${d.ownerRevenue/100 }</td>
			            		<td class="">${d.companyRevenue/100 }</td>
			            		<td class="">
			            			<sec:authorize ifAnyGranted="berthOrder_delete">
			            			<a href="javascript:bulkDelete(${d.berthorderid});">删除</a>
			            			</sec:authorize>
			            			
			            			<sec:authorize ifAnyGranted="berthOrder_drawback">
			            			<a href="javascript:drawback(${d.berthorderid},${d.isDrawback},${d.defaultStatus});">退还保证金</a>
			            			</sec:authorize>
			            			
			            			<a href="<%=path %>/berthOrder_view?berthOrder.berthorderid=${d.berthorderid}">详情</a>
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
	$(".m_berthOrder_l").attr("class","lhover");
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
	location.href=the_host+"berthOrder_list?event="+type;
	
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
				window.location.href=the_host+"berthOrder_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"berthOrder_delete?ids="+ids;
		});
	}
}
/**
 * 退还保证金
 */
 function drawback(berthorderid,isback,defaultStatus){
	if(defaultStatus!=0){
			if(isback==0){
				if(berthorderid == 0){
					var berthorderid = "";
					if(berthorderid.length>0){
						berthorderid=berthorderid.substr(0,berthorderid.length-1);
						layer.confirm('确定退还吗？',function(index){
							window.location.href=the_host+"berthOrder_drawback?berthorderid="+berthorderid;
						});
					}
				}else{
					layer.confirm('确定退还吗？',function(index){
					window.location.href=the_host+"berthOrder_drawback?berthorderid="+berthorderid;
					});
				}
			}else{
				layer.alert('违约保证金已经退还！', 8, !1);
			}
			
	}else{
		layer.alert('订单未违约，不支持退款！', 8, !1);
	}
}

</script>
</body>
</html>