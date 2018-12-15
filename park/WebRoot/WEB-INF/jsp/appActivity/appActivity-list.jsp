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
	<title>app活动 |泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
		<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
</head>
<script type="text/javascript">

function openWindow(activityid){
	 var path = "<%=path %>/appActivity_toPreview?appActivity.activityid="+activityid;
	$.layer({
	    type: 2,
	    shadeClose: true,
	    title: false,
	    closeBtn: [0, false],
	    shade: [0.8, '#000'],
	    border: [0],
	    offset: ['20px',''],
	    area: ['800px', ($(window).height() - 50) +'px'],
	    iframe: {src: path}
	}); 

}

var openPhoto = function(url){
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
<style>

.textEllipsis{
	overflow:hidden;
	text-overflow:ellipsis;
	-o-text-overflow:ellipsis;
}


</style>
<body>
<%@include file="../template/top.jsp"%> 
<div class="content">
	<%@include file="../template/menu.jsp"%> 

	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">App活动</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			
			
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/appActivity_list" method="post" id="searchForm" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
				    	<div class="l-b-b">活动标题：
				        	<input type="text" value="${appActivity.title}" name="appActivity.title"  />
				        </div>
				         <div class="l-b-n" id="xrig">	
				         	<input type="hidden" value="${page}" name="page" id="page"/>
							<input type="submit" value="搜 索"  class="btn1"/>
				         </div>
				    </form>     
				</div>
			</div>
			
			<div class="listbox">
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<sec:authorize ifAnyGranted="appActivity_toAdd">
								<th width="88" class=""><a href="<%=path %>/appActivity_toSave">添加</a></th>
								<th class="tabx"><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="appActivity_delete">
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
			            		<td class=""><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">标题</td>
			            		<td class="">备注</td>
			            		<td class="">缩略图</td>
			            		<td class="">添加时间</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="appActivityPage.list" var="d">
		            		<tr class="">
			            		<td class=""><input type="checkbox" name="checkbox" value="${d.activityid}" id="checkbox_${d.activityid}" class="checkItem"/></td>
			            		<td class="" >${d.title}</td>
			            		<td class="textEllipsis" width="400px">${d.memo}</td>
			            		<td class="">  
			            			<c:if test="${d.imgurl!=null}">
			            				<a href="javascript:openPhoto( '<%=clienturl %>${d.imgurl}' );"  >点击预览</a>
			            			</c:if>
			            			
			            		</td>
			            		<td class="">${d.formatPosttime}</td>
			            		<td class="">
			            			<sec:authorize ifAnyGranted="appActivity_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete(${d.activityid});">删除</a>|
			            			</sec:authorize>
			            			<sec:authorize ifAnyGranted="appActivity_toUpdate">
			            			<a href="<%=path %>/appActivity_toSave?appActivity.activityid=${d.activityid}" >修改</a>| 
			            			</sec:authorize>
			            			<a href="javascript:openWindow(${d.activityid});" >预览</a> 
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
			       		<s:if test="%{appActivityPage.totalPage > 10}">
				          	<s:if test="%{appActivityPage.currentPage > 6}">
				          		<a href="<%=path %>/appActivity_list?page=1;" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{appActivityPage.totalPage > (appActivityPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="appActivityPage.currentPage-5" />
								   	<s:param name="last" value="appActivityPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{appActivityPage.currentPage==(#count.index+appActivityPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="<%=path %>/appActivity_list?page=<s:property/>" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="<%=path %>/appActivity_list?page=${appActivityPage.totalPage}" rel="<s:property value="appActivityPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="appActivityPage.currentPage-5" />
								   	<s:param name="last" value="appActivityPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{appActivityPage.currentPage==(#count.index+appActivityPage.currentPage-5)}">
									   		<span class="current"><s:property/></span>
									   	</s:if>
									   	<s:else>
									   		<a href="<%=path %>/appActivity_list?page=<s:property/>" rel="<s:property/>"><s:property/></a>  
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
								   	<s:if test="%{appActivityPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="<%=path %>/appActivity_list?page=<s:property/>" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="<%=path %>/appActivity_list?page=${appActivityPage.totalPage}" rel="<s:property value="appActivityPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{appActivityPage.totalPage>1&&10>=appActivityPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="appActivityPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{appActivityPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="<%=path %>/appActivity_list?page=<s:property/>" rel="<s:property/>"><s:property/></a>  
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
	$(".m_appActivity_l").attr("class","lhover");
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
				window.location.href=the_host+"appActivity_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"appActivity_delete?ids="+ids;
		});
	}
		
}
</script>
</body>
</html>