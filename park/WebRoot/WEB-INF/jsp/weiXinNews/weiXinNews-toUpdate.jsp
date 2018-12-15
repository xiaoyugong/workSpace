<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.parkbobo.utils.*" %>
<%@include file="../common/taglibs.jsp" %>
 <%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
String path = request.getContextPath();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
String today = df.format(new Date());
DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
String date ="今天是："+ df1.format(new Date());
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String clienturl = Configuration.getInstance().getValue("clienturl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>图文管理|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/plugins/validator/jquery.validator.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/validator/local/zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/page/plugins/lhgcalendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/layer/layer.min.js"></script>
		<script type="text/javascript" src="<%=path %>/page/plugins/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path%>/page/plugins/jquery.parser.js"></script>
	<script type="text/javascript" src="<%=path %>/page/js/common.js" ></script>
	<script type="text/javascript" src="<%=path %>/page/js/index.js" ></script>
<script>
var host='<%=path %>';
var page;
 
 $(function(){
    	
        CKEDITOR.replace('editor01',{
			filebrowserImageUploadUrl:'<%=path %>/upload_uploadImg'
        	
        });

    	
    })
 
 
    
    function uploadThumb(){
	 $("#uploadForm").form("submit",{
			 url:host+"/weiXinMaterial_addThumb",
		 	onSubmit:function(){
		 		$("#message").html("正在上传..");
		 	},
		 	success:function(data){
		 		var json = $.parseJSON(data);
		 		if(json.status){
			 		$("#message").html("上传成功");
			 		$("#thumb").val(json.media);
		 		}else{
			 		$("#message").html("上传失败");
		 			
		 			
		 		}
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
			<div class="fbbox">
				<div class="fb-bt">
					<i>图文管理</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
					<form action="<%=path %>/weiXinMaterial_addThumb" method="post" enctype="multipart/form-data"  id="uploadForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
					        <table width="100%" border="0" class="adduser_tab">
					            <tr>
					            	<td align="right" width="110px">缩略图： </td>
						            <td align="left">
						            	<input type="hidden" name="material.type" value="thumb"/>
						            	<input class="mes_form1" type="file" name="upload" onchange="uploadThumb()"/><span id="message"></span>
						            </td>
					            </tr>
					        </table>
					        
						</form>
						<form action="<%=path %>/weiXinNews_update" method="post" enctype="multipart/form-data"  id="saveForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
					        <table width="100%" border="0" class="adduser_tab">
					        <tr>
					            	<td align="right" width="110px">缩略图： </td>
						            <td align="left">
						            	<input class="mes_form1" readonly="readonly" type="text" value="${news.thumb_media_id}"  name="news.thumb_media_id" id="thumb" value=""/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">标题： </td>
						            <td align="left">
						            	<input class="mes_form1" type="text" name="news.title" value="${news.title}"/>
						            	<input type="hidden" name="news.media_id" value="${news.media_id}"/>
						            	
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">作者： </td>
						            <td align="left">
						            	<input class="mes_form1" type="text" value="微泊科技" name="news.author" value="${news.author}"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">摘要： </td>
						            <td align="left">
						            	<input class="mes_form1" type="text" name="news.digest" value="${news.digest}"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">显示封面： </td>
						            <td align="left">
						            <s:radio list="#{'0':'否','1':'是'}" name="news.show_cover_pic" value="news.show_cover_pic" theme="simple"></s:radio>
						            </td>
						            
					            </tr>
					            <tr>
						            <td align="right" width="110px" valign="top">内容： </td>
						            <td align="left">
										<textarea name="news.content" id="editor01"  style="width: 270px; height: 100px" id="description">${news.content}</textarea>
						            </td>
					            </tr>
					        </table>
					        
							<div class="tijiao">
					        	<input type="submit" value="保存" class="btn1" />
					        </div>
					         <div class="tijiao">
					        		  <a href="javascript:history.go(-1);" ><font color="gray">返回</font></a>
					        </div>
						</form>   	
					</div>
					
					
				</div>
			</div>
		</div>
	</div>
</div>
<div>

</div>
<style>
	#carpark{
		font-size: 15px;
	}
	#carpark thead{
		background:#EDF6FA;
		
	}
	#carpark thead th{
		font-weight:bold;
		width:210px;
		 height:30px;
		 text-align: center;
	}
	#carpark tbody td{
		 text-align: center;
	}
</style>
<div  id="carpark">
</div>
<script type="text/javascript">

</script>
</body>
</html>