<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="com.wy.domain.GoodsForm"%>
<jsp:useBean id="big" scope="page" class="com.wy.dao.BigTypeDao"/>
<jsp:useBean id="small" scope="page" class="com.wy.dao.SmallTypeDao"/>
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
 function deleteType(date) {
  if(confirm("ȷ��Ҫɾ����")){
    window.location="bigTypeAction.do?action=3&id="+date;
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
            <td><div align="center"><strong>��Ʒ������ѯ</strong></div></td>
          </tr>
        </table>
        <br>
		  <table width="90%"  border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
        <tr>
          <td width="14%" height="25"><div align="center">���ݱ��</div></td>
          <td width="19%"><div align="center">��Ʒ����</div></td>
          <td width="14%"><div align="center">�����</div></td>
          <td width="14%"><div align="center">С���</div></td>
          <td width="14%"><div align="center">�Ƿ��ؼ�</div></td>
          <td width="25%"><div align="center">����</div></td>
        </tr>
		     <%for(int i=start;i<over;i++){
            GoodsForm form=(GoodsForm)list.get(i);

      %>
        <tr bgcolor="#FFFFFF">
          <td height="30"><div align="center"><%=form.getId()%></div></td>
          <td><div align="center"><a href="<%=form.getPriture()%>" target="_blank"><%=form.getName()%></a></div></td>

          <td><div align="center"><a href="goodsAction.do?action=9&big=<%=form.getBig()%>"><%=big.selectName(form.getBig())%></a></div></td>

          <td><div align="center"><a href="goodsAction.do?action=8&small=<%=form.getSmall()%>"><%=small.selectName(form.getSmall())%></a></div></td>
          <td><div align="center"><a href="goodsAction.do?action=7&mark=<%=form.getMark()%>"><%if(form.getMark().toString().equals("0")){%>��<%}else{%>��<%}%></a></div></td>
          <td><div align="center"><a href="goodsAction.do?action=5&id=<%=form.getId()%>">��ϸ��Ϣ</a>&nbsp;&nbsp;
		 
		 <a href="goodsAction.do?action=6&id=<%=form.getId()%>">ɾ��</a>
		 
		  </div></td>
          <%}%>  </tr>
      </table>
	  <br>
	  <table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr align="center">
          <td width="13%">��Ϊ<%=maxPage%>ҳ</td>
          <td width="16%">����<%=pageNumber%>����¼</td>
          <td width="14%">��ǰΪ��<%=number+1%>ҳ</td>
          <td width="19%"><%if((number+1)==1){%>  
	  ��һҳ  
        <%}else{%>
		
		<a href="goodsAction.do?action=0&i=<%=number-1%>">
		  ��һҳ</a></td>
          <%}%>
          <td width="18%"><%if(maxPage<=(number+1)){%>
            ��һҳ
              <%}else{%>
	 
		<a href="goodsAction.do?action=0&i=<%=number+1%>">��һҳ</a>
<%}%>


          <td width="20%">
            <div align="right"><img src="image/bg-add.gif" width="9" height="9">&nbsp;<a href="bg-goodInsert.jsp">�����Ʒ</a>&nbsp;</div></td>

        </tr>
      </table>
	
    </td>
  </tr>
</table>
<jsp:include page="bg-down.jsp" flush="true" />

















</body>
</html>
