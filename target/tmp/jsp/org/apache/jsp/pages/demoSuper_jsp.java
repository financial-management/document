package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class demoSuper_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"zh-cn\">\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<meta charset=\"utf-8\">\r\n");
      out.write("\t\t\t<title>父页面</title>\r\n");
      out.write("<link href=\"../css/fileinput.min.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"../css/fileinput-rtl.min.css\" rel=\"stylesheet\">\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body>\r\n");
      out.write("\r\n");
      out.write("\t\t<form class=\"form-horizontal\">\r\n");
      out.write("\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"panel-body\" id=\"bulidTable\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-heading\" id ='pageName'>\r\n");
      out.write("\t\t\t\t\t\t\t文件列表\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"bill_date_and_status\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- 查询条件页面 -->\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"panel-body\" id=\"queryParam\" style=\"display: none;\">\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-box-toggle\" onclick=\"moreToggle()\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"caret\"></span>更多搜索条件\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div id='button_div'> </div>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- 列表页面 -->\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"table\"></table>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"panel-body\" id=\"bulidPage\" style=\"display: none\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t新增\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"save(this)\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-saved\" aria-hidden=\"true\"></span>保存\r\n");
      out.write("\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"back(this)\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-ban-circle\" aria-hidden=\"true\"></span>返回\r\n");
      out.write("\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- 维护页面 -->\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"panel-body\" id=\"insPage\"></div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"isNewStyle\" value=\"1\" />\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t<!-- 是否为修改页面 -->\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("<!-- Modal -->\r\n");
      out.write("<div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\r\n");
      out.write("<form enctype=\"multipart/form-data\" action=\"/document/base?cmd=uploadAffix\" method=\"post\">\r\n");
      out.write("  <input type=\"hidden\" name=\"did\" id=\"did\"/>\r\n");
      out.write("  <div class=\"modal-dialog modal-lg\" role=\"document\">\r\n");
      out.write("    <div class=\"modal-content\">\r\n");
      out.write("      <div class=\"modal-header\">\r\n");
      out.write("        <h5 class=\"modal-title\" id=\"exampleModalLabel\">文件上传</h5>\r\n");
      out.write("        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n");
      out.write("          <span aria-hidden=\"true\">&times;</span>\r\n");
      out.write("        </button>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"modal-body\">\r\n");
      out.write("        <input type=\"file\" name=\"files\"/>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"modal-footer\">\r\n");
      out.write("        <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">关闭</button>\r\n");
      out.write("        <button type=\"submit\" class=\"btn btn-primary\" title=\"Your custom upload logic\">上传</button>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div>\r\n");
      out.write("  </div>\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("\t</body>\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../include/listPublic.jsp", out, false);
      out.write("\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "./buttonjs.jsp", out, false);
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t$(function() {\r\n");
      out.write("\t\t\tbulidPage(true,true,true,true);\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
