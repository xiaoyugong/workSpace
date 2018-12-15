<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*"%>
<%@ page import="com.wy.domain.OrderDetailForm"%>
<%@ page import="com.wy.domain.OrderForm"%>
<%@ page import="com.wy.domain.MemberForm"%>
<jsp:useBean id="order" scope="page" class="com.wy.dao.OrderDao"/>
<jsp:useBean id="orderDetail" scope="page" class="com.wy.dao.OrderDetailDao"/>
<jsp:useBean id="goodsDao" scope="page" class="com.wy.dao.GoodsDao"/>
<jsp:useBean id="member" scope="page" class="com.wy.dao.MemberDao"/>
<%MemberForm form=(MemberForm)session.getAttribute("form");%>
<%MemberForm memberForm=member.selectOneMember(form.getId());%>
<%List orderList=order.selectOrderHead(memberForm.getName());%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�����̳�</title>
</head>
 <link href="css/css.css" rel="stylesheet" type="text/css">
<body>











<jsp:include page="fg-top.jsp" flush="true"/>
<table width="766" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="207" bgcolor="#F5F5F5">
    <!--���01-->
    <jsp:include page="fg-left.jsp" flush="true"/></td>
    <td width="559" valign="top" bgcolor="#FFFFFF" align="center">
    <!--�Ҳ�01-->	
	<jsp:include page="fg-goodSorts.jsp" flush="true"/>	
	
	<br><br>
	<strong>�����鿴</strong>
	 <br>	 <br>	 <br>
              <%if(orderList.size()==0){%>
     û�ж�����Ϣ
     <%}else{%>

		  <table width="95%"  border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolorlight="#FFFFFF" bordercolordark="#819BBC">

       <tr align="center">
            <td width="15%" height="21">���</td>

            <td width="15%">�绰</td>
            <td width="21%">��ַ</td>

            <td width="19%">����ʱ��</td>
            <td width="18%">�Ƿ����</td> <td width="12%">����</td>
          </tr>
      <%
      for(int orderNumber=0;orderNumber<orderList.size();orderNumber++){
        OrderForm orderForm=(OrderForm)orderList.get(orderNumber);
        %>
          <tr align="center">
            <td height="24"><%=orderForm.getNumber()%></td>

            <td><%=orderForm.getTel()%></td>
            <td><%=orderForm.getAddress()%></td>

            <td><%=orderForm.getCreaTime()%></td>
            <td><%if(orderForm.getSign().equals("0")){%>
            ��<%}else{%>��<%}%>
            </td>
             <td><a href="cart_detail.jsp?number=<%=orderForm.getNumber()%>">�鿴��ϸ</a></td>
          </tr>

          <%}%>
      </table>
      <div align="center">

		   <br>
	      <%if(request.getParameter("number")!=null){%>

	      <br>
	      ������ϸ��ѯ
	      <br>
<br>

      <table width="89%"  border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolorlight="#FFFFFF" bordercolordark="#819BBC">

        <tr align="center">
          <td width="25%" height="21">���</td>
          <td width="24%">��Ʒ����</td>
          <td width="28%">��Ʒ�۸�</td>
          <td width="23%">��Ʒ����</td>
        </tr>  <%
        float sum=0;
            List orderDetailList=orderDetail.selectOrderDetailNumber(request.getParameter("number"));
        for(int orderDetailNumber=0;orderDetailNumber<orderDetailList.size();orderDetailNumber++){
         OrderDetailForm orderDetailForm=(OrderDetailForm)orderDetailList.get(orderDetailNumber);

         sum=sum+orderDetailForm.getPrice()*orderDetailForm.getNumber();
         %>
        <tr  align="center" >
          <td height="21"><%=orderDetailForm.getOrderNumber()%></td>
          <td><%=goodsDao.selectOneGoods(orderDetailForm.getGoodsId()).getName()%></td>
          <td><%=orderDetailForm.getPrice()%>Ԫ</td>
          <td><%=orderDetailForm.getNumber()%></td>
        </tr>
        <%}%>
      </table>

	   <table width="89%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td height="24"><div align="right">�ܽ�<%=sum%>Ԫ</div></td>
         </tr>
       </table> <%}%>
      </div>
      <%}%>	
	
	
</td>
  </tr>
</table>

<jsp:include page="fg-down.jsp" flush="true"/>























</body>
</html>
