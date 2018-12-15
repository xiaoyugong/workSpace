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
	<title>图文管理|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer/layer.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script type="text/javascript">
		function send(meidaid){
			
			var url = "<%=path %>/weiXinNews_send";
			
			$.getJSON(url,{"news.media_id":meidaid},function(data){
				if(data.status){
					$("#"+meidaid).empty();
					$("#"+meidaid).text("已发布");
				}
				
			});
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">微信管理</a> » <a href="###">图文管理</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="listbox">
			<div class="listbt">
			</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<th width="88" class=""><a href="<%=path %>/weiXinNews_toAdd">添加</a></th>
								<th class="tabx"><i></i></th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class=""><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">media_id</td>
			            		<td class="">作者</td>
			            		<td class="">标题</td>
			            		<td class="">url</td>
			            		<td class="">是否显示封面</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="weiXinNewsPage.list" var="d">
		            		<tr class="">
			            		<td class="" width="40px" ><input type="checkbox" name="checkbox" value="${d.media_id}" id="checkbox_${d.media_id}" class="checkItem"/></td>
			            		<td class="">${d.media_id}</td>
			            		<td class="">${d.author}</td>
			            		<td class="">${d.title}</td>
			            		<td class="">${d.content_source_url}</td>
			            		<td class="">${d.show_cover_pic}</td>
			            		<td class="" >
			            			<a href="javascript:void(0);" onclick="bulkDelete('${d.media_id}');">删除</a> 
			            			<a href="<%=path %>/weiXinNews_toUpdate?news.media_id=${d.media_id}" >修改</a> 
			            			<a href="<%=path %>/weiXinNews_view?news.media_id=${d.media_id}" target="_blank" >预览</a> 
			            			<span id="${d.media_id}">
				            			<s:if test='isSend!=1'>
					            			<a href="javascript:void(0);"   onclick="send('${d.media_id}');">群发</a> 
				            			</s:if>
				            			<s:if test='isSend==1'>
					            			已发布
				            			</s:if>
			            			</span>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${weiXinNewsPage.allRow}</i>条记录，当前第 ${weiXinNewsPage.currentPage}/${weiXinNewsPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{weiXinNewsPage.totalPage > 10}">
				          	<s:if test="%{weiXinNewsPage.currentPage > 6}">
				          		<a href="javascript:page(1)" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{weiXinNewsPage.totalPage > (weiXinNewsPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="weiXinNewsPage.currentPage-5" />
								   	<s:param name="last" value="weiXinNewsPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{weiXinNewsPage.currentPage==(#count.index+weiXinNewsPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:page('<s:property/>')" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:page('${weiXinNewsPage.totalPage}')" rel="<s:property value="weiXinNewsPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="weiXinNewsPage.currentPage-5" />
								   	<s:param name="last" value="weiXinNewsPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{weiXinNewsPage.currentPage==(#count.index+weiXinNewsPage.currentPage-5)}">
									   		<span class="current"><s:property/></span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:page('<s:property/>')" rel="<s:property/>"><s:property/></a>  
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
								   	<s:if test="%{weiXinNewsPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:page('<s:property/>')" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:page('${weiXinNewsPage.totalPage}')" rel="<s:property value="weiXinNewsPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{weiXinNewsPage.totalPage>1&&10>=weiXinNewsPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="weiXinNewsPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{weiXinNewsPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:page('<s:property/>')" rel="<s:property/>"><s:property/></a>  
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


function page(page){
	location.href=the_host+"weiXinNews_list?page="+page;
	
}

$(document).ready(function(){
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
				window.location.href=the_host+"weiXinNews_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"weiXinNews_delete?ids="+ids;
		});
	}
		
}
</script>
</body>
</html>