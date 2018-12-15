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
	<title>停车场|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
		<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
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
		    iframe: {src: host+"carparkReview_list?id="+id}
		}); 
				
		}
	
	function optChange(carparkid,carparkname){
		var opt = $("#carpark_opt_"+carparkid).val();
		var path="";
		layer.load(5);
		switch(opt){
			case "entrance":
				path = host+"carparkEntrancePoint_list?carparkEntrancePoint.carpark.name="+encodeURI(carparkname)+"&formType=carpark";
				break;
			case "berth":
				path = host+"carparkBerthPolygon_list?formType=carpark&carparkBerthPolygon.carpark.name="+encodeURI(carparkname);
				break;
			case "marker":
				path = host+"carparkMarkerPoint_list?formType=carpark&carparkMarkerPoint.carpark.name="+encodeURI(carparkname);
				break;
			case "fuzhu":
				path = host+"carparkFuzhuPolygon_list?formType=carpark&carparkFuzhuPolygon.carpark.name="+encodeURI(carparkname);
				break;
			case "city":
				path = host+"carparkCityPolyline_list?formType=carpark&carparkCityPolyline.carpark.name="+encodeURI(carparkname);
				break;
			case "background":
				path = host+"carparkBackgroundPolygon_list?formType=carpark&carparkBackgroundPolygon.carpark.name="+encodeURI(carparkname);
				break;
			case "shop":
				path = host+"carparkShopPolygon_list?formType=carpark&carparkShopPolygon.carpark.name="+encodeURI(carparkname);
				break;
			case "navigationPoint":
				path = host+"carparkNavigationPoint_list?formType=carpark&carparkNavigationPoint.carparkid="+carparkid;
				break;
			case "navigation":
				path = host+"carparkNavigationPolyline_list?formType=carpark&carparkNavigationPolyline.carpark.name="+encodeURI(carparkname);
				break;
			case "road":
				path = host+"carparkRoadPolyline_list?formType=carpark&carparkRoadPolyline.carpark.name="+encodeURI(carparkname);
				break;
			case "model":
				path = host+"carparkModelPolygon_list?formType=carpark&carparkModelPolygon.carpark.name="+encodeURI(carparkname);
				break;
		
		}
		
		location.href=path;
	}

	
	function sqlopt(o){
		var val = o.value;
		if(val=="in"){
			location.href=host+"carpark_importsql"
		}
		if(val=="out"){
			var ids = "";
			$("[name='checkbox']:checked").each(function(){
				ids += $(this).val()+",";
			});
			if(ids.length>0){
				location.href=host+"carpark_outsql?ids="+ids;
			}else{
				layer.alert('请至少选择一条数据！', 8, !1);
			}
			
		}
		
		$(o).find("option:eq(0)").attr("selected","selected");
	}
