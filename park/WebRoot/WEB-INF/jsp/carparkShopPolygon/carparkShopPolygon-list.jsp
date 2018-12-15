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
	<title>停车场商场房间|泊泊停车</title>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场商场房间</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<form action="<%=path %>/carparkShopPolygon_list" method="post" id="searchForm">
				<input name="page" id="_page" type="hidden"/>
				<input name="carparkShopPolygon.name" type="hidden" value="${carparkShopPolygon.name}"/>
				<input name="carparkShopPolygon.carpark.name" type="hidden" value="${carparkShopPolygon.carpark.name}"/>
			</form>
			
			<div class="listbox">
			<div class="listbt">
				<form action="<%=path %>/carparkShopPolygon_list" method="post" >
					<div class="l-b-b">名称：
						<input name="carparkShopPolygon.name" type="text" value="${carparkShopPolygon.name}"/>
					</div>
					<div class="l-b-b">停车场：
						<input name="carparkShopPolygon.carpark.name" type="text" value="${carparkShopPolygon.carpark.name}"/>
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
							<sec:authorize ifAnyGranted="carparkShopPolygon_toAdd">
							<th width="88" ><a href="<%=path %>/carparkShopPolygon_toAdd" >添加</a></th>
							</sec:authorize>
							<sec:authorize ifAnyGranted="carparkShopPolygon_toAdd">
							<th width="108" class="tbj8"><a href="<%=path %>/carparkShopPolygon_toImportExcel" >导入</a></th>
							<th class="tabx "><i></i></th>
							</sec:authorize>
							<sec:authorize ifAnyGranted="carparkShopPolygon_delete">
								<th width="88" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
							</sec:authorize>
							<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            	<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td>停车场</td>
			            		<td class="">名称</td>
			            		<td>背景色</td>
			            		<td class="">边框色</td>
			            		<td class="">点击后背景色</td>
			            		<td class="">点击后边框色</td>
			            		<td class="">宽度</td>
			            		<td class="">高度</td>
			            		<td class="">字体颜色</td>
			            		<td class="">字体大小</td>
			            		<td class="">字体加粗</td>
			            		<td class="">字体倾斜</td>
			            		<td align="center">操作</td>
			            	</tr>
			            	<s:iterator value="page.list" var="d">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${d.gid}" id="checkbox_${d.gid}" class="checkItem"/></td>
			            		<td class="">${d.carpark.name}</td>
			            		<td class="">${d.name}</td>
			            		<td width="50px">${d.color}</td>
			            		<td class="">
			            			${d.bordercolor }
			            		</td>
			            		<td class="">
			            			${d.clickColor }
			            		</td>
			            		<td class="">
			            			${d.clickBordercolor }
			            		</td>
			            		<td class="">
			            			${d.width }
			            		</td>
			            		<td class="">
			            			${d.height }
			            		</td>
			            		<td class="">
			            			${d.fontColor }
			            		</td>
			            		<td class="">
			            			${d.fontSize }
			            		</td>
			            		<td class="">
			            			${d.fontWeight }
			            		</td>
			            		<td class="">
			            			${d.fontItalic }
			            		</td>
			            		<td align="center">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${d.gid}');">删除</a> | 
			            			<a href="<%=path %>/carparkShopPolygon_toUpdate?carparkShopPolygon.gid=${d.gid}">修改</a> | 
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
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{page.totalPage > (page.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="page.currentPage-5" />
								   	<s:param name="last" value="page.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{page.currentPage==(#count.index+page.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
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
								   	<s:if test="%{page.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="page.totalPage"/>" title="尾页"> &raquo;</a>
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
</div>
<script type="text/javascript">
var the_host = "<%=path%>/";


$(document).ready(function(){
	$(".m_carparkShopPolygon_l").attr("class","lhover");
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
				window.location.href=the_host+"carparkShopPolygon_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"carparkShopPolygon_delete?ids="+ids;
		});
	}
		
}
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
</script>
</body>
</html>