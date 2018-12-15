<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>发送通知|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/layer1.85/layer.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
    <script type="text/javascript">
	host = "<%=path %>/";
    
		function user(){};
		var user = new user();
		$(function(){
		
     	  $('#addForm').validator({
     		  
     		   invalid:function(form, errors){
     		  	resetSelect();
     		  
     		   },
     		   beforeSubmit:function(form){
     			   type = $("#notifyType").val();
     			   if(type==9){
     				   layer.alert('请选择发送类型！', 8, !1);
     				   return false;
     			   }
     			  
     			   layer.load(10);
     		   }
     	  })
			user.openlayer=function(){ 
				
				type = $("#notifyType").val();
				if(type==0){
					layer.load(5);
					$("#addForm").attr("action",host+"notify_selectCarpark");
					$("#addForm").submit();
				}
				if(type==1){
					layer.load(5);
					$("#addForm").attr("action",host+"notify_selectUser");
					$("#addForm").submit();
				}
				if(type==2){
					$("#tab").hide();
					$("#add_but").remove();
				}
		}
     	  user.notifyTitle = $("#notifyTitleCache").html();
     	  user.selmode=function(){
     		  mode = $("#notifysendMode").val();
     		  if(mode==0){
     			  if($("#notifyTitle").children().length==0){
     				  $("#notifyTitle").append(user.notifyTitle);
     			  };
     			  
     		  }
     		  if(mode==1){
     			  layer.load(5);
     			  location.href=host+"notify_toSms?notifyType=4";
     			  
     		  }
     		  
     	  }
		user.getUsers = function(){
			$.ajax({
				url:host+"notify_getUsers",
				type:"POST",
				dataType:"json",
				success:function(data){
				var html;
				for( var i = 0;i<data.length;i++){
					 html += "<option> "+data[i].name+"</option>";
				}
					$(".user_page select").html(html);
				}
			});
			
		}
		
		
		user.selectVal=function(){
			 var listcount = $("#listcount").val();
     		 if(listcount==0){
     		    layer.alert('列表不能为空！', 8, !1);
     			   return false;
     		   }
				$("#addForm").attr("action",host+"notify_toSms?method=add");
				$("#addForm").submit();
		}
		
		})
		
		function resetSelect(){
			$("#notifyType option:eq(0)").attr("selected","selected");
			
		}
		var smsTemplate = {};
		function getSms(){
			
			$.ajax({
				url:"smsTemplate.json",
				type:"GET",
				dataType:"json",
				cache:false,
				success:function(d){
					smsTemplate=d;
					var template="";
					$.each(d,function(i){
						template+="<option value="+i+">"+d[i].title+"</option>";
					});
					$("#templateTitle").append(template);
				}
		
			});
			}
	getSms();
	function getContent(){
		$("#templateContent").val("");
		var templateIndex=$("#templateTitle").val();
		if(!isNaN(templateIndex)){
			$("#templateContent").val(smsTemplate[templateIndex].content);
		}
		
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">应用管理</a> » <a href="###">系统通知</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>短信</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/notify_selectUser" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}"  >
					        <input name="notifysendMode" type="hidden" value="1"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">选择模板: </td>
						            <td>
						            	<select id="templateTitle" onchange="getContent()" name="notify.title">
						            		<option>--选择模板--</option>
						            	</select>
						            </td>
						          </tr>
						            <tr  id="notifyTitle">
								            <td align="right" width="110px">内容： </td>
								            <td align="left">
								            	<textarea rows="3" name="notify.content" cols="40" style="font-size:13px" id="templateContent"></textarea>
								            </td>
							            </tr>
					          
					            <s:set value="#{9:'---请选择---',0:'指定停车场',1:'指定用户',2:'全部用户'}" var="sendType" ></s:set>
					        	<tr>
						            <td align="right" width="110px">发送对象: </td>
						            <td>
						            
						            	<s:select theme="simple" list="#sendType"  listKey="key" listValue="value" name="notifyType" id="notifyType"  onchange="user.openlayer()"></s:select>
						            </td>
						           </tr>

					        </table>
					        <div class="listbox">
							<div class="listnr" >
					        <s:if test="notifyType!=4">
					        <%--
					        ******判断是否是用户列表start*****
						        --%><s:if test="notifyType==1">
						        
							        <table class="tab2" id="tab">
							        	<thead>
								        	<tr class="tab2yt">
								        		<td >用户名</td>
							            		<td >昵称</td>
							            		<td >姓名</td>
							            		<td>车牌号</td>
							            		<td >注册时间</td>
								        	</tr>
							        	</thead>
							        	<tbody>
							        			<s:iterator value="#session.notifyUsers" var="u">
							        			<tr>
							        				<td>${u.username}</td>
							        				<td>${u.nickname}</td>
							        				<td>${u.realname}</td>
							        				<td>${u.carNumber}</td>
							        				<td class=""><fmt:formatDate value="${u.registerTimeToDate}" pattern="yyyy/MM/dd HH:mm"/></td>
								        		</tr>
							        			</s:iterator>
							        	</tbody>
							        </table>
							        <input type="hidden" id="listcount" value="${sessionScope.notifyUsers.size()}"/>
						       </s:if>
						       <%--
						        *******end*******
					        --%>
					        <%--
					     判断是否是停车场列表 start
					        --%><s:if test="notifyType==0">
						        
							        <table class="tab2" id="tab">
							        	<thead>
								        	<tr class="tab2yt">
								        		<td class="">名称</td>
							            		<td class="">市</td>
							            		<td class="">区</td>
							            		<td class="">地址</td>
								        	</tr>
							        	</thead>
							        	<tbody>
							        			<s:iterator value="#session.notifyCarpark" var="c">
							        			<tr>
							        				<td class="">${c.name}</td>
								            		<td class="">${c.city}</td>
								            		<td class="">${c.county}</td>
								            		<td class="">${c.address}</td>
								        		</tr>
							        			</s:iterator>
							        	</tbody>
							        </table>
					        	<input type="hidden" id="listcount" value="${sessionScope.notifyCarpark.size()}"/>
						       </s:if><%--
						       end
					        --%><div>
					        	<button onclick="user.openlayer()" id="add_but">继续添加</button>
					        </div>
					        </s:if>
							<div class="tijiao">
					        	<input type="submit" value="发送" class="btn1" onclick="return user.selectVal();"/>
					        </div>
					         </div>
					         </div>
						</form>   
							
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<table>
 <tr  id="notifyTitleCache" style="display: none">
	 <td align="right" width="110px">标题： </td>
		<td align="left">
		<input type="text" class="mes_form1" id="name" name="notify.title" value="${notify.title}"
			data-rule="通知标题:required; length[~100, true]"/>
			 </td>
  </tr>
</table>
<div class="user_page" style="display: none">
</div>


 
<script type="text/javascript">
$(".m_notify_l").attr("class","lhover");
</script>
</body>
</html>