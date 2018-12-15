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
	<title>素材管理|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer/layer.min.js"></script>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">微信管理</a> » <a href="###">素材管理</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="listbox">
			<div class="listbt">
			</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<th width="88" class=""><a href="<%=path %>/weiXinMaterial_toAdd">添加</a></th>
								<th class="tabx"><i></i></th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class=""><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">media_id</td>
			            		<td class="">类型</td>
			            		<td class="">路径</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="weiXinMaterialPage.list" var="d">
		            		<tr class="">
			            		<td class="" width="40px" ><input type="checkbox" name="checkbox" value="${d.media_id}" id="checkbox_${d.media_id}" class="checkItem"/></td>
			            		<td class="">
			            			${d.media_id}
			            		</td>
			            		<td class="">${d.type}</td>
			            		<td class="">${d.path}</td>
			            		<td class="">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${d.media_id}');">删除</a> 
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${weiXinMaterialPage.allRow}</i>条记录，当前第 ${weiXinMaterialPage.currentPage}/${weiXinMaterialPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{weiXinMaterialPage.totalPage > 10}">
				          	<s:if test="%{weiXinMaterialPage.currentPage > 6}">
				          		<a href="javascript:page(1)" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{weiXinMaterialPage.totalPage > (weiXinMaterialPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="weiXinMaterialPage.currentPage-5" />
								   	<s:param name="last" value="weiXinMaterialPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{weiXinMaterialPage.currentPage==(#count.index+weiXinMaterialPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:page('<s:property/>')" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:page('${weiXinMaterialPage.totalPage}')" rel="<s:property value="weiXinMaterialPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="weiXinMaterialPage.currentPage-5" />
								   	<s:param name="last" value="weiXinMaterialPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{weiXinMaterialPage.currentPage==(#count.index+weiXinMaterialPage.currentPage-5)}">
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
								   	<s:if test="%{weiXinMaterialPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:page('<s:property/>')" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:page('${weiXinMaterialPage.totalPage}')" rel="<s:property value="weiXinMaterialPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{weiXinMaterialPage.totalPage>1&&10>=weiXinMaterialPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="weiXinMaterialPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{weiXinMaterialPage.currentPage==(#count.index+1)}">
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
	_weiXinMaterial = $("#_weiXinMaterial").val();
	location.href=the_host+"weiXinMaterial_list?page="+page+"&weiXinMaterial.weiXinMaterial="+_weiXinMaterial;
	
}

$(document).ready(function(){
	$(".m_weiXinMaterial_l").attr("class","lhover");
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
				window.location.href=the_host+"weiXinMaterial_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"weiXinMaterial_delete?ids="+ids;
		});
	}
		
}
</script>
</body>
</html>