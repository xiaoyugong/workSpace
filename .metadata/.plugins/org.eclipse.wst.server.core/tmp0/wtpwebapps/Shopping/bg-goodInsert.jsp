<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="com.wy.domain.BigTypeForm"%>
<%@page import="com.wy.domain.SmallTypeForm"%>
<jsp:useBean id="big" scope="page" class="com.wy.dao.BigTypeDao"/>
<jsp:useBean id="small" scope="page" class="com.wy.dao.SmallTypeDao"/>
<%
List bigList=big.selectBig();
String id=(String)request.getAttribute("bigId");
if(id==null||id.equals("")){			//��idֵΪ��ʱ������idֵΪ-1
id="-1";					//��idֵΪ��ʱ��û��ѡ���κ�һ����Ʒ��������ƣ���˲��������ƷС�������
}


Integer bigId=Integer.valueOf(id);
List smallList=small.selectOneBigId(bigId);
%>
<script language="javascript">
function checkEmpty(form){
for(i=0;i<form.length;i++){
if(form.elements[i].value==""){
alert("����Ϣ����Ϊ��");
return false;
}
}
if(isNaN(document.form.nowPirce.value)){
window.alert("�۸�ֻ��Ϊ����");
return false;
}
}
function ChangeItem(){
var big=form.big.value;
window.location.href="goodsAction.do?action=2&bigId="+big;
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
            <td><div align="center"><strong>�����Ʒ��Ϣ</strong></div></td>
          </tr>
        </table>
        <br>

  <form action="goodsAction.do?action=3" method="post" enctype="multipart/form-data"  name="form" onSubmit="return checkEmpty(form)" >
        <table width="90%"  border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
          <tr>
            <td width="20%" height="26">&nbsp;&nbsp;���������</td>
            <td width="31%" bgcolor="#FFFFFF">&nbsp;&nbsp;
              <select name="big" onChange="ChangeItem()"><option value="">��ѡ��</option>
          <%for(int i=0;i<bigList.size();i++){
            BigTypeForm bigForm=(BigTypeForm)bigList.get(i);
            %>

          <option value="<%=bigForm.getId()%>" <%if(bigId.equals(bigForm.getId())){out.println("selected");}%>>

		  <%=big.selectName(bigForm.getId())%>
		  </option>
            <%}%>
            </select></td>
            <td width="20%">&nbsp;&nbsp;������С���</td>
            <td width="31%" bgcolor="#FFFFFF">&nbsp;
              <select name="small"><option value="">��ѡ��</option>
             <%for(int i=0;i<smallList.size();i++){
               SmallTypeForm smallForm=(SmallTypeForm)smallList.get(i);
               %>
                  <option value="<%=smallForm.getId()%>"><%=smallForm.getSmallName()%></option>

               <%}%>
            </select></td>
          </tr>
          <tr>
            <td height="25">&nbsp;&nbsp;��Ʒ����</td>
            <td bgcolor="#FFFFFF">&nbsp;&nbsp;
            <input name="name" type="text" size="20"></td>
            <td>&nbsp;&nbsp;��������</td>
            <td bgcolor="#FFFFFF">&nbsp;
            <input name="from" type="text" size="20"></td>
          </tr>
          <tr>
            <td height="27">&nbsp;&nbsp;��Ʒ����</td>
            <td bgcolor="#FFFFFF">&nbsp;&nbsp;
            <input name="nowPirce" type="text" size="20"></td>
            <td>&nbsp;&nbsp;��ʼ���ؼ�</td>
            <td bgcolor="#FFFFFF">&nbsp;
            <input name="freePirce" type="hidden" size="20" value="0">0Ԫ</td>
          </tr>
		  
		  <tr>
            <td height="28">&nbsp;&nbsp;��ƷͼƬ</td>
            <td colspan="3" bgcolor="#FFFFFF">&nbsp;&nbsp;
            <input name="formFile" type="file" id="formFile"></td>
          </tr>
		  
		  
          <tr>
            <td height="28">&nbsp;&nbsp;���</td>
            <td colspan="3" bgcolor="#FFFFFF">&nbsp;&nbsp;
            <input name="introduce" type="text" size="50"></td>
          </tr>
        </table><br>
     <input type="image" class="input1"  src="image/save.jpg" width="51" height="20">
&nbsp;&nbsp;
      <a href="#" onClick="javascript:form.reset()"><img src="image/clear.gif"></a>
&nbsp;&nbsp;
      <a href="#" onClick="javasrcipt:history.go(-1)"><img src="image/back.gif"></a>
	  </form>
	
	
	
	
    </td>
  </tr>
</table>
<jsp:include page="bg-down.jsp" flush="true" />























</body>
</html>
