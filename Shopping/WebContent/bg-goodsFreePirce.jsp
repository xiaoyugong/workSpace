<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="com.wy.domain.BigTypeForm"%>
<%@page import="com.wy.domain.SmallTypeForm"%>
<%@page import="com.wy.domain.GoodsForm"%>
<jsp:useBean id="big" scope="page" class="com.wy.dao.BigTypeDao"/>
<jsp:useBean id="small" scope="page" class="com.wy.dao.SmallTypeDao"/>
<%
GoodsForm goodsForm=(GoodsForm)request.getAttribute("form");

%>

<script language="javascript">
function checkEmpty(form){
for(i=0;i<form.length;i++){
if(form.elements[i].value==""){
alert("����Ϣ����Ϊ��");
return false;
}
}
if(isNaN(document.form.free.value)){
window.alert("�۸�ֻ��Ϊ����");
return false;
}
if(document.form.free.value==document.form.now.value){
window.alert("�������ؼ���Ʒ�ļ۸�Ӧ����ԭ���ļ۸��ǵȼ۵ģ����������룡����");
return false;
}
if(document.form.free.value-document.form.now.value>0){


window.alert("�������ؼ���Ʒ�ļ۸�Ӧ�ñ�ԭ���ļ۸�ߣ����������룡����");
return false;
}

}

</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�����̳ǵĺ�̨</title>
</head>
 <link href="css/css.css" rel="stylesheet" type="text/css">
<body>


<jsp:include page="bg-up.jsp" flush="true"/>
<table width="788" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="170"  valign="top"><jsp:include page="bg-left.jsp" flush="true" /></td>
    <td width="618" align="center" valign="top" bgcolor="#FFFFFF"> <br>
	
        <table width="610" height="25" border="0" cellpadding="0" cellspacing="0" background="image/bg_02.jpg">
          <tr>
            <td><div align="center"><strong>�����ؼ���Ʒ</strong></div></td>
          </tr>
        </table>
        <br>
		
		 <form name="form" method="post" action="goodsAction.do?action=11&id=<%=goodsForm.getId()%>&mark=1" onSubmit="return checkEmpty(form)">
        <table width="90%" height="233"  border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
          <tr>
            <td width="20%" height="26">&nbsp;&nbsp;���������</td>
            <td width="31%" bgcolor="#FFFFFF">&nbsp;&nbsp;<%=big.selectName(goodsForm.getBig())%></td>
            <td width="20%">&nbsp;&nbsp;������С���</td>
            <td width="31%" bgcolor="#FFFFFF">&nbsp;&nbsp;<%=small.selectName(goodsForm.getSmall())%></td>
          </tr>
          <tr>
            <td height="26">&nbsp;&nbsp;��Ʒ����</td>
            <td bgcolor="#FFFFFF">&nbsp;&nbsp;<%=goodsForm.getName()%></td>
            <td>&nbsp;&nbsp;��������</td>
            <td bgcolor="#FFFFFF">&nbsp;&nbsp;<%=goodsForm.getFrom()%></td>
          </tr>
          <tr>
            <td height="26">&nbsp;&nbsp;��Ʒ����</td>
            <td bgcolor="#FFFFFF">&nbsp;&nbsp;
            <input name="now" type="hidden" value="<%=goodsForm.getNowPrice()%>"><%=goodsForm.getNowPrice()%>Ԫ</td>
            <td>&nbsp;&nbsp;�Ƿ��ؼ�</td>
            <td bgcolor="#FFFFFF">&nbsp;&nbsp;
            <%if(goodsForm.getMark().toString().equals("0")){%>��<%}else{%>��<%}%></td>
          </tr>
          <tr>
            <td height="23">&nbsp;&nbsp;��Ʒ����</td>
            <td bgcolor="#FFFFFF" >&nbsp;&nbsp;<%=goodsForm.getIntroduce()%></td>
		    <td>&nbsp;&nbsp;�ؼۼ۸�</td>
            <td bgcolor="#FFFFFF">
            &nbsp;
              <input name="free" type="text" size="12" >
            <input type="submit" name="Submit" value="�ύ">           </td>
          </tr>
          <tr>
            <td height="79">&nbsp;&nbsp;��ƷͼƬ</td>
            <td colspan="3" bgcolor="#FFFFFF">&nbsp;&nbsp;
            <input name="imageField" type="image" src="<%=goodsForm.getPriture()%>" width="140" height="126"></td>
          </tr>
        </table>  </form>              
        <table width="90%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="87%" height="29" align="right">&nbsp;&nbsp;&nbsp;</td>
            <td width="13%"> <a href="javascript:history.back();">����</a></td>
          </tr>
        </table>
		
		
	
    </td>
  </tr>
</table>
<jsp:include page="bg-down.jsp" flush="true" />





































</body>
</html>
