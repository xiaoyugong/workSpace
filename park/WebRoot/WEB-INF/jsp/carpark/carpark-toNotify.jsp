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
	<script type="text/javascript" src="<%=path %>/page/plugins/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
    <script type="text/javascript">
    $(function(){
    	
        CKEDITOR.replace('editor01',{
			filebrowserImageUploadUrl:'<%=path %>/upload_uploadImg'
        	
        });

    	
    })
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
				$("#addForm").attr("action",host+"notify_add?method=add");
				$("#addForm").submit();
		}
		
		})
		
		function resetSelect(){
			$("#notifyType option:eq(0)").attr("selected","selected");
			
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
				<div class="z-r-b-l"><i>位置：</i><a href="###">停车场管理</a> » <a href="###">停车场</a></div>
				<div class="z-r-b-r"><%=date %></div>
			</div>
			<div class="fbbox">
				<div class="fb-bt">
					<i>发送通知</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/carpark_sendNotify" method="post" id="addForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}"  >
					        <table width="100%" border="0" class="adduser_tab">
					        <tr>
						            <td align="right" width="110px" valign="top">发送对象： </td>
						            <td align="left">
										<input name="carpark.username" style="border:0" type="text" class="mes_form1" readonly="readonly" value="${carpark.username}"/>
						            
						            </td>
					            </tr>
							        <tr  id="notifyTitle">
								            <td align="right" width="110px">标题： </td>
								            <td align="left">
								            	<input type="text" class="mes_form1" id="name" name="notify.title" value="${notify.title}"
								            	 	data-rule="通知标题:required; length[~100, true]"/>
								            </td>
							          </tr>
						            
						           
					        	<tr>
						            <td align="right" width="110px" valign="top">内容： </td>
						            <td align="left">
										<textarea name="notify.content" id="editor01"  style="width: 270px; height: 100px" id="description">${notify.content}</textarea>
						            </td>
					            </tr>
					          
					           
							        </table>
						     <div>
					        </div>
							<div class="tijiao">
					        	<input type="submit" value="发送" class="btn1" />
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