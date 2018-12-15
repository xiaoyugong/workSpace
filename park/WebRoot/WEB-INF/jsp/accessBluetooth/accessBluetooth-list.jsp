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
	<title>授权蓝牙|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
	<script src="<%=path %>/page/plugins/layer/layer.js"></script>
</head>
<body>
			<div class="listbox">
				<div class="listbt">
				    <form action="<%=path %>/accessBluetooth_list" method="post" id="searchForm" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
				         <div class="l-b-n" id="xrig">	
				         	<input type="hidden" value="${page}" name="page" id="page"/>
				         </div>
				    </form>     
				</div>
				<div class="listnr" >
					<table width="100%" border="1" style="background: #f0f5f7;">
						<thead>
							<tr>
								<th width="88" class=""><a href="<%=path %>/accessBluetooth_add?groundlockmac=${groundlockmac}">添加</a></th>
								<th class="tabx"><i></i></th>
								<th class="tbnonebj" style="_width:900px;">&nbsp;</th>
							</tr>
						</thead>
					</table>
					<table width="100%" class="tab2" id="tab">
			            <tbody width="100%">
			            	<tr class="tab2yt">
			            		<td class="tbt1"><input type="checkbox" name="checkAll" value="checkbox" id="checkAll" class="checkAll"/></td>
			            		<td class="">地锁MAC</td>
			            		<td class="">蓝牙MAC</td>
			            		<td class="">类型</td>
			            		<td class="">是否可用</td>
			            		<td class="">车牌号</td>
			            		<td class="">手机号</td>
			            		<td>操作</td>
			            	</tr>
			            	<s:iterator value="accessBluetoothPage.list" var="a">
		            		<tr class="">
			            		<td class="tbt1"><input type="checkbox" name="checkbox" value="${a.bluetoothid}" id="checkbox_${a.bluetoothid}" class="checkItem"/></td>
			            		<td class="">${groundlockmac}</td>
			            		<td class="">${a.bluetoothmacStr}</td>
			            		<td class="">${a.bluetoothtype==0?'车载蓝牙':'非车载蓝牙'}</td>
			            		<td class="">${a.bluetoothEnable==0?'否':'是'}</td>
			            		<td class="">${a.carNumber}</td>
			            		<td class="">${a.telephone}</td>
			            		<td class="">
			            			<a href="<%=path %>/accessBluetooth_edit?bluetoothid=${a.bluetoothid}">编辑</a> |
			            			<a href="javascript:void(0);" onclick="del('${a.bluetoothid}','${groundlockmac}','${a.bluetoothmacStr}')">删除</a>
			            		</td>
			            	</tr>
			            	</s:iterator>
			            </tbody>
					</table>
				</div>
				<div class="tabbottom">
					<div class="tab-left">共<i>${accessBluetoothPage.allRow}</i>条记录，当前第 ${accessBluetoothPage.currentPage}/${accessBluetoothPage.totalPage }页</div>
					<div class="tab-right">
			       		<div class="sabrosus">
			       		<s:if test="%{accessBluetoothPage.totalPage > 10}">
				          	<s:if test="%{accessBluetoothPage.currentPage > 6}">
				          		<a href="javascript:void(0);" rel="1" title="首页">&laquo;</a><span class="em">...</span>
				          		<s:if test="%{accessBluetoothPage.totalPage > (accessBluetoothPage.currentPage+5)}">
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="accessBluetoothPage.currentPage-5" />
								   	<s:param name="last" value="accessBluetoothPage.currentPage+5" />
								   	<s:iterator status="count">
									   	<s:if test="%{accessBluetoothPage.currentPage==(#count.index+accessBluetoothPage.currentPage-5)}">
									   		<span class="current"><s:property/> </span>
									   	</s:if>
									   	<s:else>
									   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
									   	</s:else>
								   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="accessBluetoothPage.totalPage"/>" title="尾页">&raquo;</a>
				          		</s:if>
				          		<s:else>
				          			<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   	<s:param name="first" value="accessBluetoothPage.currentPage-5" />
								   	<s:param name="last" value="accessBluetoothPage.totalPage" />
								   	<s:iterator status="count">
									   	<s:if test="%{accessBluetoothPage.currentPage==(#count.index+accessBluetoothPage.currentPage-5)}">
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
								   	<s:if test="%{accessBluetoothPage.currentPage==(#count.index+1)}">
								   		<span class="current"><s:property/></span>
								   	</s:if>
								   	<s:else>
								   		<a href="javascript:void(0);" rel="<s:property/>"><s:property/></a>  
								   	</s:else>
							   	</s:iterator>
								</s:bean>
								<span class="em">...</span><a  href="javascript:void(0);" rel="<s:property value="accessBluetoothPage.totalPage"/>" title="尾页"> &raquo;</a>
				          	</s:else>
				        </s:if>
				        <s:elseif test="%{accessBluetoothPage.totalPage>1&&10>=accessBluetoothPage.totalPage}">
				          	<s:bean name="org.apache.struts2.util.Counter" id="counter">
							   	<s:param name="first" value="1" />
							   	<s:param name="last" value="accessBluetoothPage.totalPage" />
							   	<s:iterator status="count">
								   	<s:if test="%{accessBluetoothPage.currentPage==(#count.index+1)}">
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
<script type="text/javascript">
var the_host = "<%=path%>/";

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
	
	//加载扩展模块
	layer.config({
	    extend: the_host + 'page/plugins/layer/extend/layer.ext.js'
	});
});
function del(bluetoothid, groundlockmac,bluetoothmacStr)
{
	layer.confirm('确定要删除吗？',function(index){
		var index = layer.load(1, {
		    shade: [0.4,'#000'] 
		});
		$.ajax({
			type: 'POST',
			url: the_host + "accessBluetooth_delete" ,
			data: {
				'accessBluetooth.groundlock.groundlockid': encodeURI(groundlockmac),
				'bluetoothid': encodeURI(bluetoothid),
				'accessBluetooth.bluetoothmacStr': encodeURI(bluetoothmacStr)
			},
			
			dataType: 'json',
			success: function (data) {
				layer.close(index);
				if(data.status == "true")
				{
					layer.msg('删除蓝牙成功', {icon: 6});
					setTimeout(function(){
					    location.reload();
					}, 500);
				}
				else
				{
					layer.msg('删除蓝牙失败', function(){});
				}
			},
			error: function (XMLResponse) {
				layer.close(index);
			}
		});
	});
}
</script>
</body>
</html>