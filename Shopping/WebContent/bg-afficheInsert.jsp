<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<script language="javascript">
function checkEmpty(form){
for(i=0;i<form.length;i++){
if(form.elements[i].value==""){
alert("����Ϣ����Ϊ��");
return false;
}
}
}
</script><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�����̳ǵĺ�̨</title>
</head>
 
<html:html>
<link href="css/css.css" rel="stylesheet" type="text/css">
<body>



<jsp:include page="bg-up.jsp" flush="true"/>
<table width="788" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="170"  valign="top"><jsp:include page="bg-left.jsp" flush="true" /></td>
    <td width="618" align="center" valign="top" bgcolor="#FFFFFF"> <br>
	
        <table width="610" height="25" border="0" cellpadding="0" cellspacing="0" background="image/bg_02.jpg">
          <tr>
            <td><div align="center"><strong>��ӹ�����Ϣ</strong></div></td>
          </tr>
        </table>
        <br>
<html:form action="afficheAction.do?action=2" onsubmit="return checkEmpty(afficheForm)">
        <table width="90%" height="243"  border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
          <tr>
            <td width="23%" height="30"><div align="center">�������</div></td>
            <td width="77%" bgcolor="#FFFFFF"><input name="name" type="text" size="55"></td>
          </tr>
          <tr>
            <td height="208"><div align="center">��������</div>              </td>
            <td bgcolor="#FFFFFF">
            <textarea name="content" cols="45" rows="13" class="textarea"></textarea></td>
          </tr>
      </table>
   <br>
     <input type="image" class="input1"  src="image/save.jpg" width="51" height="20">
&nbsp;&nbsp;
      <a href="#" onClick="javascript:afficheForm.reset()"><img src="image/clear.gif"></a>
&nbsp;&nbsp;
      <a href="#" onClick="javasrcipt:history.go(-1)"><img src="image/back.gif"></a>
       
	 </html:form>

    </td>
  </tr>
</table>
<jsp:include page="bg-down.jsp" flush="true" />



</body>
</html:html>
