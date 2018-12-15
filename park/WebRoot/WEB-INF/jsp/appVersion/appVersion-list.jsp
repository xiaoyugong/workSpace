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
	<title>APP|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer1.85/layer.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">APP</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/appVersion_list" method="post" id="searchForm" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
				    	<div class="l-b-b">APP版本：
				        	<input type="text" value="${versioncode}" name="versioncode" data-rule="integer; range[0~]" />
				        </div>
				        <div class="l-b-b">渠道：</div>
				    	<div class="l-b-b">
				    		<ul>
							  <li class="select_box" style="width:100px;">
							    <span>请选择...</span>
								<input type="hidden" name="channelid" value="${channelid}"/>
							    <ul class="son_ul" style="width:130px;">
							    	<li rel="">请选择...</li>
							    	<s:iterator value="appChannels" var="ac">
							    	<li rel="${ac.channelid}">${ac.name}</li>
							    	</s:iterator>
							    </ul>
							  </li>
							</ul>
				         </div>	
				         <div class="l-b-n" id="xrig">	
				         	<input type="hidden" value="${page}" name="page" id="page"/>
							<input type="submit" value="搜 索"  class="btn1"/>
				         </div>
				    </form>     
				</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<sec:authorize ifAnyGranted="appVersion_add">
								<th width="88" class=""><a href="<%=path %>/appVersion_add?method=preAdd">添加</a></th>
								<th class="tabx"><i></i></th>
								</sec:authorize>
								<sec:authorize ifAnyGranted="appVersion_delete">
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
			            		<td class="">版本号</td>
			            		<td class="">渠道</td>
			            		<td class="">下载次数</td>
								<td class="">APP类型</td>
			            		<td class="">强制升级</td>
			            		<td class="">上传时间</td>
			            		<td width="10%">操作</td>
			            	</tr>
			            	<s:iterator value="appVersionPage.list" var="app">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${app.id.versioncode}_${app.id.channelid}" id="checkbox_${app.id.versioncode}_${app.id.channelid}" class="checkItem"/></td>
			            		<td class="">${app.id.versioncode}</td>
			            		<td class="">${app.appChannel.name}</td>
			            		<td class="">${app.downloadcount}</td>
			            		<td class="">${app.category eq '0'?'车主APP':'物业APP'}</td>
								<td class="">${app.needUpdate eq 'Y'?'是':'否'}</td>
			            		<td class=""><fmt:formatDate value="${app.posttimeToDate}" pattern="yyyy/MM/dd HH:mm"/></td>
			            		<td class="">
			            			<sec:authorize ifAnyGranted="appVersion_edit">
			            			<a href="<%=path %>/appVersion_edit?versioncode=${app.id.versioncode}&channelid=${app.id.channelid}&method=preEdit">编辑</a> | 
			            			</sec:authorize>
			            			<sec:authorize ifAnyGranted="appVersion_delete">
			            			<a href="javascript:void(0);" onclick="bulkDelete('${app.id.versioncode}_${app.id.channelid}');">删除</a> 
			            			</sec:authorize>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${appVersionPage.allRow}</i>条记录，当前第 ${appVersionPage.currentPage}/${appVersionPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{appVersionPage.totalPage > 10}">
				          	<s:if test="%{appVersionPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{appVersionPage.totalPage > (appVersionPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="appVersionPage.currentPage-5" />
								   	<s:param name="last" value="appVersionPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{appVersionPage.currentPage==(#count.index+appVersionPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="appVersionPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="appVersionPage.currentPage-5" />
								   	<s:param name="last" value="appVersionPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{appVersionPage.currentPage==(#count.index+appVersionPage.currentPage-5)}">
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
								   	<s:if test="%{appVersionPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="appVersionPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{appVersionPage.totalPage>1&&10>=appVersionPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="appVersionPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{appVersionPage.currentPage==(#count.index+1)}">
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
	$(".m_appVersion_l").attr("class","lhover");
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
				window.location.href=the_host+"appVersion_delete?ids="+ids;
			});
		}else{
			layer.alert('请至少选择一条数据！', 8, !1);
		}
	}else{
		layer.confirm('确定要删除吗？',function(index){
			window.location.href=the_host+"appVersion_delete?ids="+ids;
		});
	}
		
}
	
</script>
</body>
</html>