/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.36
 * Generated at: 2016-06-30 03:05:25 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.wy.domain.SellGoodsForm;

public final class cart_005fsee_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.wy.domain.SellGoodsForm");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=gb2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      com.wy.dao.GoodsDao dao = null;
      dao = (com.wy.dao.GoodsDao) _jspx_page_context.getAttribute("dao", javax.servlet.jsp.PageContext.PAGE_SCOPE);
      if (dao == null){
        dao = new com.wy.dao.GoodsDao();
        _jspx_page_context.setAttribute("dao", dao, javax.servlet.jsp.PageContext.PAGE_SCOPE);
      }
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n");
      out.write("<title>电子商城</title>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write(" <link href=\"css/css.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-top.jsp", out, true);
      out.write("\r\n");
      out.write("<table width=\"766\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td width=\"207\" bgcolor=\"#F5F5F5\">\r\n");
      out.write("    <!--左侧01-->\r\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-left.jsp", out, true);
      out.write("</td>\r\n");
      out.write("    <td width=\"559\" valign=\"top\" bgcolor=\"#FFFFFF\" align=\"center\">\r\n");
      out.write("    <!--右侧01-->\t\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-goodSorts.jsp", out, true);
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<br><br>\r\n");
      out.write("\t<strong>我的购物车</strong>\t<div align=\"center\"><br>\r\n");
      out.write("              ");
if(session.getAttribute("cart")==null){
      out.write("\r\n");
      out.write("           您还没有购物！！！\r\n");
      out.write("           ");
}else{
      out.write("\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("        <form method=\"post\" action=\"cart_modify.jsp\" name=\"form\">\r\n");
      out.write("\t\t  <table width=\"92%\"  border=\"1\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#FFFFFF\" bordercolordark=\"#819BBC\" bordercolorlight=\"#FFFFFF\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td width=\"16%\" height=\"28\"><div align=\"center\">序号</div></td>\r\n");
      out.write("            <td width=\"23%\"><div align=\"center\">商品的名称</div></td>\r\n");
      out.write("            <td width=\"22%\"><div align=\"center\">商品价格</div></td>\r\n");
      out.write("            <td width=\"22%\"><div align=\"center\">商品数量</div></td>\r\n");
      out.write("            <td width=\"17%\"><div align=\"center\">总金额</div></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("            ");

            float sum=0;
        Vector cart=(Vector)session.getAttribute("cart");
        for(int i=0;i<cart.size();i++){
          SellGoodsForm form=(SellGoodsForm)cart.elementAt(i);
          sum=sum+form.number*form.price;
          System.out.print("sum="+sum);
        
      out.write("\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td height=\"28\"><div align=\"center\">");
      out.print(i+1);
      out.write("</div></td>\r\n");
      out.write("            <td><div align=\"center\">");
      out.print(dao.selectOneGoods(new Integer(form.ID)).getName());
      out.write("</div></td>\r\n");
      out.write("            <td><div align=\"center\">");
      out.print(form.price);
      out.write("元</div></td>\r\n");
      out.write("            <td><div align=\"center\"><input name=\"num");
      out.print(i);
      out.write("\" size=\"7\" type=\"text\"  value=\"");
      out.print(form.number);
      out.write("\" onBlur=\"check(this.form)\"></div></td>\r\n");
      out.write("            <td><div align=\"center\">");
      out.print(form.number*form.price);
      out.write("元</div></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("\t\t     <script language=\"javascript\">\r\n");
      out.write("\t\t\t<!--\r\n");
      out.write("\t\t\tfunction check(myform){\r\n");
      out.write("\t\t\t\tif(isNaN(myform.num");
      out.print(i);
      out.write(".value) || myform.num");
      out.print(i);
      out.write(".value.indexOf('.',0)!=-1){\r\n");
      out.write("\t\t\t\t\talert(\"请不要输入非法字符\");myform.num");
      out.print(i);
      out.write(".focus();return;}\r\n");
      out.write("\t\t\t\tif(myform.num");
      out.print(i);
      out.write(".value==\"\"){\r\n");
      out.write("\t\t\t\t\talert(\"请输入修改的数量\");myform.num");
      out.print(i);
      out.write(".focus();return;}\r\n");
      out.write("\t\t\t\tmyform.submit();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t-->\r\n");
      out.write("\t\t</script>\r\n");
      out.write("          ");
}
      out.write("\r\n");
      out.write("        </table>\r\n");
      out.write("\r\n");
      out.write("        </form>\r\n");
      out.write("\r\n");
      out.write("<table width=\"100%\" height=\"52\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("      <tr align=\"center\" valign=\"middle\">\r\n");
      out.write("\t\t<td height=\"10\">&nbsp;\t\t</td>\r\n");
      out.write("        <td width=\"24%\" height=\"10\" colspan=\"-3\" align=\"left\">&nbsp;</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("      <tr align=\"center\" valign=\"middle\">\r\n");
      out.write("        <td height=\"21\" class=\"tableBorder_B1\">&nbsp;</td>\r\n");
      out.write("        <td height=\"21\" colspan=\"-3\" align=\"left\" >合计总金额：￥");
      out.print(sum);
      out.write("</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr align=\"center\" valign=\"middle\">\r\n");
      out.write("        <td height=\"21\" colspan=\"2\"> <a href=\"index.jsp\">继续购物</a> | <a href=\"cart_checkOut.jsp\">去收银台结账</a> | <a href=\"cart_clear.jsp\">清空购物车</a> | <a href=\"#\">修改数量</a></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
}
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("</td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-down.jsp", out, true);
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}