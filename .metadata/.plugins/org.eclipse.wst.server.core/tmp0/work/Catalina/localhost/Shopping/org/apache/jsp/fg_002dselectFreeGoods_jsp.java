/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.36
 * Generated at: 2016-06-30 03:02:09 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.wy.domain.GoodsForm;

public final class fg_002dselectFreeGoods_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.wy.domain.GoodsForm");
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
      com.wy.dao.GoodsDao goods = null;
      goods = (com.wy.dao.GoodsDao) _jspx_page_context.getAttribute("goods", javax.servlet.jsp.PageContext.PAGE_SCOPE);
      if (goods == null){
        goods = new com.wy.dao.GoodsDao();
        _jspx_page_context.setAttribute("goods", goods, javax.servlet.jsp.PageContext.PAGE_SCOPE);
      }
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n");
      out.write("<title>电子商城</title>\r\n");
      out.write("</head>\r\n");
      out.write("<link href=\"css/css.css\" rel=\"stylesheet\" type=\"text/css\"> \r\n");
List freeList =(List)request.getAttribute("list");
      out.write('\r');
      out.write('\n');

int number=Integer.parseInt((String)request.getAttribute("number"));
int maxPage=Integer.parseInt((String)request.getAttribute("maxPage"));
int pageNumber=Integer.parseInt((String)request.getAttribute("pageNumber"));
int start=number*4;//开始条数
int over=(number+1)*4;//结束条数
int count=pageNumber-over;//还剩多少条记录
if(count<=0){
  over=pageNumber;
  }

      out.write(" \r\n");
      out.write("<body>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-top.jsp", out, true);
      out.write("\r\n");
      out.write("<table width=\"766\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td width=\"207\" valign=\"top\" bgcolor=\"#F5F5F5\">\r\n");
      out.write("    <!--左侧01-->\r\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-left.jsp", out, true);
      out.write("</td>\r\n");
      out.write("    <td width=\"559\" valign=\"top\" bgcolor=\"#FFFFFF\" align=\"center\">\r\n");
      out.write("    \t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-goodSorts.jsp", out, true);
      out.write("\t\r\n");
      out.write("    <table width=\"546\" height=\"42\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" background=\"image/fg_right02.jpg\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td>&nbsp;</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t      ");

          
             
              for(int i=start;i<over;i++)
              {
                GoodsForm freeGoods=(GoodsForm)freeList.get(i);
                
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t \r\n");
      out.write("\t\t\t <br> <table width=\"99%\"  border=\"1\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#FFFFFF\" bordercolorlight=\"#FFFFFF\" bordercolordark=\"#819BBC\">\r\n");
      out.write("\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td width=\"36%\" rowspan=\"5\" height=\"120\"><div align=\"center\">\r\n");
      out.write("                  <input name=\"pricture");
      out.print(i);
      out.write("\" type=\"image\" src=\"");
      out.print(freeGoods.getPriture());
      out.write("\" width=\"140\" height=\"126\">\r\n");
      out.write("                </div></td>\r\n");
      out.write("                <td width=\"64%\" height=\"30\"><div align=\"center\">");
      out.print(freeGoods.getName());
      out.write("</div></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"30\"><div align=\"center\" style=\"text-decoration:line-through;color:#FF0000\">原价：");
      out.print(freeGoods.getNowPrice());
      out.write("元</div></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("\t\t\t     <tr>\r\n");
      out.write("                <td height=\"30\"><div align=\"center\">特价：");
      out.print(freeGoods.getFreePrice());
      out.write("元</div></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td height=\"30\"><div align=\"center\">");
      out.print(freeGoods.getIntroduce());
      out.write("</div></td>\r\n");
      out.write("              </tr>\r\n");
      out.write("              <tr>\r\n");
      out.write("               <td height=\"30\" align=\"center\">  ");
if(session.getAttribute("form")!=null||session.getAttribute("id")!=null){
      out.write("\r\n");
      out.write("               <a href=\"#\" onClick=\"window.open('goodsAction.do?action=16&id=");
      out.print(freeGoods.getId());
      out.write("','','width=500,height=200');\">查看详细内容</a>\t\t\t\t\r\n");
      out.write("\t\t\t  ");
}else{
      out.write("登录后才能购买");
}
      out.write("</td>\r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("\t\t\t");
}
      out.write("<br>\r\n");
      out.write("        \r\n");
      out.write("\t\t    <div align=\"center\">\r\n");
      out.write("\t\t      <table width=\"90%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("                <tr align=\"center\">\r\n");
      out.write("                  <td width=\"13%\">共为");
      out.print(maxPage);
      out.write("页</td>\r\n");
      out.write("                  <td width=\"18%\">共有");
      out.print(pageNumber);
      out.write("条记录</td>\r\n");
      out.write("                  <td width=\"26%\">当前为第");
      out.print(number+1);
      out.write("页</td>\r\n");
      out.write("                  <td width=\"15%\">");
if((number+1)==1){
      out.write("\r\n");
      out.write("      上一页\r\n");
      out.write("        ");
}else{
      out.write("\r\n");
      out.write("        <a href=\"goodsAction.do?action=15&mark=1&i=");
      out.print(number-1);
      out.write("\">上一页</a></td>\r\n");
      out.write("                  ");
}
      out.write("\r\n");
      out.write("                  <td width=\"14%\">");
if(maxPage<=(number+1)){
      out.write("\r\n");
      out.write("      下一页\r\n");
      out.write("        ");
}else{
      out.write("\r\n");
      out.write("        <a href=\"goodsAction.do?action=15&mark=1&i=");
      out.print(number+1);
      out.write("\">下一页</a></td>\r\n");
      out.write("                  ");
}
      out.write("<td width=\"14%\"><div align=\"center\"><a href=\"#\" onClick=\"javasrcipt:history.go(-1);\">返回</a></div></td>\r\n");
      out.write("                </tr>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("              </table>\t</td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "fg-down.jsp", out, true);
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
