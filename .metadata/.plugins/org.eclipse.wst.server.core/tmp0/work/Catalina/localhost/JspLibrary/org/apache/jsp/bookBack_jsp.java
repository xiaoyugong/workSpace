/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.36
 * Generated at: 2016-06-29 11:57:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import com.dao.BorrowDAO;
import com.actionForm.BorrowForm;
import com.actionForm.ReaderForm;
import java.util.*;
import com.core.ChStr;
import com.dao.ManagerDAO;
import com.actionForm.ManagerForm;

public final class bookBack_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/navigation.jsp", Long.valueOf(1466159598000L));
    _jspx_dependants.put("/copyright.jsp", Long.valueOf(1466159598000L));
    _jspx_dependants.put("/banner.jsp", Long.valueOf(1466159598000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.core.ChStr");
    _jspx_imports_classes.add("com.dao.ManagerDAO");
    _jspx_imports_classes.add("com.actionForm.BorrowForm");
    _jspx_imports_classes.add("com.actionForm.ManagerForm");
    _jspx_imports_classes.add("com.dao.BorrowDAO");
    _jspx_imports_classes.add("com.actionForm.ReaderForm");
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");

ReaderForm readerForm=(ReaderForm)request.getAttribute("readerinfo");
Collection coll=(Collection)request.getAttribute("borrowinfo");

      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<title>图书馆管理系统</title>\r\n");
      out.write("<link href=\"CSS/style.css\" rel=\"stylesheet\">\r\n");
      out.write("\t<script language=\"javascript\">\r\n");
      out.write("\t\tfunction checkreader(form){\r\n");
      out.write("\t\t\tif(form.barcode.value==\"\"){\r\n");
      out.write("\t\t\t\talert(\"请输入读者条形码!\");form.barcode.focus();return;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tform.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onLoad=\"clockon(bgclock)\">\r\n");
      out.write("\r\n");
      out.write("\r\n");

ChStr chStr=new ChStr();
String manager=(String)session.getAttribute("manager");
//验证用户是否登录
if (manager==null || "".equals(manager)){
	response.sendRedirect("login.jsp");
}

      out.write("\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<table width=\"778\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td height=\"118\" valign=\"top\" background=\"Images/top_bg.gif\" bgcolor=\"#EEEEEE\"><table width=\"100%\" height=\"33\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"81%\" height=\"10\"></td>\r\n");
      out.write("        <td colspan=\"2\"></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"20\">&nbsp;</td>\r\n");
      out.write("        <td width=\"10%\"><a href=\"#\" onClick=\"window.location.reload();\" class=\"word_dark\">刷新页面</a></td>\r\n");
      out.write("        <td width=\"9%\"><a href=\"#\" onClick=\"myclose()\" class=\"word_dark\">关闭系统</a></td>\r\n");
      out.write("\t\t<script language=\"javascript\">\r\n");
      out.write("\t\t\tfunction myclose(){\r\n");
      out.write("\t\t\t\tif(confirm(\"真的要关闭当前窗口吗?\")){\r\n");
      out.write("\t\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t</script>\r\n");
      out.write("        </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("      <table width=\"93%\" height=\"79\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td height=\"69\" align=\"right\" valign=\"bottom\">当前登录用户：");
      out.print(manager);
      out.write("</td>\r\n");
      out.write("        </tr>\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

ManagerDAO managerDAO=new ManagerDAO();
ManagerForm form1=(ManagerForm)managerDAO.query_p(manager);
int sysset1=0;
int readerset1=0;
int bookset1=0;
int borrowback1=0;
int sysquery1=0;
if(form1!=null){
	sysset1=form1.getSysset();
	readerset1=form1.getReaderset();
	bookset1=form1.getBookset();
	borrowback1=form1.getBorrowback();
	sysquery1=form1.getSysquery();
}


      out.write("\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<script src=\"JS/onclock.JS\" charset=\"gbk\"></script>\r\n");
      out.write("<script src=\"JS/menu.JS\" charset=\"gbk\"></script>\r\n");
      out.write("<div class=menuskin id=popmenu\r\n");
      out.write("      onmouseover=\"clearhidemenu();highlightmenu(event,'on')\"\r\n");
      out.write("      onmouseout=\"highlightmenu(event,'off');dynamichide(event)\" style=\"Z-index:100;position:absolute;\"></div>\r\n");
      out.write("<table width=\"778\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">\r\n");
      out.write("      <tr bgcolor=\"#DFA40C\">\r\n");
      out.write("        <td width=\"3%\" height=\"27\">&nbsp;</td>\r\n");
      out.write("        <td width=\"29%\"><div id=\"bgclock\" class=\"word_white\"></div></td>\r\n");
      out.write("\t\t<script language=\"javascript\">\r\n");
      out.write("\t\t\tfunction quit(){\r\n");
      out.write("\t\t\t\tif(confirm(\"真的要退出系统吗?\")){\r\n");
      out.write("\t\t\t\t\twindow.location.href=\"logout.jsp\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t</script>\r\n");
      out.write("        <td width=\"66%\" align=\"right\" bgcolor=\"#B0690B\" class=\"word_white\"><a href=\"main.jsp\" class=\"word_white\">首页</a> |\r\n");
      out.write("        ");
if(sysset1==1){
      out.write("<a  onmouseover=showmenu(event,sysmenu) onmouseout=delayhidemenu() class=\"word_white\" style=\"CURSOR:hand\" >系统设置</a> | ");
}
if(readerset1==1){
      out.write("<a  onmouseover=showmenu(event,readermenu) onmouseout=delayhidemenu() style=\"CURSOR:hand\"  class=\"word_white\">读者管理</a> | ");
}
if(bookset1==1){
      out.write("<a  onmouseover=showmenu(event,bookmenu) onmouseout=delayhidemenu() class=\"word_white\" style=\"CURSOR:hand\" >图书管理</a> | ");
}
if(borrowback1==1){
      out.write("<a  onmouseover=showmenu(event,borrowmenu) onmouseout=delayhidemenu() class=\"word_white\" style=\"CURSOR:hand\">图书借还</a> | ");
}
if(sysquery1==1){
      out.write("<a  onmouseover=showmenu(event,querymenu) onmouseout=delayhidemenu()  class=\"word_white\" style=\"CURSOR:hand\" >系统查询</a> | ");
}
      out.write("<a  href=\"manager.do?action=querypwd\" class=\"word_white\">更改口令</a> | <a href=\"#\" onClick=\"quit()\" class=\"word_white\">退出系统</a></td>\r\n");
      out.write("        <td width=\"2%\" bgcolor=\"#B0690B\">&nbsp;</td>\r\n");
      out.write("  </tr>\r\n");
      out.write("      <tr bgcolor=\"#DFA40C\">\r\n");
      out.write("        <td height=\"9\" colspan=\"4\" background=\"Images/navigation_bg_bottom.gif\"></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<table width=\"778\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td valign=\"top\" bgcolor=\"#FFFFFF\"><table width=\"100%\" height=\"558\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" class=\"tableBorder_gray\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td height=\"27\" valign=\"top\" style=\"padding:5px;\" class=\"word_orange\">&nbsp;当前位置：图书借还 &gt; 图书归还 &gt;&gt;&gt;</td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td align=\"center\" valign=\"top\" style=\"padding:5px;\"><table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"47\" background=\"Images/borrowBackRenew_back.gif\">&nbsp;</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"72\" align=\"center\" valign=\"top\" background=\"Images/main_booksort_1.gif\" bgcolor=\"#F8BF73\"><table width=\"96%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bordercolor=\"#F8BF73\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td valign=\"top\" bgcolor=\"#F8BF73\">");

int ID=0;
String name="";
String sex="";
String barcode="";
String birthday="";
String paperType="";
String paperNO="";
int number=0;
String typename="";
if(readerForm!=null){
	ID=readerForm.getId().intValue();
	name=readerForm.getName();
	sex=readerForm.getSex();
	barcode=readerForm.getBarcode();
	birthday=readerForm.getBirthday();
	paperType=readerForm.getPaperType();
	paperNO=readerForm.getPaperNO();
	number=readerForm.getNumber();
	typename=readerForm.getTypename();
}

      out.write("\r\n");
      out.write("                <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">\r\n");
      out.write("\t\t\t\t<form name=\"form1\" method=\"post\" action=\"borrow.do?action=bookback\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td><table width=\"90%\" height=\"21\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("                        <tr>\r\n");
      out.write("                          <td width=\"24%\" height=\"18\" style=\"padding-left:7px;padding-top:7px;\"><img src=\"Images/reader_checkbg.jpg\" width=\"142\" height=\"18\"></td>\r\n");
      out.write("                          <td width=\"76%\" style=\"padding-top:7px;\">读者条形码：\r\n");
      out.write("                            <input name=\"barcode\" type=\"text\" id=\"barcode\" value=\"");
      out.print(barcode);
      out.write("\" size=\"24\">\r\n");
      out.write("                            &nbsp;\r\n");
      out.write("                            <input name=\"Button\" type=\"button\" class=\"btn_grey\" value=\"确定\" onClick=\"checkreader(form1)\"></td>\r\n");
      out.write("                        </tr>\r\n");
      out.write("                    </table></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"13\" align=\"left\" style=\"padding-left:7px;\"><hr width=\"90%\" size=\"1\"></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td align=\"center\"><table width=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("                        <tr>\r\n");
      out.write("                          <td height=\"27\">姓&nbsp;&nbsp;&nbsp;&nbsp;名：\r\n");
      out.write("                            <input name=\"readername\" type=\"text\" id=\"readername\" value=\"");
      out.print(name);
      out.write("\"></td>\r\n");
      out.write("                          <td>性&nbsp;&nbsp;&nbsp;&nbsp;别：\r\n");
      out.write("                            <input name=\"sex\" type=\"text\" id=\"sex\" value=\"");
      out.print(sex);
      out.write("\"></td>\r\n");
      out.write("                          <td>读者类型：\r\n");
      out.write("                            <input name=\"readerType\" type=\"text\" id=\"readerType\" value=\"");
      out.print(typename);
      out.write("\"></td>\r\n");
      out.write("                        </tr>\r\n");
      out.write("                        <tr>\r\n");
      out.write("                          <td height=\"27\">证件类型：\r\n");
      out.write("                            <input name=\"paperType\" type=\"text\" id=\"paperType\" value=\"");
      out.print(paperType);
      out.write("\"></td>\r\n");
      out.write("                          <td>证件号码：\r\n");
      out.write("                            <input name=\"paperNo\" type=\"text\" id=\"paperNo\" value=\"");
      out.print(paperNO);
      out.write("\"></td>\r\n");
      out.write("                          <td>可借数量：\r\n");
      out.write("                            <input name=\"number\" type=\"text\" id=\"number\" value=\"");
      out.print(number);
      out.write("\" size=\"17\">\r\n");
      out.write("                            册\r\n");
      out.write("                            &nbsp;</td>\r\n");
      out.write("                        </tr>\r\n");
      out.write("                    </table></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("\t\t\t\t </form>\r\n");
      out.write("              </table></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td valign=\"top\"><table width=\"100%\" height=\"35\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#FFFFFF\" bordercolorlight=\"#FFFFFF\" bordercolordark=\"#F6B83B\" bgcolor=\"#FFFFFF\">\r\n");
      out.write("                <tr align=\"center\" bgcolor=\"#e3F4F7\">\r\n");
      out.write("                  <td width=\"24%\" height=\"25\" bgcolor=\"#FFF9D9\">图书名称</td>\r\n");
      out.write("                  <td width=\"12%\" bgcolor=\"#FFF9D9\">借阅时间</td>\r\n");
      out.write("                  <td width=\"13%\" bgcolor=\"#FFF9D9\">应还时间</td>\r\n");
      out.write("                  <td width=\"14%\" bgcolor=\"#FFF9D9\">出版社</td>\r\n");
      out.write("                  <td width=\"12%\" bgcolor=\"#FFF9D9\">书架</td>\r\n");
      out.write("                  <td bgcolor=\"#FFF9D9\">定价(元)</td>\r\n");
      out.write("                  <td width=\"12%\" bgcolor=\"#FFF9D9\"><input name=\"Button22\" type=\"button\" class=\"btn_grey\" value=\"完成归还\" onClick=\"window.location.href='bookBack.jsp'\"></td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                ");

int id=0;
String bookname="";
String borrowTime="";
String backTime="";
Float price=new Float(0);
String pubname="";
String bookcase="";
if(coll!=null && !coll.isEmpty()){
	Iterator it=coll.iterator();
	while(it.hasNext()){
	BorrowForm borrowForm=(BorrowForm)it.next();
        id=borrowForm.getId().intValue();
	bookname=borrowForm.getBookName();
	borrowTime=borrowForm.getBorrowTime();
	backTime=borrowForm.getBackTime();
	price=borrowForm.getPrice();
	pubname=borrowForm.getPubName();
	bookcase=borrowForm.getBookcaseName();

      out.write("\r\n");
      out.write("                <tr>\r\n");
      out.write("                  <td height=\"25\" style=\"padding:5px;\">&nbsp;");
      out.print(bookname);
      out.write("</td>\r\n");
      out.write("                  <td style=\"padding:5px;\">&nbsp;");
      out.print(borrowTime);
      out.write("</td>\r\n");
      out.write("                  <td style=\"padding:5px;\">&nbsp;");
      out.print(backTime);
      out.write("</td>\r\n");
      out.write("                  <td align=\"center\">&nbsp;");
      out.print(pubname);
      out.write("</td>\r\n");
      out.write("                  <td align=\"center\">&nbsp;");
      out.print(bookcase);
      out.write("</td>\r\n");
      out.write("                  <td width=\"13%\" align=\"center\">&nbsp;");
      out.print(price);
      out.write("</td>\r\n");
      out.write("                  <td width=\"12%\" align=\"center\"><a href=\"borrow.do?action=bookback&barcode=");
      out.print(barcode);
      out.write("&id=");
      out.print(id);
      out.write("&operator=");
      out.print(manager);
      out.write("\">归还</a>&nbsp;</td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                ");
	}
}
      out.write("\r\n");
      out.write("            </table>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("          </tr>\r\n");
      out.write("\t\t \r\n");
      out.write("        </table></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"19\" background=\"Images/main_booksort_2.gif\">&nbsp;</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("<table width=\"778\" height=\"66\"  border=\"0\" align=\"center\" cellpadding=\"-2\" cellspacing=\"-2\" bgcolor=\"#FFFFFF\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"11\" colspan=\"4\" background=\"Images/copyright_t.gif\"></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"124\" height=\"23\">&nbsp;</td>\r\n");
      out.write("        <td valign=\"bottom\" align=\"center\"> CopyRight &copy; 2008 www.**********.com 长春市*****有限公司</td>\r\n");
      out.write("        <td width=\"141\">&nbsp;</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"23\">&nbsp;</td>\r\n");
      out.write("        <td align=\"center\">本站请使用IE6.0或以上版本 1024*768为最佳显示效果</td>\r\n");
      out.write("        <td>&nbsp;</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"8\"></td>\r\n");
      out.write("        <td height=\"8\"></td>\r\n");
      out.write("        <td height=\"8\"></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("</table>\r\n");
      out.write("</td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
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
