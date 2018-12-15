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
	<title>app活动|泊泊停车</title>
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/common.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/base.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/page/css/fabu.css" />
	<script type="text/javascript" src="<%=path %>/page/plugins/jquery-1.8.2.min.js"></script>
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
    </script>
	
<script>

</script>
</head>
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
			<div class="fbbox">
				<div class="fb-bt">
					<i>app活动${appActivity==null?'添加':'修改'}</i>
				</div>
				<div class="fb-nr">
					<div class="adduser_form">
						<form action="<%=path %>/${appActivity==null?'appActivity_add':'appActivity_update'}" method="post" enctype="multipart/form-data"  id="saveForm" autocomplete="off" 
							data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
							<input type="hidden" name="appActivity.activityid" value="${appActivity.activityid}"/>
					        <table width="100%" border="0" class="adduser_tab">
					        	<tr>
						            <td align="right" width="110px">标题： </td>
						            <td align="left" >
						            	<input type="text" class="mes_form1" id="name" name="appActivity.title" value="${appActivity.title}"
						            	 	data-rule="通知标题:required; length[~100, true]"/>
						            </td>
					            </tr>
					            <tr>
						            <td align="right" width="110px">备注： </td>
						            <td align="left">
						            	<input type="text" class="mes_form1" id="memo" name="appActivity.memo" value="${appActivity.memo}"
						            	 	data-rule="备注:required; length[~200, true]"/>
						            </td>
					            </tr>
					            <tr>
					            	<td align="right" width="110px">图片： </td>
						            <td align="left">
						            <div>
						            	<c:if test="${appActivity.imgurl!=null}">
							            	<img src="<%=clienturl %>${appActivity.imgurl}" alt=""  width="60px" height="60px" />
						            	</c:if>
						            </div>
						            <div>
						            	<input type="file" class="mes_form1" id="memo" name="upimg"
						            	 	 />
						            </div>
						            	 	<input type="hidden" name="appActivity.imgurl" value="${appActivity.imgurl}"/>
						            </td>
					            </tr>
					        	<tr>
						            <td align="right" width="110px" valign="top">详情： </td>
						            <td align="left"  >
						            <textarea cols="80" id="editor01" name="appActivity.description" rows="10">${appActivity.description}</textarea>
						            </td>
					            </tr>
					        	
					        </table>
					        
							<div class="tijiao">
					        	<input type="submit" value="保存" class="btn1" />
					        </div>
						</form>   	
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

 
</div>

<script type="text/javascript">
$(document).ready(function(){
	$(".m_appActivity_l").attr("class","lhover");
	});
</script>
</body>
</html>