</script>
	<div class="content-right">
		<div class="zsy-right">
			<div class="z-r-bt">
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<form action="<%=path %>/carpark_list" method="post" id="searchForm">
				<input type="hidden" value="${carpark.name}" name="carpark.name"/>
				<input type="hidden" name="carpark.maptype" value="${carpark.maptype}"/>
				<input type="hidden" value="${page}" name="page" id="page"/>
				<input type="hidden" name="carpark.carparkCategory.categoryid" value="${carpark.carparkCategory.categoryid}"/>
			</form>
			
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/carpark_list" method="post" >
				    	<div class="l-b-b">名称：
				        	<input type="text" value="${carpark.name}" name="carpark.name"/>
				        </div>	
				        <div class="l-b-b">类型：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" name="carpark.carparkCategory.categoryid" value="${carpark.carparkCategory.categoryid}"/>
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="">请选择...</li>
							    	<s:iterator value="carparkCategories" var="cc">
							    	<li rel="${cc.categoryid}">${cc.name}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				         </div>
				         <div class="l-b-b">地图类型：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" name="carpark.maptype" value="${carpark.maptype}"/>
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="">请选择...</li>
							    	<li rel="1">微地图</li>
							    	<li rel="2">精确入口</li>
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
								<sec:authorize ifAnyGranted="carpark_add">
								<th width="88" class=""><a href="<%=path %>/carpark_add?method=preAdd">添加</a></th>
								<th class="tabx"><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="carpark_delete">
								<th width="108" class="tbj3"><a href="javascript:void(0);" onclick="bulkDelete(0);">删除</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="carpark_add">
								<th width="108" class="tbj8"><a href="<%=path %>/carpark_importExcel" >导入</a></th>
								<th class="tabx "><i></i></th>
								</sec:authorize>
								<th class="tabx "><i></i></th>
								<th width="108" class="tbj4">
									<select onchange="sqlopt(this)">
										<option >---sql操作---</option>
										<option value="out">导出sql</option>
										<option value="in">导入sql</option>
									</select>
								</th>
								<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">名称</td>
			            		<td class="">类型</td>
			            		<td class="">定价类型</td>
			            		<td class="">车位数</td>
			            		<td class="">市</td>
			            		<td class="">区</td>
			            		<td class="">地址</td>
			            		<td class="">级数</td>
			            		<td class="">地图类型</td>
			            		<td class="">操作</td>
			            	</tr>
			            	<s:iterator value="carparkPage.list" var="c">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${c.carparkid}" id="checkbox_${c.carparkid}" class="checkItem"/></td>
			            		<td class="">${c.name}</td>
			            		<td class="">${c.carparkCategory.name}</td>
			            		<td class="">
			            			<c:if test="${c.carparkSystemPrice.priceType==1}">强制定价</c:if>
			            			<c:if test="${c.carparkSystemPrice.priceType==2}">参考定价</c:if>
			            			<c:if test="${c.carparkSystemPrice.priceType==3 || c.carparkSystemPrice.priceType==null}">默认定价</c:if>
			            		</td>
			            		<td class="">${c.totalBerth}</td>
			            		<td class="">${c.city}</td>
			            		<td class="">${c.county}</td>
			            		<td class="">${c.address}</td>
			            		<td class="">${c.showLevel}</td>
			            		<td class="">
			            		<s:if test="#c.maptype==1">微地图</s:if>
			            		<s:elseif test="#c.maptype==2">精确入口</s:elseif>
			            		<s:else>用户新增</s:else>
			            		</td>
			            		<td class="">
			            			<sec:authorize ifAnyGranted="carpark_systemPriceEdit">
			            			<a href="<%=path %>/carpark_systemPriceEdit?id=${c.carparkid}&method=systemPriceEdit">系统定价</a> | 
			            			</sec:authorize>
			            			<sec:authorize ifAnyGranted="carpark_edit">
			            			<a href="<%=path %>/carpark_edit?id=${c.carparkid}&method=preEdit">编辑</a> | 
			            			</sec:authorize>
			            			<sec:authorize ifAnyGranted="carpark_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete(${c.carparkid});">删除</a> | 
			            			</sec:authorize>
			            			<a href="javascript:openReview('${c.carparkid}','${c.name}');" >评论</a>|
			            			<select style="border:0;color:gray" id="carpark_opt_${c.carparkid}" onchange="optChange(${c.carparkid},'${c.name}');" carpark="${c.name}">
			            				<option >
			            					请选择
			            				</option>
				            			<option value="entrance">
					            			出入口
				            			</option>
				            			<option value="berth">
				            				车位
				            			</option>
				            			<option value="marker">
				            				标注
				            			</option>
				            			<option value="fuzhu">
				            				辅助图层
				            			</option>
				            			<option value="city">
				            				外部道路
				            			</option>
				            			<option value="background">
				            				背景底图
				            			</option>
				            			<option value="shop">
				            				商场房间
				            			</option>
				            			<option value="road">
				            				内部道路
				            			</option>
				            			<option value="navigation">
				            				导航路线
				            			</option>
				            			<option value="navigationPoint">
				            				导航点
				            			</option>
				            			<option value="model">
				            				高度模型
				            			</option>
			            			</select>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${carparkPage.allRow}</i>条记录，当前第 ${carparkPage.currentPage}/${carparkPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{carparkPage.totalPage > 10}">
				          	<s:if test="%{carparkPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{carparkPage.totalPage > (carparkPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="carparkPage.currentPage-5" />
								   	<s:param name="last" value="carparkPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{carparkPage.currentPage==(#count.index+carparkPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="carparkPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="carparkPage.currentPage-5" />
								   	<s:param name="last" value="carparkPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{carparkPage.currentPage==(#count.index+carparkPage.currentPage-5)}">
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
								   	<s:if test="%{carparkPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="carparkPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{carparkPage.totalPage>1&&10>=carparkPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="carparkPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{carparkPage.currentPage==(#count.index+1)}">
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
	$(".m_carpark_l").attr("class","lhover");
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
	/*** 自定义select END***/
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
			layer.confirm('确定要删除吗？',function(index){
				window.location.href=the_host+"carpark_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"carpark_delete?ids="+ids;
		});
	}
		
}
</script>
</body>
</html>