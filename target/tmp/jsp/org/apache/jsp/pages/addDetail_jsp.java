package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class addDetail_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\t\t<title>单表模板</title>\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<form class=\"form-horizontal\">\r\n");
      out.write("\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"panel-body\" id=\"bulidPage\">\r\n");
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
      out.write("\t\t\t\t\t\t\t<div class=\"panel-body\" id=\"insPage\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<!--主表主键  用于 新增、修改-->\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type='hidden' name=\"subCode\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type='hidden' name=\"ParentPK\">\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\t\t\r\n");
      out.write("\t</form>\r\n");
      out.write("\t\t<!-- 缓存参数 -->\r\n");
      out.write("\t\t<!-- 是否为修改页面 -->\r\n");
      out.write("\t\t</body>\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../include/public.jsp", out, false);
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t  $(function() {\r\n");
      out.write("\t\t\t    var mainPageParam =pageParamFormat(ParentId +\" =\"+ParentPKValue);\r\n");
      out.write("\t\t\t\t//bulidMaintainPage($(\"#superInsertPage\"),dataSourceCode,mainPageParam);\r\n");
      out.write("\t\t\t\tbulidMaintainPage($(\"#insPage\"),_dataSourceCode);\r\n");
      out.write("\t\t\t\t//bulidButton($(\"#button_div\"),_dataSourceCode,'');\r\n");
      out.write("\t\t\t\t//bulidButton($(\"#button_child_div\"),_dataSourceCode,'');\r\n");
      out.write("\t\t\t\t//bulidButton($(\"#button_child_div_001\"),_dataSourceCode,'');\r\n");
      out.write("\t\t\t\t//bulidListPage($(\"#table\"),_dataSourceCode,_query_param);\r\n");
      out.write("\t\t\t\t//setReadonlyByDiv($(\"#superInsertPage input[type='text']\"));\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#PARENT_ID\").attr(\"readonly\",\"readonly\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#PARENT_ID\").val(ParentPKValue);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tvar Request = new Object(); \r\n");
      out.write("\t\t\t\tRequest = GetRequest();\r\n");
      out.write("\t\t\t\tvar subCode = Request['subCode'];\r\n");
      out.write("\t\t\t\t$(\"#TOTAL_CODE\").attr(\"value\",subCode);\r\n");
      out.write("\t\t\t\t$(\"#TOTAL_CODE\").attr(\"readonly\",\"readonly\");\r\n");
      out.write("\t  });\r\n");
      out.write("\t  \r\n");
      out.write("\t  function GetRequest() { \r\n");
      out.write("\t\tvar url = location.search; //获取url中\"?\"符后的字串 \r\n");
      out.write("\t\tvar theRequest = new Object(); \r\n");
      out.write("\t\tif (url.indexOf(\"?\") != -1) { \r\n");
      out.write("\t\tvar str = url.substr(1); \r\n");
      out.write("\t\tstrs = str.split(\"&\"); \r\n");
      out.write("\t\tfor(var i = 0; i < strs.length; i ++) { \r\n");
      out.write("\t\ttheRequest[strs[i].split(\"=\")[0]]=unescape(strs[i].split(\"=\")[1]); \r\n");
      out.write("\t\t} \r\n");
      out.write("\t\t} \r\n");
      out.write("\t\treturn theRequest; \r\n");
      out.write("\t\t} \r\n");
      out.write("\t\r\n");
      out.write("\tfunction save(t){\r\n");
      out.write("\t\tsavaByQuery(t,dataSourceCode,$(\"#insPage\"))\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction savaByQuery(t,_dataSourceCode,$div){\r\n");
      out.write("\t\tvar message =\"\";\r\n");
      out.write("\t\tvar buttonToken =\"add\";\r\n");
      out.write("\t\tmessage = transToServer(findBusUrlByButtonTonken(buttonToken,'',_dataSourceCode),getJson($div));\r\n");
      out.write("\t\toTable.showModal('modal', message);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tsetReadonlyByDiv($(\"#insPage input[type='text']\"));\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//window.history.back(-1);\r\n");
      out.write("\t\t//back(t);\r\n");
      out.write("\t\t//queryTableByDataSourceCode(t,_dataSourceCode);\r\n");
      out.write("}\r\n");
      out.write("\t\r\n");
      out.write("\t//重写back方法\r\n");
      out.write("\tfunction back(t){\r\n");
      out.write("\t\twindow.history.back(-1);\r\n");
      out.write("\t}\r\n");
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
