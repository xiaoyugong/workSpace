<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="com.wy.domain.OrderForm"%>
<%
List list=(List)request.getAttribute("list");
int number=Integer.parseInt((String)request.getAttribute("number"));
int maxPage=Integer.parseInt((String)request.getAttribute("maxPage"));
int pageNumber=Integer.parseInt((String)request.getAttribute("pageNumber"));
int start=number*6;//��ʼ����
int over=(number+1)*6;//��������
int count=pageNumber-over;//��ʣ��������¼
if(count<=0){
  over=pageNumber;
  }
%>
 <script Language="JavaScript">
 function deleteOrder(date) {
  if(confirm("ȷ��Ҫɾ����")){
    window.location="orderAction.do?action=2&number="+date;
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
    <td width="618" align="center" valign="top" bgcolor="#FFFFFF">
	
	
	
<br>
	

	<table width="610" height="25" border="0" cellpadding="0" cellspacing="0" background="image/bg_02.jpg">
      <tr>
        <td><div align="center"><strong>�����Ĳ�ѯ</strong></div></td>
      </tr>
    </table>	<br>
	    <table width="96%"  border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="CCCCCC">

	    <tr bgcolor="#DCDCDC">
          <td width="15%" height="25"><div align="center">���</div></td>
          <td width="14%"><div align="center">��ʵ����</div></td>

         <td width="16%"><div align="center">�Ƿ����</div></td>
        <td width="26%"><div align="center">����ʱ��</div></td>
   <td width="29%"><div align="center">����</div></td>
        </tr>
        <%for(int i=start;i<over;i++){
      OrderForm form=(OrderForm)list.get(i);
          %>
        <tr align="center" bgcolor="#FFFFFF">
          <td height="25"><%=form.getNumber()%></td>
          <td><%=form.getReallyName()%></td>

		  <td><%if(form.getSign().equals("0")){%>
                    <a href="orderAction.do?action=0&sign=<%=form.getSign()%>">��</a>
                    <%}else{%>
                    <a href="orderAction.do?action=0&sign=<%=form.getSign()%>">��</a>
          <%}%></td>
		  <td><%=form.getCreaTime()%></td>
        <td><a href="orderAction.do?action=3&number=<%=form.getNumber()%>">��ϸ��Ϣ</a>
                             &nbsp;&nbsp;
	     <%if(form.getSign().equals("1")){%>
		 �ѳ�
		 <%}else if(request.getParameter("sign")==null){%>
		  <a href="orderAction.do?action=1&number=<%=form.getNumber()%>">����</a>
		 <%}else{%>
		  <a href="orderAction.do?action=1&sign=<%=request.getParameter("sign")%>&number=<%=form.getNumber()%>">����</a>
		 <%}%>
		 &nbsp;&nbsp;

		 <a href="javascript:deleteOrder('<%=form.getNumber()%>')">ɾ��</a></td>
        </tr>
        <%}%>
      </table><br>
<%if(request.getParameter("sign")==null){%>
  <table width="96%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr align="center">
    <td width="17%">��Ϊ<%=maxPage%>ҳ</td>
    <td width="22%">����<%=pageNumber%>����¼</td>
    <td width="26%">��ǰΪ��<%=number+1%>ҳ</td>
    <td width="19%"><%if((number+1)==1){%> ��һҳ<%}else{%><a href="orderAction.do?action=0&i=<%=number-1%>">��һҳ</a></td><%}%>
    <td width="16%"><%if(maxPage<=(number+1)){%>��һҳ<%}else{%><a href="orderAction.do?action=0&i=<%=number+1%>">��һҳ</a></td><%}%>
   </tr>
 </table>
<%}else{%>
 <table width="96%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr align="center">
    <td width="14%">��Ϊ<%=maxPage%>ҳ</td>
    <td width="18%">����<%=pageNumber%>����¼</td>
    <td width="22%">��ǰΪ��<%=number+1%>ҳ</td>
    <td width="16%"><%if((number+1)==1){%> ��һҳ<%}else{%><a href="orderAction.do?action=0&i=<%=number-1%>&sign=<%=request.getParameter("sign")%>">��һҳ</a></td><%}%>
    <td width="12%"><%if(maxPage<=(number+1)){%>��һҳ<%}else{%><a href="orderAction.do?action=0&i=<%=number+1%>&sign=<%=request.getParameter("sign")%>">��һҳ</a></td><%}%>
    <td width="18%"><a href="orderAction.do?action=0">����</a></td>
   </tr>
 </table>


<%}%>	</td>
  </tr>
</table><jsp:include page="bg-down.jsp" flush="true" />

</body>
</html>
