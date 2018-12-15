<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="com.wy.domain.OrderForm"%>
<%@page import="com.wy.domain.OrderDetailForm"%>
<%@page import="com.wy.domain.GoodsForm"%>
<jsp:useBean id="order" scope="page" class="com.wy.dao.OrderDao"/>
<jsp:useBean id="goods" scope="page" class="com.wy.dao.GoodsDao"/>
<jsp:useBean id="orderDetail" scope="page" class="com.wy.dao.OrderDetailDao"/>
<%OrderForm orderForm=(OrderForm)request.getAttribute("orderForm");%>
<%List list=(List)request.getAttribute("orderDetailList");%>
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
            <td align="center"><strong>������Ϊ��<%=orderForm.getNumber()%>����ϸ��Ϣ</strong></td>
          </tr>
        </table>
        <br>

<table width="76%"  border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#DCDCDC">
      <tr align="center" >
        <td width="26%" height="25">��Ա�˺�</td>
        <td width="22%" bgcolor="#FFFFFF"><%=orderForm.getName()%></td>
        <td width="26%">��Ա����</td>
        <td width="22%" bgcolor="#FFFFFF"><%=orderForm.getReallyName()%></td>
      </tr>
      <tr  align="center">
        <td height="25">�ͻ��绰</td>
        <td bgcolor="#FFFFFF"><%=orderForm.getTel()%></td>
        <td>�ͻ���ַ</td>
        <td bgcolor="#FFFFFF"><%=orderForm.getAddress()%></td>
      </tr>
      <tr  align="center">
        <td height="25">���ʽ</td>
        <td bgcolor="#FFFFFF"><%=orderForm.getSetMoney()%></td>
        <td>���ͷ�ʽ</td>
        <td bgcolor="#FFFFFF"><%=orderForm.getPost()%></td>
      </tr>
      <tr align="center">
        <td height="25" >��ע��Ϣ</td>
        <td bgcolor="#FFFFFF"><%=orderForm.getBz()%></td>
        <td>����ʱ��</td>
        <td bgcolor="#FFFFFF"><%=orderForm.getCreaTime()%></td>
      </tr>
    </table>
	<br>
	    <strong>��Ʒ��ϸ��Ϣ</strong>	  <br><br>
	    <table width="74%"  border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#DCDCDC">
          <tr>
            <td><div align="center">��Ʒ����</div></td>
            <td><div align="center">��Ʒ����</div></td>
            <td><div align="center">��Ʒ�۸�</div></td>
          </tr>
          <%
          float sum=0;
          for(int i=0;i<list.size();i++){
            OrderDetailForm form=(OrderDetailForm)list.get(i);
            sum=sum+form.getPrice()*form.getNumber();
       
            %>
          <tr bgcolor="#FFFFFF">
            <td><div align="center"><%=goods.selectOneGoods(form.getGoodsId()).getName()%></div></td>
            <td><div align="center"><%=form.getNumber()%></div></td>
            <td><div align="center"><%=form.getPrice()%>Ԫ</div></td>
          </tr><%}%>
      </table>
	    <br>
    �ܽ��:<%=sum%><br>
    <table width="76%" height="19"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="72%">
              <div align="left">
                <%if(orderForm.getSign().equals("1")){%>
                �Ѿ�����
                <%}else{%>
                û�г���
                <%}%>
              </div></td><td width="28%"><div align="right"><a href="javascript:history.go(-1)">����</a></div></td>
      </tr>
    </table>



    </td>
  </tr>
</table>

<jsp:include page="bg-down.jsp" flush="true" />

</body>
</html>
